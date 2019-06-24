package com.lightbend.akka.sample;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class SupervisorStrategy {
    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create();
        ActorRef supervisingActor = actorSystem.actorOf(SupervisingActor.props(), "supervising-actor");

        supervisingActor.tell("failChild", ActorRef.noSender());
    }
}

class SupervisingActor extends AbstractActor {

    static Props props() {
        return Props.create(SupervisingActor.class, SupervisingActor::new);
    }

    ActorRef child = getContext().actorOf(SupervisedActor.props(), "supervisedActor");

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchEquals("failChild", f -> {
                    child.tell("fail", getSelf());
                })
                .build();
    }
}

class SupervisedActor extends AbstractActor {
    static Props props() {
        return Props.create(SupervisedActor.class, SupervisedActor::new);
    }

    @Override
    public void preStart() throws Exception {
        System.out.println("supervised actor started");
    }

    @Override
    public void postStop() throws Exception {
        System.out.println("supervised actor stopped");
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchEquals("fail", f -> {
                    System.out.println("supervised actor fails now");
                    throw new Exception("I failed!");
                })
                .build();
    }
}
