package org.gt.chat.main.service;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.SupervisorStrategy;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.DeciderBuilder;
import com.google.common.collect.ImmutableList;
import org.gt.chat.domain.HealthCheckRequest;
import org.gt.chat.domain.HealthCheckResponse;
import org.gt.chat.main.audit.domain.AuditEvent;
import org.gt.chat.main.domain.ContentType;
import org.gt.chat.main.domain.ConversationEntity;
import org.gt.chat.main.domain.api.ConversationRequest;
import org.gt.chat.main.domain.api.GetConversationResponse;
import org.gt.chat.main.domain.dto.ConversationSaveDTO;
import org.gt.chat.main.repos.ConversationRepository;
import scala.concurrent.ExecutionContextExecutor;
import scala.concurrent.duration.Duration;

import java.util.List;
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
    private final CompletionStage<ActorRef> auditRef;
    private ConversationRepository conversationRepository;

    public ConversationActor(
            Function<akka.actor.ActorContext,
            CompletionStage<ActorRef>> auditProvider,
            ConversationRepository conversationRepository) {
        this.auditRef = auditProvider.apply(this.getContext());
        this.conversationRepository = conversationRepository;
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
                    List<ConversationEntity> conversationsFor =
                            conversationRepository.getConversationsFor(conversationRequest.getUserId());

                    GetConversationResponse build = GetConversationResponse.builder()
                            .globalRequestId(conversationRequest.getGlobalRequestId())
                            .messageDetails(
                                    conversationsFor.stream()
                                        .map(x -> GetConversationResponse.MessageDetail.builder()
                                                .content(x.getContent())
                                                .senderId(x.getSenderId())
                                                .recipientId(x.getRecipientId())
                                                .contentType(ContentType.valueOf(x.getContentType().toString()))
                                                .received(x.isReceived())
                                                .timestamp(x.getTimestamp())
                                                .build())
                                        .collect(Collectors.toList()))
                            .build();

                    pipe(CompletableFuture.completedFuture(build), dispatcher).to(getSender());
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
                    HealthCheckResponse healthCheckResponse;

                    if (auditRef.toCompletableFuture().isDone()) {
                        healthCheckResponse = ask(auditRef.toCompletableFuture().get(), healthCheckRequest, 5000)
                                .thenCompose(result -> CompletableFuture.supplyAsync(() ->
                                        ((HealthCheckResponse) result)))
                                .toCompletableFuture().get();
                    } else {
                        healthCheckResponse = HealthCheckResponse.builder().name("audit").result("Could not find audit actor").build();
                    }

                    HealthCheckResponse result = HealthCheckResponse.builder().result("OK").name("ConversationActor")
                            .dependencies(ImmutableList.of(healthCheckResponse)).build();
                    pipe(CompletableFuture.completedFuture(result), dispatcher).to(getSender());
                })
                .match(ConversationSaveDTO.class, conversationSaveDTO -> {
//                    CompletionStage<Object> saveConversationStage =
//                            ask(conversationRepositoryActor, conversationSaveDTO, 3000L);

//                    pipe(saveConversationStage, dispatcher).to(getSender());
                })
                .matchAny(o -> LOG.error("Received unknown message {}", o))
                .build();
    }
}
