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
import org.gt.chat.response.Message;
import org.gt.chat.response.Messages;
import org.gt.chat.service.ChatMessageService;
import org.gt.chat.service.MessageActor;
import org.gt.chat.service.MessageService;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ChatEndToEndTest extends JUnitRouteTest {
    private MessageRepository repository = new ChatMessageRepository();
    private MessageService service = new ChatMessageService(repository);
    ActorSystem actorSystem = ActorSystem.create();
    ActorRef actorRef = actorSystem.actorOf(Props.create(MessageActor.class, repository));
    private MessageResourceAkka messageResource = new MessageResourceAkka(actorRef);

    TestRoute route = testRoute(messageResource.route);

    @Test
    public void getConversationsForAUser() throws IOException {
        //Given
        List<Message> messageList = Arrays.asList(
                new Message("2", "Hello World", 234878234L));
        Messages expectedMessages = new Messages(messageList);

        //When
        TestRouteResult run = route.run((HttpRequest.GET("/message/users/1")));
        run.assertStatusCode(200)
            .assertContentType(ContentTypes.APPLICATION_JSON);

        Messages entity = run.entity(Jackson.unmarshaller(Messages.class));
        assertThat(expectedMessages).isEqualTo(entity);
    }
}
