package org.gt.chat.main.service;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.DeciderBuilder;
import org.gt.chat.main.audit.domain.AuditEvent;
import org.gt.chat.main.audit.domain.ConversationAggregate;
import org.gt.chat.main.audit.domain.ConversationRequest;
import org.gt.chat.main.domain.HealthCheckRequest;
import org.gt.chat.main.domain.HealthCheckResponse;
import org.gt.chat.main.repos.ConversationRepositoryActor;
import org.gt.chat.main.domain.Conversation;
import org.gt.chat.main.domain.Conversations;
import scala.concurrent.ExecutionContextExecutor;
import scala.concurrent.duration.Duration;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import static akka.actor.SupervisorStrategy.escalate;
import static akka.actor.SupervisorStrategy.resume;
import static akka.pattern.PatternsCS.ask;
import static akka.pattern.PatternsCS.pipe;
import static org.gt.chat.main.audit.domain.AuditEventType.MESSAGE_READ_EVENT;

public class ConversationActor extends AbstractActor {
    private final LoggingAdapter LOG = Logging.getLogger(this.getContext().getSystem(), this);
    private ExecutionContextExecutor dispatcher = this.getContext().getSystem().dispatcher();
    private ActorRef repoActor;
    private final CompletionStage<ActorRef> auditRef;

    public ConversationActor(Function<akka.actor.ActorContext, CompletionStage<ActorRef>> auditProvider) {
        this.auditRef = auditProvider.apply(this.getContext());
        repoActor = this.getContext()
                .actorOf(Props.create(ConversationRepositoryActor.class));
    }

    private static SupervisorStrategy strategy =
            new OneForOneStrategy(10,
                    Duration.create(1, TimeUnit.MINUTES),
                    DeciderBuilder
                            .match(IllegalArgumentException.class, e -> resume())
                            .matchAny(o -> escalate())
                            .build());

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ConversationRequest.class, conversationRequest -> {
                    CompletionStage<Conversations> listCompletionStage =
                            ask(repoActor, conversationRequest.getUserId(), 1000L)
                                    .thenCompose(x ->
                                            CompletableFuture.supplyAsync(() ->
                                                    new Conversations(conversationRequest.getGlobalRequestId(),
                                                            ((ConversationAggregate) x)
                                                                    .getMessageEntityList()
                                                                    .stream()
                                                                    .map(Conversation::from)
                                                                    .collect(Collectors.toList())))
                                    );
                    pipe(listCompletionStage, dispatcher).to(getSender());
                    auditRef.whenCompleteAsync((actorRef, throwable) -> {
                        if (actorRef != null) {
                            actorRef.tell(AuditEvent.builder()
                                    .eventPublishEpochTimeStamp(System.currentTimeMillis())
                                    .auditEventType(MESSAGE_READ_EVENT)
                                    .globalRequestId(conversationRequest.getGlobalRequestId())
                                    .build(), getSelf());
                        } else {
                            LOG.error("Audit ActorRef not available");
                        }
                    });
                }).match(HealthCheckRequest.class, healthCheckRequest -> {
                    HealthCheckResponse response =
                            HealthCheckResponse.builder().result("OK").name("ConversationActor").build();
                    CompletionStage<HealthCheckResponse> healthCheckResponseCompletableFuture =
                            CompletableFuture.completedFuture(response);
                    pipe(healthCheckResponseCompletableFuture, dispatcher).to(getSender());
                })
                .matchAny(o -> LOG.error("Received unknown message {}", o))
                .build();
    }
}
