package org.gt.chat;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class AuditAkkaServer {
    public static void main(String[] args) {
        Config config = ConfigFactory.load("application.conf");
        ActorSystem actorSystem = ActorSystem.create("akka-audit-server", config);
        ActorRef auditActor = actorSystem.actorOf(Props.create(AuditActor.class), "auditActor");
        System.out.println("Created actor " + auditActor.path().toString());
    }
}
