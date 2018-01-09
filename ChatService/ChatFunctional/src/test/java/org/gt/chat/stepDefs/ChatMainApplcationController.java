package org.gt.chat.stepDefs;

import akka.actor.ActorSystem;
import com.google.inject.Singleton;
import org.gt.chat.MainAkkaServer;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Singleton
public class ChatMainApplcationController implements Controller {
    private MainAkkaServer mainAkkaServer;

    public ChatMainApplcationController() {
        ActorSystem actorSystem = ActorSystem.create("functionalTestActorSystem");
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
