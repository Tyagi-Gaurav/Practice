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
import org.gt.chat.response.Message;
import org.gt.chat.response.Messages;
import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageResourceAkkaTest extends JUnitRouteTest {
    private final Messages expectedMessages = new Messages(Arrays.asList(
            new Message(
                    "2",
                    "Hello World",
                    234878234L)
    ));
    private ActorSystem actorSystem = ActorSystem.create("Test");
    private ActorRef messageActor =
            actorSystem.actorOf(Props.create(TestMessageActor.class));
    private MessageResourceAkka messageResource = new MessageResourceAkka(messageActor);
    TestRoute route = testRoute(messageResource.route);

    @Test
    public void getMessagesForUser() {
        //When & Then
        TestRouteResult run = route.run((HttpRequest.GET("/message/users/1")));
        run.assertStatusCode(200)
                .assertContentType(ContentTypes.APPLICATION_JSON);

        Messages entity = run.entity(Jackson.unmarshaller(Messages.class));
        assertThat(expectedMessages).isEqualTo(entity);
    }


}