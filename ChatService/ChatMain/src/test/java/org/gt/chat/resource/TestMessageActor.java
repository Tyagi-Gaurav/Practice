package org.gt.chat.resource;

import akka.actor.AbstractActor;
import akka.pattern.PatternsCS;
import org.gt.chat.response.ConversationType;
import org.gt.chat.response.Conversations;
import org.gt.chat.response.Conversation;
import scala.concurrent.ExecutionContextExecutor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.TimeZone;
import java.util.concurrent.CompletableFuture;

class TestMessageActor extends AbstractActor {

        @Override
        public Receive createReceive() {
            return receiveBuilder()
                    .match(String.class, userId -> {
                        CompletableFuture<Conversations> completableFuture = CompletableFuture.supplyAsync(() -> new Conversations(Arrays.asList(
                                new Conversation(
                                        "2",
                                        234878234L,
                                        ConversationType.GROUP,
                                        "groupId",
                                        "senderId",
                                        "Hello World")
                        )));
                        ExecutionContextExecutor dispatcher = this.getContext().getSystem().dispatcher();
                        PatternsCS.pipe(completableFuture, dispatcher).to(getSender());
                    })
                    .build();
        }
    }