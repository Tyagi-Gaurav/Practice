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
        MongoCollection<Document> collection = mongoDatabase.getCollection("test");
        return receiveBuilder().match(AuditEvent.class, auditEvent -> {
            Document doc = new Document("timestamp" , auditEvent.getEventPublishEpochTimeStamp())
                    .append("eventType", auditEvent.getAuditEventType());
            collection.insertOne(doc);
        }).build();
    }
}
