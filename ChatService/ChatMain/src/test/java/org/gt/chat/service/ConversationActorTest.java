package org.gt.chat.service;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;
import org.gt.chat.domain.ConversationAggregate;
import org.gt.chat.domain.ConversationEntity;
import org.gt.chat.response.Conversation;
import org.gt.chat.response.ConversationType;
import org.gt.chat.response.Conversations;
import org.gt.chat.util.ActorSystemTest;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;

public class ConversationActorTest extends ActorSystemTest {
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

//        Mockito.when(repository.getMessages(userId)).thenReturn(aggregate);

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
            final Props props = Props.create(ConversationActor.class);
            final ActorRef subject = actorSystem.actorOf(props);

            subject.tell("1", getRef());

            expectMsg(duration("5 second"), conversations);
        }};
    }


    @Test
    public void shouldBeWatchingRepository() {
        fail();
    }

    @Test
    public void shouldBeSupervisorForRepository() {
        fail();
    }

}