package org.gt.chat;

import akka.actor.AbstractActor;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.gt.chat.domain.AuditEvent;

public class AuditRepoActor extends AbstractActor {
    private MongoClient mongoClient = new MongoClient("localhost", 27017);
    private MongoDatabase mongoDatabase = mongoClient.getDatabase("local");
    MongoCollection<Document> collection = mongoDatabase.getCollection("test");

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(AuditEvent.class, auditEvent -> {
            Document doc = new Document("timestamp" , auditEvent.getEventPublishEpochTimeStamp())
                    .append("eventType", auditEvent.getAuditEventType());
            collection.insertOne(doc);
        }).build();
    }
}
