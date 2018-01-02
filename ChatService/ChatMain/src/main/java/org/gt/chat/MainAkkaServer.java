package org.gt.chat;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.server.Route;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.extern.slf4j.Slf4j;
import org.gt.chat.exception.MessageExceptionHandler;
import org.gt.chat.resource.MessageResourceAkka;
import org.gt.chat.service.ConversationActor;

import java.io.IOException;

@Slf4j
public class MainAkkaServer {
    private Config config;
    private ActorSystem actorSystem;

    public MainAkkaServer(Config config, ActorSystem actorSystem) {
        this.config = config;
        this.actorSystem = actorSystem;
    }

    public static void main(String[] args) throws IOException {
        Config config = ConfigFactory.load("application.conf");
        ActorSystem actorSystem = ActorSystem.create("akka-main-server", config);
        MainAkkaServer mainAkkaServer = new MainAkkaServer(config, actorSystem);
        mainAkkaServer.initialize();
        System.out.println("Server online at http://localhost:8080/");
    }

    public void initialize() {
        final Http http = Http.get(actorSystem);
        MessageExceptionHandler messageExceptionHandler = new MessageExceptionHandler();
        ActorRef actorRef = createMessageActor();
        MessageResourceAkka messageResource = new MessageResourceAkka(actorRef, messageExceptionHandler);

        final ActorMaterializer materializer = ActorMaterializer.create(actorSystem);
        Route route = messageResource.getRoute();
        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow =
                route.flow(actorSystem, materializer);
        http.bindAndHandle(routeFlow, ConnectHttp.toHost("0.0.0.0", 8080), materializer);
    }

    private ActorRef createMessageActor() {
        return actorSystem.actorOf(Props.create(ConversationActor.class));
    }

//    private CompletionStage<ActorRef> createAuditActor() {
//        String actorSystemName = config.getString("audit.system");
//        String targetHost = config.getString("audit.host");
//        long port = config.getLong("audit.port");
//        String targetActorName = config.getString("audit.actorName");
//        String fullActorPath = "akka://" +
//                actorSystemName + "@"
//                + targetHost + ":" + port + targetActorName;
//        log.info("Full Actor Path: " + fullActorPath);
//        System.out.println("Full Actor Path: " + fullActorPath);
//        ActorSelection selection = actorSystem.actorSelection(fullActorPath);
//        CompletionStage<ActorRef> actorRefCompletionStage =
//                selection.resolveOneCS(FiniteDuration.apply(5, TimeUnit.SECONDS));
//        return actorRefCompletionStage;
//    }
}
