package org.gt.chat.stepDefs.service;

import com.google.inject.Inject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import cucumber.runtime.java.guice.ScenarioScoped;
import org.bson.Document;
import org.gt.chat.domain.audit.TestAuditEvent;
import org.gt.chat.domain.audit.TestAuditEventType;
import org.gt.chat.domain.user.TestUser;
import org.gt.chat.main.domain.ConversationEntity;
import org.gt.chat.scenario.ScenarioConfig;
import org.junit.After;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.gt.chat.scenario.ConfigVariables.*;

@ScenarioScoped
public class MockDatabaseService {
    private final MongoDatabase database;
    private final ScenarioConfig scenarioConfig;

    @After
    public void reset() {
        MongoCollection<Document> auditCollection =
                database.getCollection(scenarioConfig.getString(DATABASE_AUDIT_COLLECTION));
        MongoCollection<Document> conversationCollection =
                database.getCollection(scenarioConfig.getString(DATABASE_AUDIT_COLLECTION));

        auditCollection.drop();
        conversationCollection.drop();
    }

    @Inject
    public MockDatabaseService(ScenarioConfig scenarioConfig) {
        this.scenarioConfig = scenarioConfig;
        MongoClient mongoClient = new MongoClient(scenarioConfig.getString(DATABASE_HOST),
                scenarioConfig.getInt(DATABASE_PORT));
        database = mongoClient.getDatabase(scenarioConfig.getString(DATABASE_NAME));
    }

    public List<TestAuditEvent> findAuditEventFor(String requestId, String eventType) {
        MongoCollection<Document> collection = database.getCollection(scenarioConfig.getString(DATABASE_AUDIT_COLLECTION));
        FindIterable<Document> documents =
                collection.find(and(
                        eq("requestId", requestId),
                        eq("eventType", eventType)));
        MongoIterable<TestAuditEvent> auditEventIterable = documents.map(document -> TestAuditEvent.builder()
                .auditEventType(TestAuditEventType.valueOf(document.getString("eventType")))
                .requestId(document.getString("requestId")).build());

        return StreamSupport.stream(auditEventIterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    public void createConversationsForUser() {
        MongoCollection<Document> collection =
                database.getCollection(scenarioConfig.getString(DATABASE_CONV_COLLECTION));
        collection.insertOne(
                new Document("userId", "2")
                        .append("messages", new Document("senderId", "senderId")
                                .append("messageDetails", asList(
                                        new Document("content", "Hello World")
                                                .append("received" , true)
                                                .append("timestamp", 234878234L)
                                                .append("contentType", ConversationEntity.ContentTypeEntity.TEXT_PLAIN_UTF8.toString())
                                ))));

        FindIterable<Document> documents = collection.find(new Document("userId", "2"));
        List<Document> fetchedDocuments = StreamSupport.stream(documents.spliterator(), false).collect(Collectors.toList());
        assertThat(fetchedDocuments.size()).isEqualTo(1);
    }

    public void createUser(TestUser testUser) {

    }
}
