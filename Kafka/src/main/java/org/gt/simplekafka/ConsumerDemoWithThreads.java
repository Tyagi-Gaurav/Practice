package org.gt.simplekafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class ConsumerDemoWithThreads {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerDemoWithThreads.class);

    public static void main(String[] args) {
        String topic = "first_topic";
        String bootStrapServers = "127.0.0.1:9092";
        String groupId = "my-sixth-application";

        CountDownLatch countDownLatch = new CountDownLatch(1);

        logger.info("Creating the consumer thread");
        ConsumerThread consumerThread = new ConsumerDemoWithThreads().new ConsumerThread(countDownLatch, topic, bootStrapServers, groupId);

        Thread thread = new Thread(consumerThread);
        thread.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Caught shutdown hook");
            consumerThread.shutDown();
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                logger.error("Application got interrupted", e);
            } finally {
                logger.info("Application is closing");
            }
        }));

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            logger.error("Application got interrupted", e);
        } finally {
            logger.info("Application is closing");
        }
    }

    public class ConsumerThread implements Runnable {

        private CountDownLatch countDownLatch;
        private KafkaConsumer<String, String> kafkaConsumer;

        public ConsumerThread(CountDownLatch countDownLatch,
                              String topic,
                              String bootStrapServers,
                              String groupId) {
            this.countDownLatch = countDownLatch;
            Properties properties = new Properties();

            properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
            properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
            properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

            this.kafkaConsumer = new KafkaConsumer<>(properties);
            kafkaConsumer.subscribe(Arrays.asList(topic));
        }

        @Override
        public void run() {
            //poll for data
            try {
                while (true) {
                    ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(100));
                    for (ConsumerRecord consumerRecord : consumerRecords) {
                        logger.info("Key: {}, Value: {}", consumerRecord.key(), consumerRecord.value());
                        logger.info("Partition: {}, Offset: {}", consumerRecord.partition(), consumerRecord.offset());
                    }
                }
            } catch(WakeupException e) {
                logger.info("Received Shutdown Signal!");
            } finally {
                kafkaConsumer.close();
                //Tell our main code that we're done.
                countDownLatch.countDown();
            }
        }

        public void shutDown() {
            //Special method to interrupt kafkaConsumer.poll();
            // It will throw an exception WakeupException
            kafkaConsumer.wakeup();
        }
    }
}
