package org.gt.chat.resource;

import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.ContentTypes;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.testkit.JUnitRouteTest;
import akka.http.javadsl.testkit.TestRoute;
import akka.http.javadsl.testkit.TestRouteResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.gt.chat.resource.MessageResourceAkka;
import org.gt.chat.response.Message;
import org.gt.chat.response.Messages;
import org.gt.chat.service.MessageService;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MessageResourceAkkaTest extends JUnitRouteTest {
//    private MessageService messageService = mock(MessageService.class);
//    private MessageResourceAkka messageResource = new MessageResourceAkka(messageService);
//    TestRoute route = testRoute(messageResource.route);
//    private ObjectMapper mapper = new ObjectMapper();
//
//    @Test
//    public void getMessagesForUser() {
//        //Given
//        String userId = "1";
//
//        Messages messages = new Messages(Arrays.asList(
//                new Message(
//                        "2",
//                        "Hello World",
//                        234878234L)
//        ));
//
//        when(messageService.getMessagesFor(userId)).thenReturn(messages);
//
//        //When & Then
//        TestRouteResult run = route.run((HttpRequest.GET("/message/users/1")));
//        run.assertStatusCode(200)
//                .assertContentType(ContentTypes.APPLICATION_JSON);
//
//        Messages entity = run.entity(Jackson.unmarshaller(Messages.class));
//        assertThat(messages).isEqualTo(entity);
//
//    }
}