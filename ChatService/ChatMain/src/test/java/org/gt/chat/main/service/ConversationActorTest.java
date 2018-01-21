package org.gt.chat.main.service;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.testkit.TestActor;
import akka.testkit.TestProbe;
import akka.testkit.javadsl.TestKit;
import org.gt.chat.domain.HealthCheckRequest;
import org.gt.chat.domain.HealthCheckResponse;
import org.gt.chat.main.domain.ConversationRequest;
import org.gt.chat.main.audit.exception.InvalidUserException;
import org.gt.chat.main.domain.*;
import org.gt.chat.main.util.ActorSystemTest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static akka.pattern.PatternsCS.ask;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

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
    private static final String ACTOR_NAME = "ConversationActor";

    @Before
    public void setUp() throws Exception {
        auditTestProbe = new TestProbe(actorSystem);
        CompletableFuture<ActorRef> actorRefCompletableFuture = new CompletableFuture<>();
        actorRefCompletableFuture.complete(auditTestProbe.ref());
        when(auditRefCompletionStage.whenCompleteAsync(any(BiConsumer.class)))
                .thenReturn(actorRefCompletableFuture);

        when(auditProvider.apply(any(ActorContext.class))).thenReturn(auditRefCompletionStage);
    }

    @Test
    public void getMessagesForUser() {
        // Given
        Conversations conversations = getConversations();

        //When
        new TestKit(actorSystem) {{
            final Props props = Props.create(ConversationActor.class, auditProvider);
            final ActorRef subject = actorSystem.actorOf(props);

            subject.tell(userWithId("2"), getRef());

            expectMsg(duration("5 second"), conversations);
            verify(auditRefCompletionStage).whenCompleteAsync(any(BiConsumer.class));
        }};
    }

    @Test
    public void shouldNotFailWhenASuccessCallIsMadeAfterError() throws ExecutionException, InterruptedException {
        //When
        new TestKit(actorSystem) {{
            final Props props = Props.create(ConversationActor.class, auditProvider);
            final ActorRef subject = actorSystem.actorOf(props);

            CompletionStage<Object> ask1 = ask(subject, userWithId("193"), 5000);
            try {
                ask1.toCompletableFuture().get();
                fail("Should have failed");
            } catch (Exception e) {
                assertThat(e.getCause()).isExactlyInstanceOf(InvalidUserException.class);
                assertThat(e.getMessage()).isEqualTo("org.gt.chat.main.audit.exception.InvalidUserException: 193");
            }

            CompletionStage<Object> ask2 = ask(subject, userWithId(VALID_USER_ID), 5000);
            assertThat(ask2.toCompletableFuture().get()).isEqualTo(getConversations());
            verify(auditRefCompletionStage, times(2)).whenCompleteAsync(any(BiConsumer.class));
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

            final Props props = Props.create(ConversationActor.class, auditProvider);
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

    @Ignore
    public void shouldRestartActorWhenItFailsWithException() {
        //When
        new TestKit(actorSystem) {{
            final Props props = Props.create(ConversationActor.class, auditRefCompletionStage);
            final ActorRef subject = actorSystem.actorOf(props);
            subject.tell("", getRef());

            expectMsg(IllegalArgumentException.class);
        }};
    }

    private Conversations getConversations() {
        Conversation expectedMessage = new Conversation(
                VALID_USER_ID,
                234878234L,
                ConversationType.ONE2ONE,
                "groupId",
                "senderId",
                "Hello World");
        List<Conversation> conversationList = new ArrayList<>();
        conversationList.add(expectedMessage);
        return new Conversations(GLOBAL_REQUEST_ID, conversationList);



    }

    private ConversationRequest userWithId(String userId) {
        return ConversationRequest
                .builder()
                .globalRequestId(GLOBAL_REQUEST_ID)
                .userId(userId).build();
    }

}