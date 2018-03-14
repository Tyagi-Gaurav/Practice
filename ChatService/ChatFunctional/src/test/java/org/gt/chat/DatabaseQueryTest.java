package org.gt.chat;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.function.Consumer;

import static java.util.Arrays.asList;

public class DatabaseQueryTest {
    private final MongoClient mongoClient = new MongoClient("localhost", 27017);
    private static final String DATABASE_NAME = "auditDB";
    private static final String CONV_COLLECTION = "conversationCollection";

    public static void main(String[] args) {
        DatabaseQueryTest databaseQueryTest = new DatabaseQueryTest();

        databaseQueryTest.createConversationsInDatabase(true);

        databaseQueryTest.retrieveMessagesForAUser();
    }

    private void createConversationsInDatabase(boolean deleteBeforeInsert) {
        MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
        MongoCollection<Document> collection = database.getCollection(CONV_COLLECTION);


        if (deleteBeforeInsert) {
            deleteAllConversations(collection);
        }

        collection.insertMany(asList(
                new Document("senderId" , "2").append("recipientId", "3")
                        .append("content", "Hi")
                        .append("contentType", "text/plain-utf8").append("timestamp", "1517645197"),
                new Document("senderId" , "3").append("recipientId", "2")
                        .append("content", "Hi")
                        .append("contentType", "text/plain-utf8").append("timestamp", "1517645222"),
                new Document("senderId" , "2").append("recipientId", "3")
                        .append("content", "How are you ?")
                        .append("contentType", "text/plain-utf8").append("timestamp", "1517645246"),
                new Document("senderId" , "3").append("recipientId", "2")
                        .append("content", "Good. How are you ?")
                        .append("contentType", "text/plain-utf8").append("timestamp", "1517645264"),
                new Document("senderId" , "3").append("recipientId", "4")
                        .append("content", "Good. How are you ?")
                        .append("contentType", "text/plain-utf8").append("timestamp", "1517645264"),
                new Document("senderId" , "4").append("recipientId", "5")
                        .append("content", "Good. How are you 5 ?")
                        .append("contentType", "text/plain-utf8").append("timestamp", "1517663493")
        ));
    }

    private void deleteAllConversations(MongoCollection<Document> collection) {
        collection.deleteMany(new Document());
    }

    private void retrieveMessagesForAUser() {
        MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
        MongoCollection<Document> collection = database.getCollection(CONV_COLLECTION);

        String userId = "2";

        //db.conversationCollection.find({"$or" : [{"senderId" : "3"}, {"recipientId" : "3"}]})
        FindIterable<Document> conversations = collection.find(new Document("$or",asList(
                new Document("recipientId", userId), new Document("senderId", userId))));

        conversations.forEach((Consumer<Document>) System.out::println);
    }
}
