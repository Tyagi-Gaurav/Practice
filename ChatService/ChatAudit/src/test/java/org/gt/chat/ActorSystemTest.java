package org.gt.chat;

import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import scala.concurrent.duration.FiniteDuration;

import java.util.concurrent.TimeUnit;

public abstract class ActorSystemTest {
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