package org.gt.kafka.streams;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.*;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.TopologyTestDriver;
import org.apache.kafka.streams.test.ConsumerRecordFactory;
import org.apache.kafka.streams.test.OutputVerifier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

import static junit.framework.TestCase.assertNull;

public class WordCountAppTest {

    private TopologyTestDriver testDriver;
    private StringSerializer stringSerializer = new StringSerializer();
    private ConsumerRecordFactory<String, String> consumerRecordFactory =
            new ConsumerRecordFactory(stringSerializer, stringSerializer);

    @Before
    public void setUp() throws Exception {
        Serde<String> stringSerde = Serdes.String();
        Properties config = new Properties();

        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-test");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "dummy:1234");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, stringSerde.getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, stringSerde.getClass());

        WordCountApp wordCountApp = new WordCountApp();

        testDriver = new TopologyTestDriver(wordCountApp.createTopology(), config);
    }

    @After
    public void tearDown() throws Exception {
        if (testDriver != null) {
            testDriver.close();
        }
    }

    @Test
    public void makeSureCountsAreCorrect() {
        //given
        pushNewInputRecord("testing Kafka Streams");

        //then
        OutputVerifier.compareKeyValue(readOutput(), "testing", 1L);
        OutputVerifier.compareKeyValue(readOutput(), "kafka", 1L);
        OutputVerifier.compareKeyValue(readOutput(), "streams", 1L);
        assertNull(readOutput());
    }

    @Test
    public void makeSureCountsAreCorrectForDuplicateWords() {
        //given
        pushNewInputRecord("testing Kafka Streams");

        //then
        OutputVerifier.compareKeyValue(readOutput(), "testing", 1L);
        OutputVerifier.compareKeyValue(readOutput(), "kafka", 1L);
        OutputVerifier.compareKeyValue(readOutput(), "streams", 1L);
        assertNull(readOutput());

        pushNewInputRecord("testing Kafka again");
        OutputVerifier.compareKeyValue(readOutput(), "testing", 2L);
        OutputVerifier.compareKeyValue(readOutput(), "kafka", 2L);
        OutputVerifier.compareKeyValue(readOutput(), "again", 1L);

    }

    @Test
    public void makeSureWordsBecomLowerCase() throws Exception {
        //given
        pushNewInputRecord("KAFKA KAFKA KAFKA");

        //Then
        OutputVerifier.compareKeyValue(readOutput(), "kafka", 1L);
        OutputVerifier.compareKeyValue(readOutput(), "kafka", 2L);
        OutputVerifier.compareKeyValue(readOutput(), "kafka", 3L);
    }

    private ProducerRecord<String, Long> readOutput() {
        return testDriver.readOutput("word-count-output", new StringDeserializer(), new LongDeserializer());
    }

    private void pushNewInputRecord(String value) {
        testDriver.pipeInput(consumerRecordFactory.create("word-count-input", null, value));
    }
}