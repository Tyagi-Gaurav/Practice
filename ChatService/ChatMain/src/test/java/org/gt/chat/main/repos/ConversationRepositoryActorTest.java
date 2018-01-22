package org.gt.chat.main.repos;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;
import com.mongodb.Function;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValueFactory;
import org.bson.Document;
import org.gt.chat.main.audit.exception.InvalidUserException;
import org.gt.chat.main.domain.ConversationAggregate;
import org.gt.chat.main.domain.ConversationEntity;
import org.gt.chat.main.util.ActorSystemTest;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import static akka.pattern.PatternsCS.ask;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.emptyList;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ConversationRepositoryActorTest extends ActorSystemTest {
    private static final String COLLECTION_VALUE = UUID.randomUUID().toString();
    private static final String COLLECTION_KEY = "repo.collection";

    @Mock
    private MongoDatabase db;

    @Mock
    private MongoCollection<Document> dbCollection;

    @Before
    public void setUp() throws Exception {
        FindIterable<Document> findIterable = mock(FindIterable.class);
        MongoIterable<ConversationEntity> mongoIterable = mock(MongoIterable.class);
        ConversationEntity conversationEntity = new ConversationEntity(
                "2",
                "Hello World",
                234878234L,
                "groupId",
                "senderId");

        when(db.getCollection(COLLECTION_VALUE)).thenReturn(dbCollection);
        doNothing().when(dbCollection).insertOne(any(Document.class));
        when(dbCollection.find(any(Document.class))).thenReturn(findIterable);
        when(findIterable.map(any(Function.class))).thenReturn(mongoIterable);
        when(mongoIterable.spliterator()).thenReturn(Arrays.asList(conversationEntity).spliterator());
    }

    @BeforeClass
    public static void beforeSetup() {
        Config config = ConfigFactory.systemProperties()
                .withValue(COLLECTION_KEY, ConfigValueFactory.fromAnyRef(COLLECTION_VALUE));
        actorSystem = actorSystem.create("Test", config);
    }

    @Test
    public void getMessagesForUser() {
        // Given
        ConversationEntity conversationEntity = new ConversationEntity(
                "2",
                "Hello World",
                234878234L,
                "groupId",
                "senderId");
        List<ConversationEntity> entityList = new ArrayList<>();
        entityList.add(conversationEntity);
        ConversationAggregate aggregate = new ConversationAggregate(entityList);

        new TestKit(actorSystem) {{
            final Props props = Props.create(ConversationRepositoryActor.class, db);
            final ActorRef subject = actorSystem.actorOf(props);

            subject.tell("2", getRef());

            expectMsg(duration("5 second"), aggregate);
            verify(dbCollection).find(any(Document.class));
        }};
    }

    @Test
    public void shouldThrowExceptionWhenInvalidDataIsPassed() throws ExecutionException, InterruptedException {
        //Given
        FindIterable<Document> findIterable = mock(FindIterable.class);
        MongoIterable<ConversationEntity> mongoIterable = mock(MongoIterable.class);
        when(dbCollection.find(any(Document.class))).thenReturn(findIterable);
        when(findIterable.map(any(Function.class))).thenReturn(mongoIterable);
        when(mongoIterable.spliterator()).thenReturn(Spliterators.emptySpliterator());

        //When
        new TestKit(actorSystem) {{
            final Props props = Props.create(ConversationRepositoryActor.class, db);
            final ActorRef subject = actorSystem.actorOf(props);

            CompletionStage<Object> ask = ask(subject, "193", 5000);

            try {
                ask.toCompletableFuture().get();
                fail("Should have failed");
            } catch (Exception e) {
                assertThat(e.getCause()).isExactlyInstanceOf(InvalidUserException.class);
                assertThat(e.getMessage()).isEqualTo("org.gt.chat.main.audit.exception.InvalidUserException: 193");
            }
        }};
    }
}