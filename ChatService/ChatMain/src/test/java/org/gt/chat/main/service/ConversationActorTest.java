package org.gt.chat.main.service;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.testkit.TestActor;
import akka.testkit.TestProbe;
import akka.testkit.javadsl.TestKit;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.gt.chat.domain.HealthCheckRequest;
import org.gt.chat.domain.HealthCheckResponse;
import org.gt.chat.main.domain.ContentType;
import org.gt.chat.main.domain.dto.ConversationSaveDTO;
import org.gt.chat.main.exception.InvalidUserException;
import org.gt.chat.main.domain.ConversationEntity;
import org.gt.chat.main.domain.api.ConversationRequest;
import org.gt.chat.main.domain.api.GetConversationResponse;
import org.gt.chat.main.util.ActorSystemTest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static akka.pattern.PatternsCS.ask;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConversationActorTest extends ActorSystemTest {

    @Mock
    private CompletionStage<ActorRef> auditRefCompletionStage;

    @Mock
    private CompletableFuture<ActorRef> actorRefCompletableFuture;

    @Mock
    private Function<ActorContext, CompletionStage<ActorRef>> auditProvider;

    private static final String GLOBAL_REQUEST_ID = "Test-request-Id";
    private static final String VALID_USER_ID = "2";
    private TestProbe auditTestProbe;
    private TestProbe conversationRepoActorProbe;
    private static final String ACTOR_NAME = "ConversationActor";
    private Props props;

    @Before
    public void setUp() throws Exception {
        auditTestProbe = new TestProbe(actorSystem);
        conversationRepoActorProbe = new TestProbe(actorSystem);
        CompletableFuture<ActorRef> actorRefCompletableFuture = new CompletableFuture<>();
        actorRefCompletableFuture.complete(auditTestProbe.ref());
        when(auditRefCompletionStage.whenCompleteAsync(any(BiConsumer.class)))
                .thenReturn(actorRefCompletableFuture);

        when(auditProvider.apply(any(ActorContext.class))).thenReturn(auditRefCompletionStage);

        conversationRepoActorProbe.setAutoPilot(new TestActor.AutoPilot() {
            @Override
            public TestActor.AutoPilot run(ActorRef sender, Object msg) {
                if (msg.equals("2")) {
                    sender.tell(getConversationEntity(), ActorRef.noSender());
                    return noAutoPilot();
                } else {
                    throw new InvalidUserException(msg.toString());
                }
            }
        });

        props = Props.create(ConversationActor.class, auditProvider, conversationRepoActorProbe.ref());
    }

    @Test
    public void getMessagesForUser() {
        //Given
        GetConversationResponse conversations = getExpectedConversations();

        //When
        new TestKit(actorSystem) {{
            final ActorRef subject = actorSystem.actorOf(props);

            subject.tell(userWithId("2"), getRef());

            expectMsg(duration("5 second"), conversations);
            verify(auditRefCompletionStage).whenCompleteAsync(any(BiConsumer.class));
            conversationRepoActorProbe.expectMsg("2");
        }};
    }

    @Test
    public void healthCheckShouldBeSuccessfulWhenActorIsRunning() throws Exception {
        //Given
        HealthCheckResponse dependenciesHealthCheck =
                HealthCheckResponse.builder().name("audit").result("OK").build();

        when(auditRefCompletionStage.toCompletableFuture()).thenReturn(actorRefCompletableFuture);
        when(actorRefCompletableFuture.get()).thenReturn(auditTestProbe.ref());
        when(actorRefCompletableFuture.isDone()).thenReturn(true);

        //When
        new TestKit(actorSystem) {{
            auditTestProbe.setAutoPilot(new TestActor.AutoPilot() {
                @Override
                public TestActor.AutoPilot run(ActorRef sender, Object msg) {
                    sender.tell(dependenciesHealthCheck, ActorRef.noSender());
                    return noAutoPilot();
                }
            });

            final ActorRef subject = actorSystem.actorOf(props);

            CompletionStage<Object> ask = ask(subject, HealthCheckRequest.builder().build(), 5000);
            Object healthCheckResult = ask.toCompletableFuture().get();

            assertThat(healthCheckResult).isInstanceOf(HealthCheckResponse.class);
            HealthCheckResponse healthCheckResponse = (HealthCheckResponse) healthCheckResult;
            assertThat(healthCheckResponse.getName()).isEqualTo(ACTOR_NAME);
            assertThat(healthCheckResponse.getResult()).isEqualTo("OK");
            auditTestProbe.expectMsgClass(HealthCheckRequest.class);
        }};
    }

    @Test
    public void saveConversationForUserInDatabase() throws Exception {
        //given
        conversationRepoActorProbe.setAutoPilot(new TestActor.AutoPilot() {
            @Override
            public TestActor.AutoPilot run(ActorRef sender, Object msg) {
                sender.tell(Boolean.TRUE, ActorRef.noSender());
                return noAutoPilot();
            }
        });

        //when
        new TestKit(actorSystem) {{
            final ActorRef subject = actorSystem.actorOf(props);

            ConversationSaveDTO msg = saveDTO();
            subject.tell(msg, getRef());

            expectMsg(Boolean.TRUE);
            conversationRepoActorProbe.expectMsg(msg);
        }};
    }

    @Ignore
    public void shouldRestartActorWhenItFailsWithException() {
        //When
        new TestKit(actorSystem) {{
            final ActorRef subject = actorSystem.actorOf(props);
            subject.tell("", getRef());

            expectMsg(IllegalArgumentException.class);
        }};
    }

    private GetConversationResponse getExpectedConversations() {
        return GetConversationResponse.builder()
                .globalRequestId(GLOBAL_REQUEST_ID)
                .userId(VALID_USER_ID)
                .messages(GetConversationResponse.Messages.builder()
                        .senderId("senderId")
                        .messageDetails(asList(GetConversationResponse.MessageDetail.builder()
                                .received(true)
                                .timestamp(234878234L)
                                .content("Hello World")
                                .contentType(ContentType.TEXT_PLAIN_UTF8)
                                .build()))
                        .build())
                .build();
    }

    private ConversationEntity getConversationEntity() {
        return ConversationEntity.builder()
                .userId(VALID_USER_ID)
                .messages(ConversationEntity.Messages.builder()
                        .senderId("senderId")
                        .messageDetails(asList(ConversationEntity.MessageDetailEntity.builder()
                                .received(true)
                                .timestamp(234878234L)
                                .content("Hello World")
                                .contentType(ConversationEntity.ContentTypeEntity.TEXT_PLAIN_UTF8)
                                .build()))
                        .build())
                .build();
    }

    private ConversationRequest userWithId(String userId) {
        return ConversationRequest
                .builder()
                .globalRequestId(GLOBAL_REQUEST_ID)
                .userId(userId).build();
    }

    private ConversationSaveDTO saveDTO() {
        return ConversationSaveDTO.builder().build();
    }

}