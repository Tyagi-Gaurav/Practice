package org.gt.chat.main.audit;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.testkit.TestActor;
import akka.testkit.TestProbe;
import akka.testkit.javadsl.TestKit;
import org.gt.chat.main.audit.domain.*;
import org.gt.chat.main.audit.exception.InvalidAuditEventException;
import org.junit.Test;

import javax.xml.crypto.Data;
import java.util.concurrent.CompletionStage;

import static akka.pattern.PatternsCS.ask;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public class AuditActorTest extends ActorSystemTest {
    private TestProbe auditRepoProbe;

    @Test
    public void shouldNotStoreWhenAuditEventIsNotSupported() {
        AuditEvent auditEvent = AuditEvent
                .builder()
                .eventPublishEpochTimeStamp(System.currentTimeMillis())
                .build();

        new TestKit(actorSystem) {{
            auditRepoProbe = new TestProbe(actorSystem);
            final Props props = Props.create(AuditActor.class, auditRepoProbe.ref());
            final ActorRef subject = actorSystem.actorOf(props);

            CompletionStage<Object> ask = ask(subject, auditEvent, 5000);

            try {
                ask.toCompletableFuture().get();
                fail("Should have thrown an exception");
            } catch (Exception e) {
                assertThat(e.getCause()).isExactlyInstanceOf(InvalidAuditEventException.class);
                assertThat(e.getMessage()).contains("Invalid Audit Event: null");
            }
        }};
    }

    @Test
    public void shouldStoreWhenAuditEventIsSupported() {
        //Given
        AuditEvent auditEvent = AuditEvent
                .builder()
                .globalRequestId("Test-Request-id")
                .auditEventType(AuditEventType.MESSAGE_READ_EVENT)
                .eventPublishEpochTimeStamp(System.currentTimeMillis())
                .build();

        new TestKit(actorSystem) {{
            auditRepoProbe = new TestProbe(actorSystem);
            final Props props = Props.create(AuditActor.class, auditRepoProbe.ref());
            final ActorRef subject = actorSystem.actorOf(props);

            CompletionStage<Object> ask = ask(subject, auditEvent, 5000);

            try {
                ask.toCompletableFuture().get();
                auditRepoProbe.expectMsg(auditEvent);
            } catch (Exception e) {
                fail(e.getMessage());
            }
        }};
    }

    @Test
    public void shouldReturnOKWhenAuditActorIsRunning() throws Exception {
        //Given
        HealthCheckRequest healthCheckRequest = HealthCheckRequest.builder().build();

        new TestKit(actorSystem) {{
            auditRepoProbe = new TestProbe(actorSystem);
            final Props props = Props.create(AuditActor.class, auditRepoProbe.ref());
            final ActorRef subject = actorSystem.actorOf(props);
            auditRepoProbe.setAutoPilot(new TestActor.AutoPilot() {
                @Override
                public TestActor.AutoPilot run(ActorRef sender, Object msg) {
                    sender.tell(DatabaseHealthCheckResponse.builder().result("OK").build()
                            , ActorRef.noSender());
                    return noAutoPilot();
                }
            });

            //When
            CompletionStage<Object> ask = ask(subject, healthCheckRequest, 5000);

            HealthCheckResponse expectedResponse = HealthCheckResponse.builder()
                    .result("OK")
                    .database("OK")
                    .build();

            //Then
            try {
                Object result = ask.toCompletableFuture().get();
                assertThat(result).isInstanceOf(HealthCheckResponse.class);
                assertThat(((HealthCheckResponse)result)).isEqualTo(expectedResponse);
                auditRepoProbe.expectMsg(HealthCheckRequest.builder().build());
            } catch (Exception e) {
                fail(e.getMessage());
            }
        }};
    }

    @Test
    public void shouldReturnFailureForDatabaseWhenDatabaseIsDown() throws Exception {
        //Given
        HealthCheckRequest healthCheckRequest = HealthCheckRequest.builder().build();
        String failureMessage = "Failure: Database Not Available Exception";

        new TestKit(actorSystem) {{
            auditRepoProbe = new TestProbe(actorSystem);
            final Props props = Props.create(AuditActor.class, auditRepoProbe.ref());
            final ActorRef subject = actorSystem.actorOf(props);
            auditRepoProbe.setAutoPilot(new TestActor.AutoPilot() {
                @Override
                public TestActor.AutoPilot run(ActorRef sender, Object msg) {
                    sender.tell(DatabaseHealthCheckResponse.builder().result(failureMessage).build(),
                            ActorRef.noSender());
                    return noAutoPilot();
                }
            });

            //When
            CompletionStage<Object> ask = ask(subject, healthCheckRequest, 5000);

            HealthCheckResponse expectedResponse = HealthCheckResponse.builder()
                    .result("OK")
                    .database(failureMessage)
                    .build();

            //Then
            try {
                Object result = ask.toCompletableFuture().get();
                assertThat(result).isInstanceOf(HealthCheckResponse.class);
                assertThat(((HealthCheckResponse)result)).isEqualTo(expectedResponse);
                auditRepoProbe.expectMsg(HealthCheckRequest.builder().build());
            } catch (Exception e) {
                fail(e.getMessage());
            }
        }};
    }
}