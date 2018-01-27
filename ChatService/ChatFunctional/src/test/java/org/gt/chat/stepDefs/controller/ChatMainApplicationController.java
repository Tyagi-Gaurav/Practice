package org.gt.chat.stepDefs.controller;

import akka.actor.ActorSystem;
import com.google.inject.Singleton;
import com.typesafe.config.ConfigFactory;
import org.gt.chat.main.MainAkkaServer;

@Singleton
public class ChatMainApplicationController implements Controller {
    private MainAkkaServer mainAkkaServer;

    public ChatMainApplicationController() {
        ActorSystem actorSystem = ActorSystem.create("functionalTestMainActorSystem",
                ConfigFactory.load("functionalTestMainApplication.conf")
                        .withFallback(ConfigFactory.load("functionalTestApplication.conf")));
        mainAkkaServer = new MainAkkaServer(actorSystem);
    }

    @Override
    public void start() {
        mainAkkaServer.initialize();
    }

    @Override
    public void stop() {
        //
    }
}
