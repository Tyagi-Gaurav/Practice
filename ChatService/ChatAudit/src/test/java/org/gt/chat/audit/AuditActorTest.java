package org.gt.chat.audit;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;
import org.gt.chat.audit.domain.AuditEvent;
import org.gt.chat.audit.exception.InvalidAuditEventException;
import org.junit.Test;

import java.util.concurrent.CompletionStage;

import static akka.pattern.PatternsCS.ask;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public class AuditActorTest extends ActorSystemTest {

    @Test
    public void shouldNotStoreWhenAuditEventIsNotSupported() {
        AuditEvent auditEvent = AuditEvent
                .builder()
                .eventPublishEpochTimeStamp(System.currentTimeMillis())
                .build();

        new TestKit(actorSystem) {{
           final Props props = Props.create(AuditActor.class);
           final ActorRef subject = actorSystem.actorOf(props);

            CompletionStage<Object> ask = ask(subject, auditEvent, 5000);

            try {
                ask.toCompletableFuture().get();
                fail("Should have thrown an exception");
            } catch (Exception e) {
                assertThat(e.getCause()).isExactlyInstanceOf(InvalidAuditEventException.class);
                assertThat(e.getMessage()).isEqualTo("org.gt.chat.audit.exception.InvalidAuditEventException: Invalid Audit Event: null");
            }
        }};
    }
}