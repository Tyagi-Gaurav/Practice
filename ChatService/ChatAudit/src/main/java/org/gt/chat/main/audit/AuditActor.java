package org.gt.chat.main.audit;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import org.gt.chat.main.audit.domain.AuditEvent;
import org.gt.chat.main.audit.domain.HealthCheckRequest;
import org.gt.chat.main.audit.domain.HealthCheckResponse;
import org.gt.chat.main.audit.exception.InvalidAuditEventException;
import scala.concurrent.ExecutionContextExecutor;

import java.util.concurrent.CompletableFuture;

import static akka.pattern.PatternsCS.pipe;
import static java.util.concurrent.CompletableFuture.*;

public class AuditActor extends AbstractActor {
    private ExecutionContextExecutor dispatcher = this.getContext().getSystem().dispatcher();
    private final LoggingAdapter LOG = Logging.getLogger(this.getContext().getSystem(), this);
    private ActorRef auditRepoRef;

    public AuditActor(ActorRef auditRepoRef) {
        this.auditRepoRef = auditRepoRef;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
            .match(AuditEvent.class, auditEvent -> {
                CompletableFuture<Boolean> auditResult =
                        supplyAsync(() -> {
                            if (auditEvent.getAuditEventType() == null) {
                                throw new InvalidAuditEventException("Invalid Audit Event: " + auditEvent.getAuditEventType());
                            }
                            auditRepoRef.tell(auditEvent, getSender());
                            return true;
                        });
                pipe(auditResult, dispatcher).to(getSender());
            })
            .match(HealthCheckRequest.class, healthCheckRequest -> {
                CompletableFuture<HealthCheckResponse> result = completedFuture(HealthCheckResponse.builder().result("OK").build());
                pipe(result, dispatcher).to(getSender());
            })
            .matchAny(o -> LOG.error("Received unknown message {}", o))
            .build();
    }
}
