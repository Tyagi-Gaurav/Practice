package org.gt.chat;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.ContentTypes;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.testkit.JUnitRouteTest;
import akka.http.javadsl.testkit.TestRoute;
import akka.http.javadsl.testkit.TestRouteResult;
import org.gt.chat.exception.ErrorResponse;
import org.gt.chat.exception.MessageExceptionHandler;
import org.gt.chat.resource.MessageResourceAkka;
import org.gt.chat.response.Conversation;
import org.gt.chat.response.ConversationType;
import org.gt.chat.response.Conversations;
import org.gt.chat.service.ConversationActor;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ChatEndToEndTest extends JUnitRouteTest {
    private MessageExceptionHandler messageExceptionHandler = new MessageExceptionHandler();
    ActorSystem actorSystem = ActorSystem.create();
    ActorRef actorRef = actorSystem.actorOf(Props.create(ConversationActor.class));
    private MessageResourceAkka messageResource = new MessageResourceAkka(actorRef, messageExceptionHandler);

    TestRoute route = testRoute(messageResource.route);

    @Test
    public void getConversationsForAUser() throws IOException {
        //Given
        List<Conversation> conversationList = Arrays.asList(
                new Conversation("2",
                        234878234L,
                        ConversationType.ONE2ONE,
                        "groupId",
                        "senderId",
                        "Hello World"));
        Conversations expectedMessages = new Conversations(conversationList);

        //When
        TestRouteResult run = route.run((HttpRequest.GET("/conversations/2")));
        run.assertStatusCode(200)
            .assertContentType(ContentTypes.APPLICATION_JSON);

        Conversations entity = run.entity(Jackson.unmarshaller(Conversations.class));
        assertThat(expectedMessages).isEqualTo(entity);
    }

    @Test
    public void return_NotFound_when_user_not_found() {
        //When
        TestRouteResult run = route.run((HttpRequest.GET("/conversations/345")));
        run.assertStatusCode(404)
              .assertContentType(ContentTypes.APPLICATION_JSON);

        ErrorResponse entity = run.entity(Jackson.unmarshaller(ErrorResponse.class));
        assertThat(entity.getCode()).isEqualTo("CHT_0001");
        assertThat(entity.getDescription()).isEqualTo("Invalid User: 345");
    }
}