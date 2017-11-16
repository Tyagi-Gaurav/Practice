package org.gt.chat.service;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import org.gt.chat.domain.ConversationAggregate;
import org.gt.chat.repos.ConversationRepositoryActor;
import org.gt.chat.response.Conversation;
import org.gt.chat.response.Conversations;
import scala.concurrent.ExecutionContextExecutor;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import static akka.pattern.PatternsCS.ask;
import static akka.pattern.PatternsCS.pipe;

public class ConversationActor extends AbstractActor {
    private final LoggingAdapter LOG = Logging.getLogger(this.getContext().getSystem(), this);
    private ExecutionContextExecutor dispatcher = this.getContext().getSystem().dispatcher();
    private ActorRef repoActor;

    public ConversationActor() {
        repoActor = this.getContext().getSystem()
                .actorOf(Props.create(ConversationRepositoryActor.class));
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
