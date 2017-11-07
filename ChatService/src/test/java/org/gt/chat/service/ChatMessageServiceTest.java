package org.gt.chat.service;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;
import org.gt.chat.domain.MessageAggregate;
import org.gt.chat.domain.MessageEntity;
import org.gt.chat.repos.MessageRepository;
import org.gt.chat.response.Message;
import org.gt.chat.response.Messages;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import scala.concurrent.duration.FiniteDuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ChatMessageServiceTest {
    private static final FiniteDuration shutdownDuration =
            scala.concurrent.duration.Duration.apply(1L, TimeUnit.SECONDS);
    private MessageRepository repository = mock(MessageRepository.class);
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
        MessageEntity messageEntity = new MessageEntity(
                2L,
                "Hello World",
                234878234L
                );
        List<MessageEntity> entityList = new ArrayList<>();
        entityList.add(messageEntity);
        MessageAggregate aggregate = new MessageAggregate(entityList);

        when(repository.getMessages(userId)).thenReturn(aggregate);

        Message expectedMessage = new Message(
                "2",
                "Hello World",
                234878234L);
        List<Message> messageList = new ArrayList<>();
        messageList.add(expectedMessage);
        Messages messages = new Messages(messageList);

        new TestKit(actorSystem) {{
            final Props props = Props.create(MessageActor.class, repository);
            final ActorRef subject = actorSystem.actorOf(props);

            //final TestKit probe = new TestKit(actorSystem);
            subject.tell("1", getRef());

            expectMsg(duration("5 second"), messages);
        }};

//        new ActorTest()
//            .with(actorSystem)
//            .forActor(MessageActor.class)
//            .withArguments(repository)
//            .execute((ref, baseRef) -> {
//                ref.tell("1", baseRef);
//            })
//            .expect(messages)
//            .build();

    }


}