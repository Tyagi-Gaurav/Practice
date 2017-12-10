package org.gt.chat;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import akka.util.Timeout;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.gt.chat.exception.MessageExceptionHandler;
import org.gt.chat.resource.MessageResourceAkka;
import org.gt.chat.service.ConversationActor;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;

import java.io.IOException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

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
        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow =
                messageResource.route.flow(actorSystem, materializer);
        http.bindAndHandle(routeFlow, ConnectHttp.toHost("0.0.0.0", 8080), materializer);
    }

    private ActorRef createMessageActor() {
        return actorSystem.actorOf(Props.create(ConversationActor.class, createAuditActor()));
    }

    private CompletionStage<ActorRef> createAuditActor() {
        String actorSystemName = config.getString("audit.system");
        String targetHost = config.getString("audit.host");
        long port = config.getLong("audit.port");
        String targetActorName = config.getString("audit.actorName");
        ActorSelection selection = actorSystem.actorSelection("akka://" +
                    actorSystemName + "@"
                    + targetHost + ":" + port + targetActorName);
        return selection.resolveOneCS(FiniteDuration.apply(5, TimeUnit.SECONDS));
    }
}
