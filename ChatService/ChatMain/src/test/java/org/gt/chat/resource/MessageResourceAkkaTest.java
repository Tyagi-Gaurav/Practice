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
import io.swagger.annotations.ApiOperation;
import org.gt.chat.exception.ErrorResponse;
import org.gt.chat.exception.MessageExceptionHandler;
import org.gt.chat.mockActors.TestMessageActor;
import org.gt.chat.response.Conversation;
import org.gt.chat.response.ConversationType;
import org.gt.chat.response.Conversations;
import org.junit.Ignore;
import org.junit.Test;
import scala.reflect.api.Annotations;

import javax.ws.rs.Path;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageResourceAkkaTest extends JUnitRouteTest {
    private MessageExceptionHandler messageExceptionHandler = new MessageExceptionHandler();
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
    private MessageResourceAkka messageResource = new MessageResourceAkka(messageActor, messageExceptionHandler);
    TestRoute route = testRoute(messageResource.getRoute());

    @Test
    public void getMessagesForUser() {
        //When & Then
        TestRouteResult run = route.run((HttpRequest.GET("/conversations/2")));
        run.assertStatusCode(200)
                .assertContentType(ContentTypes.APPLICATION_JSON);

        Conversations entity = run.entity(Jackson.unmarshaller(Conversations.class));
        assertThat(expectedMessages).isEqualTo(entity);
    }

    @Test
    public void shouldThrowExceptionWhenUserNotFound() {
        //When & Then
        int userId = 23994;
        TestRouteResult run = route.run((HttpRequest.GET("/conversations/" + userId)));
        run.assertStatusCode(404)
                .assertContentType(ContentTypes.APPLICATION_JSON);

        ErrorResponse entity = run.entity(Jackson.unmarshaller(ErrorResponse.class));
        assertThat(entity.getCode()).isEqualTo("CHT_0001");
        assertThat(entity.getDescription()).isEqualTo("Invalid User: " + userId);
    }

    @Test
    public void shouldGenerateSwaggerDocumentationForRoute() throws Exception {
        //Given
        Method conversationRouteMethod = messageResource.getClass().getMethod("conversationsRoute", null);
        assertThat(conversationRouteMethod.isAnnotationPresent(Path.class)).isTrue();
        assertThat(conversationRouteMethod.getAnnotation(Path.class).value()).isEqualTo("/conversations/{userId}");

        assertThat(conversationRouteMethod.isAnnotationPresent(ApiOperation.class)).isTrue();
        assertThat(conversationRouteMethod.getAnnotation(ApiOperation.class).value()).isEqualTo("Return conversations for a user");
        assertThat(conversationRouteMethod.getAnnotation(ApiOperation.class).code()).isEqualTo(200);
        assertThat(conversationRouteMethod.getAnnotation(ApiOperation.class).httpMethod()).isEqualTo("GET");
        assertThat(conversationRouteMethod.getAnnotation(ApiOperation.class).response()).isEqualTo(Conversations.class);

        //Then
    }
}