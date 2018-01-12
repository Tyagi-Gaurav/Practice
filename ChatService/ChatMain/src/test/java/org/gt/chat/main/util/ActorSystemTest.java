package org.gt.chat.main.util;

import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import scala.concurrent.duration.FiniteDuration;

import java.util.concurrent.TimeUnit;

public class ActorSystemTest {
    private static final FiniteDuration shutdownDuration =
            scala.concurrent.duration.Duration.apply(1L, TimeUnit.SECONDS);
    protected static ActorSystem actorSystem;

    @BeforeClass
    public static void beforeCall() {
        actorSystem = ActorSystem.create("Test-Actor-System");
    }

    @AfterClass
    public static void afterCall() {
        TestKit.shutdownActorSystem(actorSystem,
                shutdownDuration,
                true);
        actorSystem = null;
    }
}
