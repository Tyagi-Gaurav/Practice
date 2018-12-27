package org.gt.simplekafka.consumer;

import com.google.gson.JsonParser;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
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

            int recordCount = consumerRecords.count();
            logger.info("Received {} records", recordCount);

            BulkRequest bulkRequest = new BulkRequest();

            for (ConsumerRecord consumerRecord : consumerRecords) {
                //2 strategies for generating Ids.
                // kafka generic Id
                /*String id = String.format("%s_%s_%s"
                        , consumerRecord.topic(),
                        consumerRecord.partition(),
                        consumerRecord.offset());*/

                logger.info("Message Received: " + consumerRecord.value());
                String id = extractIdFromTweets(consumerRecord.value().toString());

                // Insert data into elasticsearch
                IndexRequest indexRequest =
                        new IndexRequest(
                                "twitter",
                                "tweets",
                                id // This makes consumer idempotent
                        ).source(consumerRecord.value(), XContentType.JSON);

                bulkRequest.add(indexRequest);
            }


            if (recordCount > 0) {
                BulkResponse bulkResponse = restClient.bulk(bulkRequest, RequestOptions.DEFAULT);

                logger.info("Committing offsets");
                kafkaConsumer.commitSync();
                logger.info("Offsets have been committed");
            }
        }

        //restClient.close();
    }

    private static String extractIdFromTweets(String value) {
        JsonParser jsonParser = new JsonParser();
        return jsonParser.parse(value).getAsJsonObject().get("id_str").getAsString();
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
        properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "100");

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
