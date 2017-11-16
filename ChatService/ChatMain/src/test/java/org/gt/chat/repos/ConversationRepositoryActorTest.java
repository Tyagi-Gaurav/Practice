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

public class ConversationRepositoryActorTest extends ActorSystemTest {
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

        new TestKit(actorSystem) {{
            final Props props = Props.create(ConversationRepositoryActor.class);
            final ActorRef subject = actorSystem.actorOf(props);

            subject.tell("1", getRef());

            expectMsg(duration("5 second"), aggregate);
        }};
    }
}