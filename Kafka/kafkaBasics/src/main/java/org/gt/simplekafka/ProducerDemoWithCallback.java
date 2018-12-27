package org.gt.simplekafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Properties;

public class ProducerDemoWithCallback {
    private static final Logger logger = LoggerFactory.getLogger(ProducerDemoWithCallback.class);

    public static void main(String[] args) {
        //Create Producer Properties
        String bootstrapServers = "127.0.0.1:9092";
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());

        //Create Producer
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);

        //Create Producer Record

        //Send Data - asynchronous
        Callback callback = (metadata, exception) -> {
            //executes every time record is successfully sent.
            if (Objects.isNull(exception)) {
                //Record Successfully sent.
                logger.info("Received new metadata. \n" +
                                "Topic: {}\n" +
                                "Partition: {}\n" +
                                "Offset: {}\n" +
                                "Timestamp: {}\n"
                        , metadata.topic(), metadata.partition(), metadata.offset(), metadata.timestamp());
            } else {
                logger.error(exception.getMessage(), exception);
            }
        };

        for (int i = 0; i < 10; i++) {
            ProducerRecord<String, String> producerRecord =
                    new ProducerRecord<>("first_topic", "Hello World " + i);
            kafkaProducer.send(producerRecord, callback);
        }


        //flush Data
        kafkaProducer.flush();
        kafkaProducer.close();
    }
}
