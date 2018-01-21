package org.gt.chat.main.mockActors;

import akka.actor.AbstractActor;
import akka.pattern.PatternsCS;
import org.gt.chat.main.domain.ConversationRequest;
import org.gt.chat.main.audit.exception.InvalidUserException;
import org.gt.chat.main.domain.ConversationType;
import org.gt.chat.main.domain.Conversations;
import org.gt.chat.main.domain.Conversation;
import scala.concurrent.ExecutionContextExecutor;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class TestMessageActor extends AbstractActor {

        @Override
        public Receive createReceive() {
            return receiveBuilder()
                    .match(ConversationRequest.class, conversationRequest -> {
                        CompletableFuture<Conversations> completableFuture = CompletableFuture.supplyAsync(() -> {
                        if (conversationRequest.getUserId().equals("2")) {
                            return new Conversations(conversationRequest.getGlobalRequestId(), Arrays.asList(
                                    new Conversation(
                                            "2",
                                            234878234L,
                                            ConversationType.GROUP,
                                            "groupId",
                                            "senderId",
                                            "Hello World")
                            ));
                        } else {
                            throw new InvalidUserException(conversationRequest.getUserId());
                        }});
                        ExecutionContextExecutor dispatcher = this.getContext().getSystem().dispatcher();
                        PatternsCS.pipe(completableFuture, dispatcher).to(getSender());
                    })
                    .build();
        }
    }