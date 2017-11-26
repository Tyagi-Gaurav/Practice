package org.gt.chat.service;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.DeciderBuilder;
import org.gt.chat.domain.ConversationAggregate;
import org.gt.chat.repos.ConversationRepositoryActor;
import org.gt.chat.response.Conversation;
import org.gt.chat.response.Conversations;
import scala.concurrent.ExecutionContextExecutor;
import scala.concurrent.duration.Duration;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static akka.actor.SupervisorStrategy.escalate;
import static akka.actor.SupervisorStrategy.resume;
import static akka.pattern.PatternsCS.ask;
import static akka.pattern.PatternsCS.pipe;

public class ConversationActor extends AbstractActor {
    private final LoggingAdapter LOG = Logging.getLogger(this.getContext().getSystem(), this);
    private ExecutionContextExecutor dispatcher = this.getContext().getSystem().dispatcher();
    private ActorRef repoActor;

    public ConversationActor() {
        repoActor = this.getContext()
                .actorOf(Props.create(ConversationRepositoryActor.class));
    }

    private static SupervisorStrategy strategy =
            new OneForOneStrategy(10,
                    Duration.create(1, TimeUnit.MINUTES),
                    DeciderBuilder
                    .match(IllegalArgumentException.class, e -> resume())
                    .matchAny(o -> escalate())
                    .build());

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, userId -> {
                    CompletionStage<Conversations> listCompletionStage =
                            ask(repoActor, userId, 1000L)
                            .thenCompose(x ->
                                    CompletableFuture.supplyAsync(() ->
                                        new Conversations(((ConversationAggregate) x)
                                                .getMessageEntityList()
                                                .stream()
                                                .map(Conversation::from)
                                                .collect(Collectors.toList())))
                );
                    pipe(listCompletionStage, dispatcher).to(getSender());
                })
                .matchAny(o -> LOG.error("Received unknown message {}", o))
                .build();
    }
}