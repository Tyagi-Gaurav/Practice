package org.gt.chat.repos;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import org.gt.chat.domain.ConversationAggregate;
import org.gt.chat.domain.ConversationEntity;
import scala.concurrent.ExecutionContextExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static akka.pattern.PatternsCS.pipe;

public class ConversationRepositoryActor extends AbstractActor {
    private final LoggingAdapter LOG = Logging.getLogger(this.getContext().getSystem(), this);
    private ExecutionContextExecutor dispatcher = this.getContext().getSystem().dispatcher();

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, userId -> {
                    CompletableFuture<ConversationAggregate> conversationAggregateCompletableFuture
                            = CompletableFuture.supplyAsync(() -> {
                        ConversationEntity messageEntity = new ConversationEntity(
                                2L,
                                "Hello World",
                                234878234L,
                                "groupId",
                                "senderId"
                        );
                        List<ConversationEntity> entityList = new ArrayList<>();
                        entityList.add(messageEntity);
                        ConversationAggregate aggregate = new ConversationAggregate(entityList);
                        return aggregate;
                    });
                    pipe(conversationAggregateCompletableFuture, dispatcher).to(getSender());
                })
                .matchAny(o -> LOG.error("Received unknown message {}", o))
                .build();
    }
}
