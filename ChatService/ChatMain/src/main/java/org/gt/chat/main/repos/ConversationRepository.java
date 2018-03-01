package org.gt.chat.main.repos;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.typesafe.config.Config;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.gt.chat.main.domain.ConversationEntity;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Slf4j
public class ConversationRepository {
    private MongoDatabase mongoDatabase;
    private Config config;

    public ConversationRepository(MongoDatabase mongoDatabase, Config config) {
        this.mongoDatabase = mongoDatabase;
        this.config = config;
    }

    public List<ConversationEntity> getConversationsFor(String userId) {
        MongoCollection<Document> collection = mongoDatabase.getCollection(config.getString("repo.collection"));
        FindIterable<Document> conversations = collection.find(new Document("$or",
                                new Document(new Document("recipientId", userId).append("senderId", userId))));

        MongoIterable<ConversationEntity> conversationEntityIterable =
            conversations.map(document -> {
                String senderId = document.getString("senderId");
                String recipientId = document.getString("recipientId");
                String contentType = document.getString("contentType");
                String content = document.getString("content");
                Long timestamp = document.getLong("timestamp");

                return ConversationEntity.builder()
                        .senderId(senderId)
                        .recipientId(recipientId)
                        .content(content)
                        .timestamp(timestamp == null ? 0 : timestamp)
                        .received(userId.equals(recipientId))
                        .contentType(contentType == null ? null :
                                ConversationEntity.ContentTypeEntity.valueOf(contentType))
                        .build();
            });

        return StreamSupport.stream(conversationEntityIterable.spliterator(), true)
                .collect(Collectors.toList());
    }
}
