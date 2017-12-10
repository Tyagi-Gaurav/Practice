package org.gt.chat.service;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;
import org.gt.chat.exception.InvalidUserException;
import org.gt.chat.response.Conversation;
import org.gt.chat.response.ConversationType;
import org.gt.chat.response.Conversations;
import org.gt.chat.util.ActorSystemTest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import scala.Option;
import scala.concurrent.Future;
import scala.util.Try;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.function.BiConsumer;

import static akka.pattern.PatternsCS.ask;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ConversationActorTest extends ActorSystemTest {

    @Mock
    private CompletionStage<ActorRef> auditRefCompletionStage;

    @Before
    public void setUp() throws Exception {
        when(auditRefCompletionStage.whenCompleteAsync(any(BiConsumer.class)))
                .thenReturn(mock(CompletionStage.class));
    }

    @Test
    public void getMessagesForUser() {
        // Given
        Conversations conversations = getConversations();

        //When
        new TestKit(actorSystem) {{
            final Props props = Props.create(ConversationActor.class, auditRefCompletionStage);
            final ActorRef subject = actorSystem.actorOf(props);

            subject.tell("2", getRef());

            expectMsg(duration("5 second"), conversations);
            verify(auditRefCompletionStage).whenCompleteAsync(any(BiConsumer.class));
        }};
    }

    @Test
    public void shouldNotFailWhenASuccessCallIsMadeAfterError() throws ExecutionException, InterruptedException {
        //When
        new TestKit(actorSystem) {{
            final Props props = Props.create(ConversationActor.class, auditRefCompletionStage);
            final ActorRef subject = actorSystem.actorOf(props);

            CompletionStage<Object> ask1 = ask(subject, "193", 5000);
            try {
                ask1.toCompletableFuture().get();
                fail("Should have failed");
            } catch (Exception e) {
                assertThat(e.getCause()).isExactlyInstanceOf(InvalidUserException.class);
                assertThat(e.getMessage()).isEqualTo("org.gt.chat.exception.InvalidUserException: 193");
            }

            CompletionStage<Object> ask2 = ask(subject, "2", 5000);
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
                "2",
                234878234L,
                ConversationType.ONE2ONE,
                "groupId",
                "senderId",
                "Hello World");
        List<Conversation> conversationList = new ArrayList<>();
        conversationList.add(expectedMessage);
        return new Conversations(conversationList);
    }

}