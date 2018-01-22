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
import org.gt.chat.main.domain.ConversationAggregate;
import org.gt.chat.main.domain.ConversationEntity;
import org.gt.chat.main.audit.exception.InvalidUserException;
import scala.concurrent.ExecutionContextExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static akka.pattern.PatternsCS.pipe;

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
                    CompletableFuture<ConversationAggregate> conversationAggregateCompletableFuture
                            = CompletableFuture.supplyAsync(() -> {

                        MongoCollection<Document> collection = mongoDatabase.getCollection(config.getString("repo.collection"));
                        FindIterable<Document> conversations = collection.find(new Document("userId", userId));
                        MongoIterable<ConversationEntity> conversationEntityIterable =
                                conversations.map(document ->
                                        ConversationEntity.builder()
                                                .content(document.getString("content"))
                                                .groupId(document.getString("groupId"))
                                                .senderId(document.getString("senderId"))
                                                .receivedTimeStamp(document.getLong("receivedTimeStamp"))
                                                .messageId(document.getString("messageId"))
                                                .build());

                        List<ConversationEntity> conversationEntities =
                                StreamSupport.stream(conversationEntityIterable.spliterator(), false)
                                .collect(Collectors.toList());

                        if (conversationEntities.size() > 0) {
                            ConversationAggregate aggregate = new ConversationAggregate(conversationEntities);
                            return aggregate;
                        } else {
                            throw new InvalidUserException(userId);
                        }
                    });
                    pipe(conversationAggregateCompletableFuture, dispatcher).to(getSender());
                })
                .matchAny(o -> {
                    LOG.error("Error Occurred");
                    throw new IllegalArgumentException(o.toString());
                })
                .build();
    }
}
