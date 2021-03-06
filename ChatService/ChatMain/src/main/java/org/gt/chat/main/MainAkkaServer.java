package org.gt.chat.main;

import akka.NotUsed;
import akka.actor.*;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.swagger.jaxrs.config.DefaultReaderConfig;
import lombok.extern.slf4j.Slf4j;
import org.gt.chat.main.exception.MessageExceptionHandler;
import org.gt.chat.main.repos.ConversationRepository;
import org.gt.chat.main.resource.DocumentationRoute;
import org.gt.chat.main.resource.HealthCheckResource;
import org.gt.chat.main.resource.MessageResourceAkka;
import org.gt.chat.main.service.ConversationActor;
import scala.concurrent.duration.FiniteDuration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
public class MainAkkaServer {
    private ActorSystem actorSystem;

    public MainAkkaServer(ActorSystem actorSystem) {
        this.actorSystem = actorSystem;
    }

    public static void main(String[] args) throws IOException {
        Config config = ConfigFactory.load("application.conf");
        ActorSystem actorSystem = ActorSystem.create("akka-main-server", config);
        MainAkkaServer mainAkkaServer = new MainAkkaServer(actorSystem);
        mainAkkaServer.initialize();
    }

    public void initialize() {
        final Http http = Http.get(actorSystem);
        MessageExceptionHandler messageExceptionHandler = new MessageExceptionHandler();
        ActorRef actorRef = createMessageActor();
        DocumentationRoute documentationRoute = getDocumentationRoute();
        MessageResourceAkka messageResource = new MessageResourceAkka(
                actorRef,
                messageExceptionHandler,
                documentationRoute);

        HealthCheckResource healthCheckResource = new HealthCheckResource(actorRef, documentationRoute);

        final ActorMaterializer materializer = ActorMaterializer.create(actorSystem);

        AllRoutes allRoutes = new AllRoutes(messageResource, healthCheckResource);

        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow =
                allRoutes.getRoutes().flow(actorSystem, materializer);
        http.bindAndHandle(routeFlow, ConnectHttp.toHost("0.0.0.0", 8080), materializer);
        System.out.println("Server online at http://localhost:8080/");
    }

    private DocumentationRoute getDocumentationRoute() {
        DefaultReaderConfig readerConfig = new DefaultReaderConfig();
        return new DocumentationRoute(readerConfig);
    }

    private ActorRef createMessageActor() {
        Function<ActorContext, CompletionStage<ActorRef>> actorRefSupplier = this::createAuditActor;
        return actorSystem.actorOf(Props.create(ConversationActor.class,
                actorRefSupplier,
                getConversationRepositoryActor()));
    }

    private ConversationRepository getConversationRepositoryActor() {
        Config config = actorSystem.settings().config();
        return new ConversationRepository(databaseProvider(config), config, timeProvider());
    }

    private Supplier<LocalDateTime> timeProvider() {
        return LocalDateTime::now;
    }

    private CompletionStage<ActorRef> createAuditActor(ActorContext context) {
        Config config = context.system().settings().config();
        String actorSystemName = config.getString("audit.system");
        String targetHost = config.getString("audit.host");
        long port = config.getLong("audit.port");
        String targetActorName = config.getString("audit.actorName");
        String fullActorPath = "akka://" +
                actorSystemName + "@"
                + targetHost + ":" + port + targetActorName;
        log.error("Full Actor Path: " + fullActorPath);
        ActorSelection selection = context.actorSelection(fullActorPath);
        CompletionStage<ActorRef> actorRefCompletionStage =
                selection.resolveOneCS(FiniteDuration.apply(5, TimeUnit.SECONDS));
        return actorRefCompletionStage;
    }

    private MongoDatabase databaseProvider(Config config) {
        MongoClient mongoClient = new MongoClient(config.getString("repo.hostname"),
                config.getInt("repo.port"));
        return mongoClient.getDatabase(config.getString("repo.dbName"));
    }
}
