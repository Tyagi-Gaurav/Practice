package com.example.iot;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class IOTSupervisor extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    static Props props() {
        return Props.create(IOTSupervisor.class, IOTSupervisor::new);
    }

    @Override
    public void preStart() throws Exception {
        log.info("IOT Application started");
    }

    @Override
    public void postStop() throws Exception {
        log.info("IOT Application stopped");
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().build();
    }
}
