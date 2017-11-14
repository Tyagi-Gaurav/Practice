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
import org.gt.chat.repos.ChatMessageRepository;
import org.gt.chat.repos.MessageRepository;
import org.gt.chat.resource.MessageResourceAkka;
import org.gt.chat.response.Conversation;
import org.gt.chat.response.ConversationType;
import org.gt.chat.response.Conversations;
import org.gt.chat.service.ChatMessageService;
import org.gt.chat.service.ConversationActor;
import org.gt.chat.service.ConversationService;
import org.junit.Test;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

import static org.assertj.core.api.Assertions.assertThat;

public class ChatEndToEndTest extends JUnitRouteTest {
    private MessageRepository repository = new ChatMessageRepository();
    private ConversationService service = new ChatMessageService(repository);
    ActorSystem actorSystem = ActorSystem.create();
    ActorRef actorRef = actorSystem.actorOf(Props.create(ConversationActor.class, repository));
    private MessageResourceAkka messageResource = new MessageResourceAkka(actorRef);

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
        TestRouteResult run = route.run((HttpRequest.GET("/conversations/1")));
        run.assertStatusCode(200)
            .assertContentType(ContentTypes.APPLICATION_JSON);

        Conversations entity = run.entity(Jackson.unmarshaller(Conversations.class));
        assertThat(expectedMessages).isEqualTo(entity);
    }
}
