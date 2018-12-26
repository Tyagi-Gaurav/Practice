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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class TwitterProducer {

    private Logger logger = LoggerFactory.getLogger(TwitterProducer.class);

    private String consumerKey = "p2QtggUP0llVI94Tp8IJe5u2B";
    private String consumerSecret = "j46pPMXSbMgCjFJX3S7BSxIaTXLajMHFcFpcTAr3LFkdyDwWTq";
    private String token = "2443944504-xoMVCx14taeiLbXawH2bOsbvjXmaIzauzoTSIHA";
    private String tokenSecret = "KzUQckqKpsLk44oJNfBYGOJE4E7UXtgEBEYNv8ShsPGPQ";

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


        //Loop to send tweets to Kafka
        while (!twitterClient.isDone()) {
            String message = null;
            try {
                message = msgQueue.poll(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
                twitterClient.stop();
            }

            logger.info(message);
        }

        logger.info("End of application");
    }

    public BasicClient createTwitterClient(BlockingQueue<String> msgQueue) {
        /** Declare the host you want to connect to, the endpoint, and authentication (basic auth or oauth) */
        Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
        StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();

        // Optional: set up some followings and track terms
        List<Long> followings = Lists.newArrayList(1234L, 566788L);
        List<String> terms = Lists.newArrayList("bitcoin");
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
