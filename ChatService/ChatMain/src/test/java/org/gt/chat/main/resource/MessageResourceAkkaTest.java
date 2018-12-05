package org.gt.chat.main.resource;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.ContentTypes;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.testkit.JUnitRouteTest;
import akka.http.javadsl.testkit.TestRoute;
import akka.http.javadsl.testkit.TestRouteResult;
import akka.testkit.TestActor;
import akka.testkit.TestProbe;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.jaxrs.config.DefaultReaderConfig;
import org.gt.chat.main.domain.ContentType;
import org.gt.chat.main.domain.api.GetConversationResponse;
import org.gt.chat.main.domain.api.SendConversationRequest;
import org.gt.chat.main.domain.dto.ConversationSaveDTO;
import org.gt.chat.main.exception.ErrorResponse;
import org.gt.chat.main.exception.InvalidUserException;
import org.gt.chat.main.exception.MessageExceptionHandler;
import org.gt.chat.main.util.StringBasedHeader;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.Path;
import java.lang.reflect.Method;
import java.util.UUID;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class MessageResourceAkkaTest extends JUnitRouteTest {
    private final String GLOBAL_REQUEST_ID = "global-request-id";

    private MessageExceptionHandler messageExceptionHandler = new MessageExceptionHandler();
    private String requestId = "Test-Request-Id";
    private final GetConversationResponse expectedMessages = GetConversationResponse.builder()
            .globalRequestId(GLOBAL_REQUEST_ID)
            .messageDetails(asList(GetConversationResponse.MessageDetail.builder()
                .senderId("senderId")
                .recipientId("recipientId")
                .received(true)
                .timestamp(234878234L)
                .content("Hello World")
                .contentType(ContentType.TEXT_PLAIN_UTF8)
                .build()))
            .build();

    private ActorSystem actorSystem = ActorSystem.create(UUID.randomUUID().toString());
    private TestProbe messageActor = new TestProbe(actorSystem);

    private MessageResourceAkka messageResource;
    private TestRoute route;

    @Before
    public void setUp() throws Exception {
        DefaultReaderConfig readerConfig = new DefaultReaderConfig();
        DocumentationRoute documentationRoute = new DocumentationRoute(readerConfig);
        messageResource = new MessageResourceAkka(messageActor.ref(), messageExceptionHandler, documentationRoute);
        route = testRoute(messageResource.getRoute());

        messageActor.setAutoPilot(new TestActor.AutoPilot() {
            @Override
            public TestActor.AutoPilot run(ActorRef sender, Object msg) {
                sender.tell(getExpectedConversations(), ActorRef.noSender());
                return noAutoPilot();
            }
        });
    }

    @Test
    public void getMessagesForUser() {
        //When & Then
        TestRouteResult run = route.run((HttpRequest.GET("/conversations/2")
                .addHeader(new StringBasedHeader("X-request-Id", requestId))));
        run.assertStatusCode(200)
                .assertContentType(ContentTypes.APPLICATION_JSON);

        GetConversationResponse response = run.entity(Jackson.unmarshaller(GetConversationResponse.class));
        assertThat(expectedMessages).isEqualTo(response);
    }

    @Test
    public void shouldGenerateANewRequestIdWhenNoRequestIdProvided() {
        //When & Then
        TestRouteResult run = route.run((HttpRequest.GET("/conversations/2")));
        run.assertStatusCode(200)
                .assertContentType(ContentTypes.APPLICATION_JSON);

        GetConversationResponse entity = run.entity(Jackson.unmarshaller(GetConversationResponse.class));
        assertThat(entity.getGlobalRequestId()).isNotEmpty();
    }

    @Test
    public void shouldSendMessageFromOneUserToAnother() throws Exception {
        //When
        ObjectMapper objectMapper = new ObjectMapper();
        TestRouteResult run = route.run((HttpRequest.POST("/conversations")
                .withEntity(ContentTypes.APPLICATION_JSON, objectMapper.writeValueAsString(SendConversationRequest.builder()
                        .senderId("a")
                        .recipientUserId("b")
                        .content("Hello World")
                        .contentType(ContentType.APPLICATION_JSON)
                            .build()))
        ));

        ConversationSaveDTO expectedDTO = ConversationSaveDTO.builder()
                .senderId("a")
                .recipientId("b")
                .content("Hello World")
                .contentType(ContentType.APPLICATION_JSON)
                .build();

        //Then
        run.assertStatusCode(202);
        messageActor.expectMsg(expectedDTO);
    }

    @Test
    public void shouldThrowErrorWhenContentTypeDoesNotMatch() throws Exception {
        //When
        ObjectMapper objectMapper = new ObjectMapper();
        TestRouteResult run = route.run((HttpRequest.POST("/conversations")
                .withEntity(objectMapper.writeValueAsString(SendConversationRequest.builder().build()))));

        //Then
        run.assertStatusCode(415);
    }

    @Ignore
    public void shouldThrowExceptionWhenUserNotFound() {
        //Given
        messageActor.setAutoPilot(new TestActor.AutoPilot() {
            @Override
            public TestActor.AutoPilot run(ActorRef sender, Object msg) {
               throw new InvalidUserException("");
            }
        });

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
        Method conversationRouteMethod = messageResource.getClass().getMethod("getConversationsRoute", null);
        assertThat(conversationRouteMethod.isAnnotationPresent(Path.class)).isTrue();
        assertThat(conversationRouteMethod.getAnnotation(Path.class).value()).isEqualTo("/conversations/{userId}");

        assertThat(conversationRouteMethod.isAnnotationPresent(ApiOperation.class)).isTrue();
        assertThat(conversationRouteMethod.getAnnotation(ApiOperation.class).value()).isEqualTo("Return conversations for a user");
        assertThat(conversationRouteMethod.getAnnotation(ApiOperation.class).code()).isEqualTo(200);
        assertThat(conversationRouteMethod.getAnnotation(ApiOperation.class).httpMethod()).isEqualTo("GET");
        assertThat(conversationRouteMethod.getAnnotation(ApiOperation.class).response()).isEqualTo(GetConversationResponse.class);
    }

    private GetConversationResponse getExpectedConversations() {
        return GetConversationResponse.builder()
                .globalRequestId(GLOBAL_REQUEST_ID)
                .messageDetails(asList(GetConversationResponse.MessageDetail.builder()
                        .senderId("senderId")
                        .recipientId("recipientId")
                        .received(true)
                        .timestamp(234878234L)
                        .content("Hello World")
                        .contentType(ContentType.TEXT_PLAIN_UTF8)
                        .build()))
                .build();
    }
}