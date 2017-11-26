package org.gt.chat;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import org.gt.chat.exception.MessageExceptionHandler;
import org.gt.chat.resource.MessageResourceAkka;
import org.gt.chat.service.ConversationActor;

import java.io.IOException;

public class AkkaServer {
    public static void main(String[] args) throws IOException {
        ActorSystem actorSystem = ActorSystem.create();
        final Http http = Http.get(actorSystem);

        MessageExceptionHandler messageExceptionHandler = new MessageExceptionHandler();
        ActorRef actorRef = actorSystem.actorOf(Props.create(ConversationActor.class));
        MessageResourceAkka messageResource = new MessageResourceAkka(actorRef, messageExceptionHandler);

        final ActorMaterializer materializer = ActorMaterializer.create(actorSystem);
        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow =
                messageResource.route.flow(actorSystem, materializer);
        http.bindAndHandle(routeFlow, ConnectHttp.toHost("0.0.0.0", 8080), materializer);

        System.out.println("Server online at http://localhost:8080/");
    }
}