package org.gt.chat.resource;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.ContentTypes;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.testkit.JUnitRouteTest;
import akka.http.javadsl.testkit.TestRoute;
import akka.http.javadsl.testkit.TestRouteResult;
import org.gt.chat.response.Conversation;
import org.gt.chat.response.ConversationType;
import org.gt.chat.response.Conversations;
import org.junit.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.TimeZone;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageResourceAkkaTest extends JUnitRouteTest {
    private final Conversations expectedMessages = new Conversations(Arrays.asList(
            new Conversation(
                    "2",
                    234878234L,
                    ConversationType.GROUP,
                    "groupId",
                    "senderId",
                    "Hello World")
    ));
    private ActorSystem actorSystem = ActorSystem.create("Test");
    private ActorRef messageActor =
            actorSystem.actorOf(Props.create(TestMessageActor.class));
    private MessageResourceAkka messageResource = new MessageResourceAkka(messageActor);
    TestRoute route = testRoute(messageResource.route);

    @Test
    public void getMessagesForUser() {
        //When & Then
        TestRouteResult run = route.run((HttpRequest.GET("/conversations/1")));
        run.assertStatusCode(200)
                .assertContentType(ContentTypes.APPLICATION_JSON);

        Conversations entity = run.entity(Jackson.unmarshaller(Conversations.class));
        assertThat(expectedMessages).isEqualTo(entity);
    }


}