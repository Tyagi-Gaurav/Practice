package org.gt.chat.main.repos;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import org.gt.chat.main.audit.domain.ConversationAggregate;
import org.gt.chat.main.audit.domain.ConversationEntity;
import org.gt.chat.main.audit.exception.InvalidUserException;
import scala.concurrent.ExecutionContextExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static akka.pattern.PatternsCS.pipe;

public class ConversationRepositoryActor extends AbstractActor {
    private final LoggingAdapter LOG = Logging.getLogger(this.getContext().getSystem(), this);
    private ExecutionContextExecutor dispatcher = this.getContext().getSystem().dispatcher();

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
        return receiveBuilder()
                .match(String.class, (x -> !x.isEmpty()) ,userId -> {
                    CompletableFuture<ConversationAggregate> conversationAggregateCompletableFuture
                            = CompletableFuture.supplyAsync(() -> {
                        if (userId.equals("2")) {
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
