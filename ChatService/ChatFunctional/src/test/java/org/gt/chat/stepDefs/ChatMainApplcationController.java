package org.gt.chat.stepDefs;

import akka.actor.ActorSystem;
import com.google.inject.Singleton;
import org.gt.chat.MainAkkaServer;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Singleton
public class ChatMainApplcationController {
    private MainAkkaServer mainAkkaServer;

    public ChatMainApplcationController() {
        ActorSystem actorSystem = ActorSystem.create("functionalTestActorSystem");
        mainAkkaServer = new MainAkkaServer(actorSystem);
    }

    public void start() {
        mainAkkaServer.initialize();
    }

    public void stop() {
        //
    }
}
