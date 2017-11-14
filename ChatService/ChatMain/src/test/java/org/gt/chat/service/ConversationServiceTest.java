package org.gt.chat.service;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;
import org.gt.chat.domain.ConversationAggregate;
import org.gt.chat.domain.ConversationEntity;
import org.gt.chat.repos.MessageRepository;
import org.gt.chat.response.Conversation;
import org.gt.chat.response.ConversationType;
import org.gt.chat.response.Conversations;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import scala.concurrent.duration.FiniteDuration;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class ConversationServiceTest {
    private static final FiniteDuration shutdownDuration =
            scala.concurrent.duration.Duration.apply(1L, TimeUnit.SECONDS);
    private MessageRepository repository = Mockito.mock(MessageRepository.class);
    static ActorSystem actorSystem;

    @BeforeClass
    public static void startActorSystem() {
        actorSystem = ActorSystem.create("Test-Actor-System");
    }

    @AfterClass
    public static void stopSystemAfterAllTests() {
        TestKit.shutdownActorSystem(actorSystem,
                shutdownDuration,
                true);
        actorSystem = null;
    }

    @Test
    public void getMessagesForUser() {
        // Given
        String userId = "1";
        ConversationEntity conversationEntity = new ConversationEntity(
                2L,
                "Hello World",
                234878234L,
                "groupId",
                "senderId");
        List<ConversationEntity> entityList = new ArrayList<>();
        entityList.add(conversationEntity);
        ConversationAggregate aggregate = new ConversationAggregate(entityList);

        Mockito.when(repository.getMessages(userId)).thenReturn(aggregate);

        Conversation expectedMessage = new Conversation(
                "2",
                234878234L,
                ConversationType.ONE2ONE,
                "groupId",
                "senderId",
                "Hello World");
        List<Conversation> conversationList = new ArrayList<>();
        conversationList.add(expectedMessage);
        Conversations conversations = new Conversations(conversationList);

        new TestKit(actorSystem) {{
            final Props props = Props.create(ConversationActor.class, repository);
            final ActorRef subject = actorSystem.actorOf(props);

            subject.tell("1", getRef());

            expectMsg(duration("5 second"), conversations);
        }};

//        new ActorTest()
//            .with(actorSystem)
//            .forActor(ConversationActor.class)
//            .withArguments(repository)
//            .execute((ref, baseRef) -> {
//                ref.tell("1", baseRef);
//            })
//            .expect(messages)
//            .build();

    }


}