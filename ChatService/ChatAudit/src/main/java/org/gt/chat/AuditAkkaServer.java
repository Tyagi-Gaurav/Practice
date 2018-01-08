package org.gt.chat;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.bson.Document;

public class AuditAkkaServer {
    public static void main(String[] args) {
        AuditAkkaServer auditAkkaServer = new AuditAkkaServer();
        auditAkkaServer.initialize();
    }

    private void initialize() {
        Config config = ConfigFactory.load("application.conf");
        ActorSystem actorSystem = ActorSystem.create("akka-audit-server", config);

        ActorRef auditRepoActor = actorSystem.actorOf(Props.create(AuditRepoActor.class,
                databaseProvider(config)));

        ActorRef auditActor = actorSystem.actorOf(Props.create(AuditActor.class, auditRepoActor), "auditActor");
        System.out.println("Created actor " + auditActor.path().toString());
    }

    private Object databaseProvider(Config config) {
        MongoClient mongoClient = new MongoClient(config.getString("repo.hostname"),
                config.getInt("repo.port"));
        return mongoClient.getDatabase(config.getString("repo.dbName"));
    }
}
