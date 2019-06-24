package com.lightbend.akka.sample;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class StartStopActors {
    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create();
        ActorRef first = actorSystem.actorOf(StartStopActor1.props(), "first");
        first.tell("stop", ActorRef.noSender());
    }
}

class StartStopActor1 extends AbstractActor {

    static Props props() {
        return Props.create(StartStopActor1.class, StartStopActor1::new);
    }

    @Override
    public void preStart() throws Exception {
        System.out.println("First started");
        getContext().actorOf(StartStopActor2.props(), "second");
    }

    @Override
    public void postStop() throws Exception {
        System.out.println("First stopped");
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchEquals("stop", p-> {
                    getContext().stop(getSelf());
                })
                .build();
    }
}

class StartStopActor2 extends AbstractActor {

    static Props props() {
        return Props.create(StartStopActor2.class, StartStopActor2::new);
    }

    @Override
    public void preStart() throws Exception {
        System.out.println("Second started");
    }

    @Override
    public void postStop() throws Exception {
        System.out.println("Second stopped");
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().build(); //Empty behaviour
    }
}