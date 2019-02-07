package org.gt.streams;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class IdempotentBankProducer {
    public static void main(String[] args) {

        String bootStrapServer = "localhost:9092";
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        //Ensuring no duplicates are pushed
        properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        properties.put(ProducerConfig.LINGER_MS_CONFIG , "1");
        properties.put(ProducerConfig.RETRIES_CONFIG , "3");

        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);

        Callback callback = (metadata, exception) -> {
            if (Objects.isNull(exception)) {
                System.out.println(String.format("Received new metadata. \n" +
                                "Topic: %s\n" +
                                "Partition: %d\n" +
                                "Offset: %d\n" +
                                "Timestamp: %d\n"
                        , metadata.topic(), metadata.partition(), metadata.offset(), metadata.timestamp()));
            } else {
                System.err.println(exception.getMessage());
            }
         };


        //Create 100 records per second using random amounts and keep count of amounts sent.
        List<MetaData> metaDataList = Arrays.asList(
                new MetaData("John"),
                new MetaData("Alice"),
                new MetaData("Manikchand"),
                new MetaData("Phillip"),
                new MetaData("Alan"),
                new MetaData("Homer"),
                new MetaData("Marge"),
                new MetaData("Ravi"),
                new MetaData("Bhujang"),
                new MetaData("RatanLal")
        );

        ExecutorService executorService = Executors.newFixedThreadPool(metaDataList.size());

        Random random = new Random();
        AtomicBoolean isRunning = new AtomicBoolean(true);
        Map<String, Long> collect = metaDataList.stream()
                .collect(Collectors.toMap(metaData -> metaData.name,
                        metaData -> 0L));

        metaDataList.stream()
                .map(md -> (Runnable) () -> {
                    while (isRunning.get()) {
                        md.next((long) random.nextInt(100), LocalDateTime.now());
                        System.out.println(String.format("Posting %s to kafka", md.toString()));
                        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("bank-transactions", md.name, md.toString());
                        kafkaProducer.send(producerRecord, callback);
                        try {
                            collect.computeIfPresent(md.name, (s, oldValue) -> oldValue + md.amount);
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            System.err.println(e.getMessage());
                        }
                    }
                }).forEach(executorService::execute);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down executor");
            isRunning.compareAndSet(true, false);
            try {
                executorService.shutdown();
                System.out.println("Waiting for threads to shutdown...");
                executorService.awaitTermination(2000, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
            System.out.println("Shutdown complete!");

            collect.forEach((key, value) -> {
                System.out.println(String.format("Total bank balance for %s is %d" , key, value));
            });
        }
        ));
    }
}
