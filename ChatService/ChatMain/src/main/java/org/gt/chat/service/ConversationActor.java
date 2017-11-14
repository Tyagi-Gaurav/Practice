package org.gt.chat.service;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import org.gt.chat.repos.MessageRepository;
import org.gt.chat.response.Conversations;
import org.gt.chat.response.Conversation;
import scala.concurrent.ExecutionContextExecutor;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static akka.pattern.PatternsCS.pipe;

public class ConversationActor extends AbstractActor {
    private final LoggingAdapter LOG = Logging.getLogger(this.getContext().getSystem(), this);
    private final MessageRepository repository;

    public ConversationActor(MessageRepository repository) {
        this.repository = repository;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, userId -> {
                    CompletableFuture<Conversations> completableFuture = CompletableFuture.supplyAsync(() ->
                            new Conversations(repository.getMessages(userId)
                                    .getMessageEntityList()
                                    .stream()
                                    .map(Conversation::from)
                                    .collect(Collectors.toList())));
                    ExecutionContextExecutor dispatcher = this.getContext().getSystem().dispatcher();
                    pipe(completableFuture, dispatcher).to(getSender());
                })
                .matchAny(o -> LOG.error("Received unknown message {}", o))
                .build();
    }
}
