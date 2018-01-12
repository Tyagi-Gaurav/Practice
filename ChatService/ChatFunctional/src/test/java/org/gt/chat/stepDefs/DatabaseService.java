package org.gt.chat.stepDefs;

import com.google.inject.Inject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import cucumber.runtime.java.guice.ScenarioScoped;
import org.bson.Document;
import org.gt.chat.main.audit.domain.TestAuditEvent;
import org.gt.chat.main.audit.domain.TestAuditEventType;
import org.gt.chat.scenario.ScenarioConfig;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static org.gt.chat.scenario.ConfigVariables.*;

@ScenarioScoped
public class DatabaseService {
    private final MongoDatabase database;
    private final ScenarioConfig scenarioConfig;

    @Inject
    public DatabaseService(ScenarioConfig scenarioConfig) {
        this.scenarioConfig = scenarioConfig;
        MongoClient mongoClient = new MongoClient(scenarioConfig.getString(DATABASE_HOST),
                scenarioConfig.getInt(DATABASE_PORT));
        database = mongoClient.getDatabase(scenarioConfig.getString(DATABASE_NAME));
    }

    public List<TestAuditEvent> findAuditEventFor(String requestId, String eventType) {
        MongoCollection<Document> collection = database.getCollection(scenarioConfig.getString(DATABASE_COLLECTION));
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
}
