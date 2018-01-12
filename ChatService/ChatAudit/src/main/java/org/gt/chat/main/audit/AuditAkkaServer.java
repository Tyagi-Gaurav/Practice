package org.gt.chat.main.audit;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.mongodb.MongoClient;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuditAkkaServer {
    private ActorSystem actorSystem;

    public AuditAkkaServer(ActorSystem actorSystem) {
        this.actorSystem = actorSystem;
    }

    public static void main(String[] args) {
        Config config = ConfigFactory.load("application.conf");
        ActorSystem actorSystem = ActorSystem.create("akka-audit-server", config);
        AuditAkkaServer auditAkkaServer = new AuditAkkaServer(actorSystem);
        auditAkkaServer.initialize();
    }

    public void initialize() {
        Config config = actorSystem.settings().config();
        ActorRef auditRepoActor = actorSystem.actorOf(Props.create(AuditRepoActor.class,
                databaseProvider(config)));

        ActorRef auditActor = actorSystem.actorOf(Props.create(AuditActor.class, auditRepoActor), "auditActor");
        log.info("Created actor " + auditActor.path().toString());
    }

    private Object databaseProvider(Config config) {
        MongoClient mongoClient = new MongoClient(config.getString("repo.hostname"),
                config.getInt("repo.port"));
        return mongoClient.getDatabase(config.getString("repo.dbName"));
    }
}
