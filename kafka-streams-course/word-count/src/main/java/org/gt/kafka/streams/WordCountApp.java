package org.gt.kafka.streams;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;

import java.util.Arrays;
import java.util.Properties;

public class WordCountApp {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "word-count-app"); //Used as Consumer group Id
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        Serde<String> stringSerde = Serdes.String();
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, stringSerde.getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, stringSerde.getClass());

        WordCountApp wordCountApp = new WordCountApp();

        KafkaStreams kafkaStreams = new KafkaStreams(wordCountApp.createTopology(), properties);
        // The drawback of cleaning up local state prior is that your app must rebuilt its local state from scratch, which
        // will take time and will require reading all the state-relevant data from the Kafka cluster over the network.
        // Thus in a production scenario you typically do not want to clean up always as we do here but rather only when it
        // is truly needed, i.e., only under certain conditions (e.g., the presence of a command line flag for your app).
        // See `ApplicationResetExample.java` for a production-like example.
        kafkaStreams.cleanUp();
        kafkaStreams.start();

        System.out.println(kafkaStreams.toString());

        //Add shutdown hook to respond to SIGTERM and gracefully close kafka streams
        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
    }

    public Topology createTopology() {
        Serde<String> stringSerde = Serdes.String();
        StreamsBuilder streamsBuilder = new StreamsBuilder();

        //1 - Stream from Kafka
        KStream<String, String> wordCountStream = streamsBuilder.stream("word-count-input",
                Consumed.with(stringSerde, stringSerde));

        //2 - Map Values to lowercase
        KTable<String, Long> wordCountTable = wordCountStream.mapValues((ValueMapper<String, String>) String::toLowerCase)
                //3 - FlatMap values split by space
                .flatMapValues((ValueMapper<String, Iterable<String>>) value -> Arrays.asList(value.split(" ")))
                //4 - Select a new key and discard the old key
                .selectKey((ignoredKey, word) -> word)
                //5 - Group key by aggregation
                .groupByKey()
                //6 - Count occurrences
                .count();

        wordCountTable.toStream().to("word-count-output", Produced.with(stringSerde, Serdes.Long()));

        return streamsBuilder.build();
    }
}
