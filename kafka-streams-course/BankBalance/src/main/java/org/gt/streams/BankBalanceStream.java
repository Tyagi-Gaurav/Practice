package org.gt.streams;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

public class BankBalanceStream {
    public static void main(String[] args) {
        Properties config = new Properties();

        //Properties Config
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "bank-balance-app");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        Serde<String> stringSerde = Serdes.String();
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, stringSerde.getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.Long().getClass());

        //Exactly Once Config
        config.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, StreamsConfig.EXACTLY_ONCE );

        //Define Streams Config
        StreamsBuilder streamsBuilder = new StreamsBuilder();
        ObjectMapper objectMapper = new ObjectMapper();

        /*
        Create Topology
        1. Read one topic from Kafka
        2. GroupbyKey -- Since we already have a key, no repartition happens.
        3. Aggregate
        4. Write to kafka.
         */
        KStream<String, String> kStream = streamsBuilder.stream("bank-transactions", Consumed.with(stringSerde, stringSerde));
        kStream.groupByKey()
                .aggregate(() -> 0L, (key, value, aggregate) -> {
                    long amount = 0L;
                    try {
                        MetaData metaData = objectMapper.readValue(new StringReader(value), MetaData.class);
                        amount = metaData.amount;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return aggregate + amount;
                })
                .toStream()
                .peek((key, value) -> System.out.println("Key : " + key + ", Value: " + value))
                .to("bank-transactions-output", Produced.with(stringSerde, Serdes.Long()));

        KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(), config);
        kafkaStreams.cleanUp();

        //Run Topology
        kafkaStreams.start();

        System.out.println(kafkaStreams.toString());

        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
    }
}
