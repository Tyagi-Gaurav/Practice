package org.gt.streams;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Properties;

public class UserEnrichKafkaStream {
    public static void main(String[] args) {
        Properties config = new Properties();

        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "user-event-enrich-app");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG,
                Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,
                Serdes.String().getClass());

        StreamsBuilder streamsBuilder = new StreamsBuilder();

        /*
        Get a stream of user purchases
        1. Read from one topic (KStream)
        */
        KStream<String, String> userPurchases = streamsBuilder.stream("user-purchases");


        /*
            2. Read from other topic (GlobalKTable)
            Get a global ktable out of kafka.
            This will be replicated on each kafka streams application.

            The key for GlobalKTable is userId.
         */
        GlobalKTable<String, String>
                usersGlobalTable = streamsBuilder.globalTable("user-table");


        /*
        3. Inner Join
         */
        KStream<String, String> userPurchasesEnrichedJoin = userPurchases.join(usersGlobalTable,
                (key, value) -> key, /* map from (key, value) of stream to key of GLobalKTable*/
                (userPurchase, userInfo) -> "Purchase=" + userPurchase + ", UserInfo=[" + userInfo + "]");


        /*
        4. Write to kafka the result of the inner join.
         */
        userPurchasesEnrichedJoin.to("user-purchases-enriched-inner-join");

        /*
        5. Left Join
         */
        KStream<String, String> userPurchasesEnrichedLeftJoin = userPurchases.leftJoin(usersGlobalTable,
                (key, value) -> key,
                (userPurchase, userInfo) -> {
                    if (userInfo != null) {
                        return "Purchase=" + userPurchase + ", UserInfo=[" + userInfo + "]";
                    } else {
                        return "Purchase=" + userPurchase + ", UserInfo=null";
                    }
                });

        /*
        6. Write to kafka the result of the left join.
         */
        userPurchasesEnrichedLeftJoin.to("user-purchases-enriched-left-join");

        KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(), config);
        kafkaStreams.cleanUp();

        kafkaStreams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
    }
}
