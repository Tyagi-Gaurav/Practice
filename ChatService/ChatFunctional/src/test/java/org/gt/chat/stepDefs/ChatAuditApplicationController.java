package org.gt.chat.stepDefs;

import akka.actor.ActorSystem;
import com.google.inject.Singleton;
import com.typesafe.config.ConfigFactory;
import org.gt.chat.main.audit.AuditAkkaServer;

@Singleton
public class ChatAuditApplicationController implements Controller {

    private final AuditAkkaServer auditAkkaServer;

    public ChatAuditApplicationController() {
        ActorSystem actorSystem = ActorSystem.create("functionalTestAuditActorSystem",
                ConfigFactory.load("functionalTestAuditApplication.conf")
                        .withFallback(ConfigFactory.load("functionalTestApplication.conf")));
        auditAkkaServer = new AuditAkkaServer(actorSystem);
    }

    @Override
    public void stop() throws RuntimeException {

    }

    @Override
    public void start() throws RuntimeException {
        auditAkkaServer.initialize();
    }
}
