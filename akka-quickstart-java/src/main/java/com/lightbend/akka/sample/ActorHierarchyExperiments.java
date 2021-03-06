package com.lightbend.akka.sample;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import java.io.IOException;

public class ActorHierarchyExperiments {
    public static void main(String[] args) throws IOException {
        ActorSystem testSystem = ActorSystem.create("testSystem");

        ActorRef firstRef = testSystem.actorOf(PrintMyActorref.props(), "first-actor");
        System.out.println("First: " + firstRef);
        firstRef.tell("printit", ActorRef.noSender());

        System.out.println(">>> Press ENTER to exit <<<");

        try {
            System.in.read();
        } finally {
            testSystem.terminate();
        }
    }
}

class PrintMyActorref extends AbstractActor {

    static Props props() {
        return Props.create(PrintMyActorref.class, PrintMyActorref::new);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchEquals("printit", p -> {
                    ActorRef actorRef = getContext().actorOf(Props.empty(), "second-actor");
                    System.out.println(actorRef);
                }).build();
    }
}
