package org.gt.kafka.streams;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;

import java.util.Arrays;
import java.util.Properties;

import static org.apache.kafka.common.serialization.Serdes.Long;

public class FavouriteColor {
    public static void main(String[] args) {
        //Create Properties
        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "favourite-color-app");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); //Same as --from-beginning
        Serde<String> stringSerde = Serdes.String();
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, stringSerde.getClass());
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, stringSerde.getClass());

        //Define Stream Config
        StreamsBuilder streamsBuilder = new StreamsBuilder();
        KStream<String, String> stream =
                streamsBuilder.stream("favourite-colour-input", Consumed.with(stringSerde, stringSerde));

        //Create Topology
        /*
        1. Read one topic from kafka (KStream)
        2. Filter bad values
        3. Select key that will be the user id
        4. Map values to extract the color as lower case
        5. Filter to remove bad colours
        6. Write to kafka as intermediary topic
        7. Read from kafka as KTable.
        8. GroupBy Colors
        9. Count to count colors occurrences (KTable)
        10. Write to Kafka as final topic.
         */
        KStream<String, String> filteredStream = stream
                .peek((key, value) -> System.out.println("Key1: " + key + ", Value: " + value))
                .filter((key, value) -> value.contains(","))
                .peek((key, value) -> System.out.println("Key2: " + key + ", Value: " + value))
                .selectKey((ignoredKey, value) -> value.split(",")[0].toLowerCase())
                .peek((key, value) -> System.out.println("Key3: " + key + ", Value: " + value))
                .mapValues(value -> value.split(",")[1].toLowerCase())
                .peek((key, value) -> System.out.println("Key4: " + key + ", Value: " + value))
                .filter((key, color) -> Arrays.asList("green", "blue", "red").contains(color))
                .peek((key, value) -> System.out.println("Key5: " + key + ", Value: " + value));

        filteredStream.to("color-data-compacted");

        KTable<String, String> uniqueColors = streamsBuilder.table("color-data-compacted");

        KTable<String, Long> count = uniqueColors.groupBy((key, color) -> new KeyValue<>(color, color))
                .count();

        count.toStream().to("favourite-colour-output", Produced.with(stringSerde, Long()));

        //Prepare to run the topology
        KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(), config);
        kafkaStreams.cleanUp();

        //Run Topology
        kafkaStreams.start();

        System.out.println(kafkaStreams.toString());

        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
    }
}
