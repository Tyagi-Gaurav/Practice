package org.gt.chat.main.audit;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValueFactory;
import org.bson.Document;
import org.gt.chat.main.audit.domain.AuditEvent;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.CompletionStage;

import static akka.pattern.PatternsCS.ask;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AuditRepoActorTest extends ActorSystemTest {
    private static final String COLLECTION_VALUE = "abc";
    private static final String COLLECTION_KEY = "repo.collection";

    @Mock
    private MongoDatabase db;

    @Mock
    private MongoCollection<Document> dbCollection;

    @Before
    public void setUp() throws Exception {
        when(db.getCollection(COLLECTION_VALUE)).thenReturn(dbCollection);
        doNothing().when(dbCollection).insertOne(any(Document.class));
    }

    @BeforeClass
    public static void beforeSetup() {
        Config config = ConfigFactory.systemProperties()
                .withValue(COLLECTION_KEY, ConfigValueFactory.fromAnyRef(COLLECTION_VALUE));
        actorSystem = actorSystem.create("Test", config);
    }

    @Test
    public void shouldStoreAuditEventInDatabase() throws Exception {
        long eventPublishEpochTimeStamp = System.currentTimeMillis();
        AuditEvent auditEvent = AuditEvent
                .builder()
                .globalRequestId("global-request-id")
                .eventPublishEpochTimeStamp(eventPublishEpochTimeStamp)
                .build();

        new TestKit(actorSystem) {{
            final Props props = Props.create(AuditRepoActor.class, db);
            final ActorRef subject = actorSystem.actorOf(props);

            CompletionStage<Object> ask = ask(subject, auditEvent, 5000);
            while (!ask.toCompletableFuture().isDone());

            verify(db).getCollection(COLLECTION_VALUE);
            verify(dbCollection).insertOne(any(Document.class));
        }};
    }
}
