package org.gt.chat;

import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.testkit.JUnitRouteTest;
import akka.http.javadsl.testkit.TestRoute;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.gt.chat.repos.ChatMessageRepository;
import org.gt.chat.repos.MessageRepository;
import org.gt.chat.resource.MessageResourceAkka;
import org.gt.chat.response.Message;
import org.gt.chat.response.Messages;
import org.gt.chat.service.ChatMessageService;
import org.gt.chat.service.MessageService;
import org.junit.*;

import javax.ws.rs.core.Application;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ChatEndToEndTest extends JUnitRouteTest {
    private MessageRepository repository = new ChatMessageRepository();
    private MessageService service = new ChatMessageService(repository);
    private MessageResourceAkka messageResource = new MessageResourceAkka(service);

    TestRoute route = testRoute(messageResource.route);

    @Test
    public void getMessagesForAUser() {
        //When
        route.run((HttpRequest.GET("/message/users/1")))
        .assertStatusCode(200);

        //Then
        /*assertThat(messages).isNotNull();
        List<Message> messageResponseList = messages.getMessageResponseList();
        assertThat(messageResponseList).isNotEmpty();
        Message message = messageResponseList.get(0);
        assertThat(message.getContent()).isEqualTo("Hello World");
        assertThat(message.getId()).isEqualTo("2");
        assertThat(message.getTimestamp()).isEqualTo(234878234L);*/
    }
}
