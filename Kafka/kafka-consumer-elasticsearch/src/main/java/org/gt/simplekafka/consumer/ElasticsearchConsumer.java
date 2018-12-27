package org.gt.simplekafka.consumer;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class ElasticsearchConsumer {

    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchConsumer.class);

    public static void main(String[] args) throws IOException {
        RestHighLevelClient restClient = createRestClient();

        KafkaConsumer<String, String> kafkaConsumer = createKafkaConsumer("twitter_tweets");

        //poll for data
        while (true) {
            ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord consumerRecord : consumerRecords) {
                // Insert data into elasticsearch
                IndexRequest indexRequest =
                        new IndexRequest("twitter", "tweets")
                                .source(consumerRecord.value(), XContentType.JSON);

                IndexResponse indexResponse = restClient.index(indexRequest, RequestOptions.DEFAULT);

                String id = indexResponse.getId();
                logger.info("id: {}", id);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        //restClient.close();
    }

    public static KafkaConsumer<String, String> createKafkaConsumer(String topic) {
        Properties properties = new Properties();

        String bootStrapServers = "127.0.0.1:9092";
        String groupId = "kafka-demo-elasticsearch";

        //Create Consumer Configs
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        //Create Consumer
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);

        //subscribe to topics
        kafkaConsumer.subscribe(Arrays.asList(topic));
        return kafkaConsumer;
    }

    public static RestHighLevelClient createRestClient() {

        String hostName = "kafka-course-6268196731.eu-west-1.bonsaisearch.net";
        String username = "12mbmcyylz";
        String password = "28e7ii5uv8";

        final BasicCredentialsProvider basicCredentialsProvider = new BasicCredentialsProvider();
        basicCredentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(username, password));

        RestClientBuilder builder = RestClient.builder(
                new HttpHost(hostName, 443, "https"))
                .setHttpClientConfigCallback(httpClientBuilder ->
                        httpClientBuilder.setDefaultCredentialsProvider(basicCredentialsProvider));


        return new RestHighLevelClient(builder);
    }
}
