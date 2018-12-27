package org.gt.simplekafka.consumer;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
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

public class ElasticsearchConsumer {

    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchConsumer.class);

    public static void main(String[] args) throws IOException {
        RestHighLevelClient restClient = createRestClient();

        String jsonString = "{\"foo\" : \"bar\"}";

        IndexRequest indexRequest =
                new IndexRequest("twitter", "tweets")
                        .source(jsonString, XContentType.JSON);

        IndexResponse indexResponse = restClient.index(indexRequest, RequestOptions.DEFAULT);

        String id = indexResponse.getId();
        logger.info("Id : {}", id);

        restClient.close();
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
