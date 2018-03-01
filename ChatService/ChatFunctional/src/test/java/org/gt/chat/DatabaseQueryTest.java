package org.gt.chat;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class DatabaseQueryTest {
    private final MongoClient mongoClient = new MongoClient("localhost", 27017);
    private static final String DATABASE_NAME = "auditDB";
    private static final String CONV_COLLECTION = "conversationCollection";

    public static void main(String[] args) {
        DatabaseQueryTest databaseQueryTest = new DatabaseQueryTest();
        databaseQueryTest.retrieveMessagesForAUser();
    }

    private void retrieveMessagesForAUser() {
        MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
        MongoCollection<Document> collection = database.getCollection(CONV_COLLECTION);

        String userId = "2";

        //db.conversationCollection.find({"$or" : [{"senderId" : "3"}, {"recipientId" : "3"}]})
        FindIterable<Document> conversations = collection.find(new Document("$or",
                new Document(new Document("recipientId", userId).append("senderId", userId))));


    }
}
