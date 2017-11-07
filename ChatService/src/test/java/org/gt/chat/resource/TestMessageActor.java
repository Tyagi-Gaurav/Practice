package org.gt.chat.resource;

import akka.actor.AbstractActor;
import org.gt.chat.response.Message;
import org.gt.chat.response.Messages;
import scala.concurrent.ExecutionContextExecutor;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import static akka.pattern.PatternsCS.pipe;

class TestMessageActor extends AbstractActor {

        @Override
        public Receive createReceive() {
            return receiveBuilder()
                    .match(String.class, userId -> {
                        CompletableFuture<Messages> completableFuture = CompletableFuture.supplyAsync(() -> new Messages(Arrays.asList(
                                new Message(
                                        "2",
                                        "Hello World",
                                        234878234L)
                        )));
                        ExecutionContextExecutor dispatcher = this.getContext().getSystem().dispatcher();
                        pipe(completableFuture, dispatcher).to(getSender());
                    })
                    .build();
        }
    }