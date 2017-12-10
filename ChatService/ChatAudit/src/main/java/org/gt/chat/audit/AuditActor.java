package org.gt.chat.audit;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import org.gt.chat.audit.domain.AuditEvent;
import org.gt.chat.audit.exception.InvalidAuditEventException;
import scala.concurrent.ExecutionContextExecutor;

import java.util.concurrent.CompletableFuture;

import static akka.pattern.PatternsCS.pipe;

public class AuditActor extends AbstractActor {
    private ExecutionContextExecutor dispatcher = this.getContext().getSystem().dispatcher();
    private final LoggingAdapter LOG = Logging.getLogger(this.getContext().getSystem(), this);

    @Override
    public Receive createReceive() {
        return receiveBuilder()
            .match(AuditEvent.class, auditEvent -> {
                CompletableFuture<Boolean> auditResult =
                        CompletableFuture.supplyAsync(() -> {
                            if (auditEvent.getAuditEventType() == null) {
                                throw new InvalidAuditEventException("Invalid Audit Event: " + auditEvent.getAuditEventType());
                            }
                            return true;
                        });
                pipe(auditResult, dispatcher).to(getSender());
            })
            .matchAny(o -> LOG.error("Received unknown message {}", o))
            .build();
    }
}