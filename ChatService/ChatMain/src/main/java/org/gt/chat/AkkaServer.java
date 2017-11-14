package org.gt.chat;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import org.gt.chat.repos.ChatMessageRepository;
import org.gt.chat.repos.MessageRepository;
import org.gt.chat.resource.MessageResourceAkka;
import org.gt.chat.service.ConversationActor;

import java.io.IOException;
import java.util.concurrent.CompletionStage;

public class AkkaServer {
    public static void main(String[] args) throws IOException {
        ActorSystem actorSystem = ActorSystem.create();
        final Http http = Http.get(actorSystem);

        MessageRepository repository = new ChatMessageRepository();
        ActorRef actorRef = actorSystem.actorOf(Props.create(ConversationActor.class, repository));
        MessageResourceAkka messageResource = new MessageResourceAkka(actorRef);

        actorSystem.actorOf(Props.create(ConversationActor.class, repository));

        final ActorMaterializer materializer = ActorMaterializer.create(actorSystem);
        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow =
                messageResource.route.flow(actorSystem, materializer);
        final CompletionStage<ServerBinding> binding =
                http.bindAndHandle(routeFlow, ConnectHttp.toHost("localhost", 8080), materializer);

        System.out.println("Server online at http://localhost:8080/\nPress RETURN to stop...");
        System.in.read(); // let it run until user presses return

        binding
                .thenCompose(ServerBinding::unbind)
                .thenAccept(unbound -> actorSystem.terminate());
    }
}
