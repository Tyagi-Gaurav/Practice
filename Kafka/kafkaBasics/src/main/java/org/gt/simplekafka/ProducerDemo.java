package org.gt.simplekafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class ProducerDemo {
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
        ProducerRecord<String, String> producerRecord =
                new ProducerRecord<>("first_topic", "Hello World");

        //Send Data - asynchronous
        kafkaProducer.send(producerRecord);

        //flush Data
        kafkaProducer.flush();
        kafkaProducer.close();
    }
}
