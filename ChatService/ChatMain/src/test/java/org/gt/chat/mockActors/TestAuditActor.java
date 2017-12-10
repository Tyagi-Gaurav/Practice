package org.gt.chat.mockActors;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class TestAuditActor extends AbstractActor {
    private final LoggingAdapter LOG = Logging.getLogger(this.getContext().getSystem(), this);

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchAny(o -> LOG.info("Audit Actor called with " + o))
                .build();
    }
}
