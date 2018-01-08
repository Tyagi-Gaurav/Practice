package org.gt.chat;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.bson.Document;
import org.gt.chat.domain.AuditEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;
import java.util.concurrent.CompletionStage;

import static akka.pattern.PatternsCS.ask;
import static com.mongodb.client.model.Filters.eq;
import static org.assertj.core.api.Assertions.assertThat;

public class AuditRepoActorTest extends ActorSystemTest {
    private static final MongodStarter starter = MongodStarter.getDefaultInstance();
    private MongodExecutable _mongodExe;
    private MongodProcess _mongod;
    private MongoClient mongo;
    private MongoDatabase db;

    @Before
    public void setUp() throws Exception {
        _mongodExe = starter.prepare(new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net("localhost", 12345, Network.localhostIsIPv6()))
                .build());
        _mongod = _mongodExe.start();

        mongo = new MongoClient("localhost", 12345);
        db = mongo.getDatabase("test-" + UUID.randomUUID().toString());
    }

    @After
    public void tearDown() throws Exception {
        _mongod.stop();
        _mongodExe.stop();
    }


    @Test
    public void shouldStoreAuditEventInDatabase() throws Exception {
        long eventPublishEpochTimeStamp = System.currentTimeMillis();
        AuditEvent auditEvent = AuditEvent
                .builder()
                .eventPublishEpochTimeStamp(eventPublishEpochTimeStamp)
                .build();

        new TestKit(actorSystem) {{
            final Props props = Props.create(AuditRepoActor.class, db);
            final ActorRef subject = actorSystem.actorOf(props);

            CompletionStage<Object> ask = ask(subject, auditEvent, 5000);

            while (!ask.toCompletableFuture().isDone());

            FindIterable<Document> documents = db.getCollection("test")
                    .find(eq("timestamp",
                            eventPublishEpochTimeStamp));

            MongoIterable<AuditEvent> timestamp = documents.map(doc -> AuditEvent.builder()
                    .eventPublishEpochTimeStamp((Long) doc.get("timestamp")).build());

            assertThat(timestamp.first()).isEqualTo(auditEvent);
        }};
    }
}
