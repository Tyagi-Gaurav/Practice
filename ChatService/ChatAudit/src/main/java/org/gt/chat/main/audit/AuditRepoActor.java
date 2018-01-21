package org.gt.chat.main.audit;

import akka.actor.AbstractActor;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.gt.chat.domain.HealthCheckRequest;
import org.gt.chat.domain.HealthCheckResponse;
import org.gt.chat.main.audit.domain.AuditEvent;
import scala.concurrent.ExecutionContextExecutor;

import static akka.pattern.PatternsCS.pipe;
import static java.util.concurrent.CompletableFuture.completedFuture;

public class AuditRepoActor extends AbstractActor {
    private ExecutionContextExecutor dispatcher = this.getContext().getSystem().dispatcher();
    private MongoDatabase mongoDatabase;

    public AuditRepoActor(MongoDatabase mongoDatabase) {
        this.mongoDatabase = mongoDatabase;
    }

    @Override
    public Receive createReceive() {
        String collectionName = this.getContext()
                .getSystem().settings().config().getString("repo.collection");
        MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);

        return receiveBuilder()
                .match(AuditEvent.class, auditEvent -> {
                    Document doc = new Document("timestamp", auditEvent.getEventPublishEpochTimeStamp())
                            .append("eventType", toString(auditEvent))
                            .append("requestId", auditEvent.getGlobalRequestId());
                    collection.insertOne(doc);
                })
                .match(HealthCheckRequest.class, healthCheckRequest -> {
                    String result = "OK";
                    try {
                        mongoDatabase.listCollectionNames();
                    } catch (Exception e) {
                        result = e.getMessage();
                    }
                    pipe(completedFuture(HealthCheckResponse.builder()
                    .name("database").result(result).build()), dispatcher).to(getSender());
                })
                .build();
    }

    private String toString(AuditEvent auditEvent) {
        return auditEvent.getAuditEventType() != null ? auditEvent.getAuditEventType().toString() : "";
    }
}
