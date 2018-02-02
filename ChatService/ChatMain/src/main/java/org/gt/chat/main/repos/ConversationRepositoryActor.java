package org.gt.chat.main.repos;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.typesafe.config.Config;
import org.bson.Document;
import org.gt.chat.main.domain.dto.ConversationSaveDTO;
import org.gt.chat.main.exception.InvalidUserException;
import org.gt.chat.main.domain.ConversationEntity;
import scala.concurrent.ExecutionContextExecutor;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static akka.pattern.PatternsCS.pipe;
import static java.util.Collections.emptyList;

public class ConversationRepositoryActor extends AbstractActor {
    private final LoggingAdapter LOG = Logging.getLogger(this.getContext().getSystem(), this);
    private ExecutionContextExecutor dispatcher = this.getContext().getSystem().dispatcher();

    private MongoDatabase mongoDatabase;

    public ConversationRepositoryActor(MongoDatabase mongoDatabase) {
        this.mongoDatabase = mongoDatabase;
    }

    @Override
    public void preRestart(Throwable reason, Optional<Object> message) throws Exception {
        LOG.error("Yo, I am restarting due to " + reason);
        super.preRestart(reason, message);
    }

    @Override
    public void postRestart(Throwable reason) throws Exception {
        LOG.error("... restart completed after " + reason);
        super.postRestart(reason);
    }

    @Override
    public Receive createReceive() {
        Config config = this.getContext().getSystem().settings().config();
        return receiveBuilder()
                .match(String.class, (x -> !x.isEmpty()), userId -> {
                    CompletableFuture<ConversationEntity> conversationAggregateCompletableFuture
                            = CompletableFuture.supplyAsync(() -> {

                        MongoCollection<Document> collection = mongoDatabase.getCollection(config.getString("repo.collection"));
                        FindIterable<Document> conversations = collection.find(new Document("userId", userId));
                        MongoIterable<ConversationEntity> conversationEntityIterable =
                                conversations.map(document -> {
                                            Optional<Document> messages = Optional.ofNullable((Document) document.get("messages"));
                                            Optional<List<Document>> messageDetails = Optional.ofNullable((List<Document>) messages.orElse(new Document()).get("messageDetails"));
                                            return ConversationEntity.builder()
                                                    .userId(document.getString("userId"))
                                                    .messages(ConversationEntity.Messages.builder()
                                                            .senderId(messages.orElse(new Document()).getString("senderId"))
                                                            .messageDetails(messageDetails.orElse(emptyList()).stream().map(
                                                                    md -> ConversationEntity.MessageDetailEntity.builder()
                                                                            .content(md.getString("content"))
                                                                            .received(md.getBoolean("received"))
                                                                            .timestamp(md.getLong("timestamp"))
                                                                            .contentType(ConversationEntity.ContentTypeEntity.valueOf(md.getString("contentType")))
                                                                            .build()
                                                            ).collect(Collectors.toList()))
                                                            .build()).build();
                                        });

                        Optional<ConversationEntity> conversationEntities =
                                StreamSupport.stream(conversationEntityIterable.spliterator(), false)
                                .findFirst();


                        if (conversationEntities.isPresent()) {
                            return conversationEntities.get();
                        } else {
                            throw new InvalidUserException(userId);
                        }
                    });
                    pipe(conversationAggregateCompletableFuture, dispatcher).to(getSender());
                })
                .match(ConversationSaveDTO.class, conversationSaveDTO -> {
                    MongoCollection<Document> collection = mongoDatabase.getCollection(config.getString("repo.collection"));
//                    collection.findOneAndUpdate(
//                            new Document("userId", conversationSaveDTO.getSenderId())
//                            .append("messages.senderId", conversationSaveDTO.getRecipientId()),
//                            new Document("$push", new Document("messages.$.messageDetails",
//                                    new Document("content", conversationSaveDTO.getMessage().getContent()).
//                                    append("contentType", conversationSaveDTO.getMessage().getContentType().toString())
//                                    .append("received", true)
//                                    .append()
//                            );
                })
                .matchAny(o -> {
                    LOG.error("Error Occurred");
                    throw new IllegalArgumentException(o.toString());
                })
                .build();
    }
}
