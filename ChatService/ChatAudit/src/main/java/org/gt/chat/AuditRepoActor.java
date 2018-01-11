package org.gt.chat;

import akka.actor.AbstractActor;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.gt.chat.domain.AuditEvent;

public class AuditRepoActor extends AbstractActor {
    private MongoDatabase mongoDatabase;

    public AuditRepoActor(MongoDatabase mongoDatabase) {
        this.mongoDatabase = mongoDatabase;
    }

    @Override
    public Receive createReceive() {
        String collectionName = this.getContext()
                .getSystem().settings().config().getString("repo.collection");
        MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
        return receiveBuilder().match(AuditEvent.class, auditEvent -> {
            Document doc = new Document("timestamp" , auditEvent.getEventPublishEpochTimeStamp())
                    .append("eventType", toString(auditEvent));
            collection.insertOne(doc);
        }).build();
    }

    private String toString(AuditEvent auditEvent) {
        return auditEvent.getAuditEventType() != null ? auditEvent.getAuditEventType().toString() : "";
    }
}
