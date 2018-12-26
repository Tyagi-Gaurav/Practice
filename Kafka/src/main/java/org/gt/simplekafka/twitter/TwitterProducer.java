package org.gt.simplekafka.twitter;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.BasicClient;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class TwitterProducer {

    private Logger logger = LoggerFactory.getLogger(TwitterProducer.class);

    private String consumerKey = "";
    private String consumerSecret = "";
    private String token = "";
    private String tokenSecret = "";
    private List<String> terms = Lists.newArrayList("kafka");

    public TwitterProducer() {
    }

    public static void main(String[] args) {
        new TwitterProducer().run();
    }

    public void run() {
        //Create Twitter Client
        LinkedBlockingQueue<String> msgQueue = new LinkedBlockingQueue<>(1000);
        BasicClient twitterClient = createTwitterClient(msgQueue);

        //Attempt to establish a connection
        twitterClient.connect();

        //Create Kafka Producer
        KafkaProducer<String, String> kafkaProducer = createKafkaProducer();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("shutting down application");
            logger.info("shutting down twitter client");
            twitterClient.stop();
            logger.info("closing producer");
            kafkaProducer.close();
            logger.info("done!");
        }));

        //Loop to send tweets to Kafka
        while (!twitterClient.isDone()) {
            String message = null;
            try {
                message = msgQueue.poll(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
                twitterClient.stop();
            }

            if (message != null) {
                kafkaProducer.send(
                        new ProducerRecord<String, String>("twitter_tweets", null, message), (metadata, exception) -> {
                            if (exception != null) {
                                logger.error("Something bad happened", exception);
                            }
                        });
            }
        }

        logger.info("End of application");
    }

    private KafkaProducer<String, String> createKafkaProducer() {
        String bootstrapServers = "127.0.0.1:9092";
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());

        //Create Safe Producer
        properties.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
        properties.setProperty(ProducerConfig.ACKS_CONFIG, "all");
        properties.setProperty(ProducerConfig.RETRIES_CONFIG, String.valueOf(Integer.MAX_VALUE));
        properties.setProperty(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "5");

        //Create Producer
        return new KafkaProducer<>(properties);
    }

    public BasicClient createTwitterClient(BlockingQueue<String> msgQueue) {
        /** Declare the host you want to connect to, the endpoint, and authentication (basic auth or oauth) */
        Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
        StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();

        // Optional: set up some followings and track terms
        List<Long> followings = Lists.newArrayList(1234L, 566788L);
        hosebirdEndpoint.followings(followings);
        hosebirdEndpoint.trackTerms(terms);

        // These secrets should be read from a config file
        Authentication hosebirdAuth =
                new OAuth1(consumerKey, consumerSecret, token, tokenSecret);

        ClientBuilder builder = new ClientBuilder()
                .name("Hosebird-Client-01")                              // optional: mainly for the logs
                .hosts(hosebirdHosts)
                .authentication(hosebirdAuth)
                .endpoint(hosebirdEndpoint)
                .processor(new StringDelimitedProcessor(msgQueue));

        return builder.build();
    }
}
