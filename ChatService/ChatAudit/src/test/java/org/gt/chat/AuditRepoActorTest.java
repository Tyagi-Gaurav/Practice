package org.gt.chat;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;
import org.gt.chat.domain.AuditEvent;
import org.junit.Test;

import java.util.concurrent.CompletionStage;

import static akka.pattern.PatternsCS.ask;

public class AuditRepoActorTest extends ActorSystemTest {

    @Test
    public void shouldStoreAuditEventInDatabase() throws Exception {
        AuditEvent auditEvent = AuditEvent
                .builder()
                .eventPublishEpochTimeStamp(System.currentTimeMillis())
                .build();

        new TestKit(actorSystem) {{
            final Props props = Props.create(AuditRepoActor.class);
            final ActorRef subject = actorSystem.actorOf(props);

            CompletionStage<Object> ask = ask(subject, auditEvent, 5000);

            ask.toCompletableFuture().get();
        }};
    }
}
