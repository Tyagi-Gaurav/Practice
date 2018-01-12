package org.gt.chat.main.service;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.testkit.TestProbe;
import akka.testkit.javadsl.TestKit;
import org.gt.chat.main.audit.domain.ConversationRequest;
import org.gt.chat.main.audit.exception.InvalidUserException;
import org.gt.chat.main.response.Conversation;
import org.gt.chat.main.response.ConversationType;
import org.gt.chat.main.response.Conversations;
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
    private Function<ActorContext, CompletionStage<ActorRef>> auditProvider;

    private static final String GLOBAL_REQUEST_ID = "Test-request-Id";
    private static final String VALID_USER_ID = "2";
    private TestProbe auditTestProbe;

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