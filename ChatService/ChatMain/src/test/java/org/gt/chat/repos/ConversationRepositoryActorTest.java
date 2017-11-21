package org.gt.chat.repos;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;
import org.gt.chat.domain.ConversationAggregate;
import org.gt.chat.domain.ConversationEntity;
import org.gt.chat.util.ActorSystemTest;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import static akka.pattern.PatternsCS.ask;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public class ConversationRepositoryActorTest extends ActorSystemTest {
    @Test
    public void getMessagesForUser() {
        // Given
        ConversationEntity conversationEntity = new ConversationEntity(
                2L,
                "Hello World",
                234878234L,
                "groupId",
                "senderId");
        List<ConversationEntity> entityList = new ArrayList<>();
        entityList.add(conversationEntity);
        ConversationAggregate aggregate = new ConversationAggregate(entityList);

        new TestKit(actorSystem) {{
            final Props props = Props.create(ConversationRepositoryActor.class);
            final ActorRef subject = actorSystem.actorOf(props);

            subject.tell("2", getRef());

            expectMsg(duration("5 second"), aggregate);
        }};
    }

    @Test
    public void shouldThrowExceptionWhenInvalidDataIsPassed() throws ExecutionException, InterruptedException {
        //When
        new TestKit(actorSystem) {{
            final Props props = Props.create(ConversationRepositoryActor.class);
            final ActorRef subject = actorSystem.actorOf(props);

            CompletionStage<Object> ask = ask(subject, "193", 5000);
            try {
                ask.toCompletableFuture().get();
                fail("Should have failed");
            } catch (Exception e) {
                assertThat(e.getCause()).isExactlyInstanceOf(IllegalArgumentException.class);
                assertThat(e.getMessage()).isEqualTo("java.lang.IllegalArgumentException: Invalid User 193");
            }
        }};
    }
}