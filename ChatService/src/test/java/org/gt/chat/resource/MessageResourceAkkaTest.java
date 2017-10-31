package org.gt.chat.resource;

import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.testkit.JUnitRouteTest;
import akka.http.javadsl.testkit.TestRoute;
import org.gt.chat.resource.MessageResourceAkka;
import org.gt.chat.response.Message;
import org.gt.chat.response.Messages;
import org.gt.chat.service.MessageService;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MessageResourceAkkaTest extends JUnitRouteTest {
    private MessageService messageService = mock(MessageService.class);
    private MessageResourceAkka messageResource = new MessageResourceAkka(messageService);
    TestRoute route = testRoute(messageResource.route);

    @Test
    public void getMessagesForUser() {
        //Given
        String userId = "1";
        Message expectedMessage = new Message(
                "2",
                "Hello World",
                234878234L);

        List<Message> messageList = new ArrayList<>();
        messageList.add(expectedMessage);
        Messages messages = new Messages(messageList);
        when(messageService.getMessagesFor(userId)).thenReturn(messages);

        //When & Then
        route.run(HttpRequest.GET("/message/users/1")).assertStatusCode(200);

    }
}