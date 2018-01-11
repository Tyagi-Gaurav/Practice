package org.gt.chat;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.ContentTypes;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.headers.ModeledCustomHeader;
import akka.http.javadsl.testkit.JUnitRouteTest;
import akka.http.javadsl.testkit.TestRoute;
import akka.http.javadsl.testkit.TestRouteResult;
import akka.testkit.TestProbe;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValueFactory;
import org.gt.chat.domain.AuditEvent;
import org.gt.chat.exception.ErrorResponse;
import org.gt.chat.exception.MessageExceptionHandler;
import org.gt.chat.resource.MessageResourceAkka;
import org.gt.chat.response.Conversation;
import org.gt.chat.response.ConversationType;
import org.gt.chat.response.Conversations;
import org.gt.chat.service.ConversationActor;
import org.gt.chat.util.StringBasedHeader;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class ChatEndToEndTest extends JUnitRouteTest {
    private MessageExceptionHandler messageExceptionHandler = new MessageExceptionHandler();
    private TestRoute route;
    private TestProbe testProbe;
    private String requestId;

    @Before
    public void setUp() throws Exception {
        Config config = ConfigFactory.systemProperties()
                .withValue("audit.actorname", ConfigValueFactory.fromAnyRef("/user/auditActor"));
        ActorSystem actorSystem = ActorSystem.create("TestActorSystem", config);
        testProbe = new TestProbe(actorSystem);
        CompletionStage<ActorRef> completionStage = CompletableFuture.completedFuture(testProbe.ref());
        Function<ActorContext, CompletionStage<ActorRef>> actorRefSupplier = (ac) -> completionStage;
        ActorRef actorRef = actorSystem.actorOf(Props.create(ConversationActor.class, actorRefSupplier));
        MessageResourceAkka messageResource = new MessageResourceAkka(actorRef, messageExceptionHandler);
        requestId = "Test-request-Id";

        route = testRoute(messageResource.getRoute());
    }

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
        Conversations expectedMessages = new Conversations(requestId, conversationList);

        //When
        TestRouteResult run = route.run((HttpRequest.GET("/conversations/2")
                .addHeader(new StringBasedHeader("X-request-id", requestId))));
        run.assertStatusCode(200)
            .assertContentType(ContentTypes.APPLICATION_JSON);

        Conversations entity = run.entity(Jackson.unmarshaller(Conversations.class));
        assertThat(expectedMessages).isEqualTo(entity);
        testProbe.expectMsgClass(AuditEvent.class);
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
