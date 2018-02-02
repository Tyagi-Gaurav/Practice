package org.gt.chat.main.repos;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;
import com.mongodb.Function;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValueFactory;
import org.bson.Document;
import org.gt.chat.main.domain.ContentType;
import org.gt.chat.main.domain.ConversationEntity;
import org.gt.chat.main.domain.dto.ConversationSaveDTO;
import org.gt.chat.main.exception.InvalidUserException;
import org.gt.chat.main.util.ActorSystemTest;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.Collections;
import java.util.Spliterators;
import java.util.UUID;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import static akka.pattern.PatternsCS.ask;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ConversationRepositoryActorTest extends ActorSystemTest {
    private static final String COLLECTION_VALUE = UUID.randomUUID().toString();
    private static final String COLLECTION_KEY = "repo.collection";
    private static final String VALID_USER_ID = "2";

    @Mock
    private MongoDatabase db;

    @Mock
    private FindIterable<Document> findIterable = mock(FindIterable.class);

    @Mock
    private MongoCollection<Document> dbCollection;

    @Mock
    private MongoIterable<ConversationEntity> mongoIterable = mock(MongoIterable.class);

    @Before
    public void setUp() throws Exception {
        ConversationEntity conversationEntity = getConversationEntity();

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
        ConversationEntity expectedConversationEntity = getConversationEntity();
        when(findIterable.map(any(Function.class))).thenAnswer((Answer<MongoIterable>) invocationOnMock -> {
            Object[] arguments = invocationOnMock.getArguments();
            Function argument = (Function<Document, ConversationEntity>) arguments[0];

            Object apply = argument.apply(getSearchDocument());
            assertThat(apply).isEqualTo(expectedConversationEntity);
            return mongoIterable;
        });

        new TestKit(actorSystem) {{
            final Props props = Props.create(ConversationRepositoryActor.class, db);
            final ActorRef subject = actorSystem.actorOf(props);

            subject.tell("2", getRef());

            expectMsg(duration("5 second"), expectedConversationEntity);
            verify(dbCollection).find(any(Document.class));
            verify(findIterable).map(any(Function.class));
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
                assertThat(e.getMessage()).isEqualTo("org.gt.chat.main.exception.InvalidUserException: 193");
            }
        }};
    }

    @Test
    public void shouldReturnSuccessWhenNoMessagesAvailableForUser() throws ExecutionException, InterruptedException {
        // Given
        ConversationEntity expectedConversationEntity = getConversationEntityWithNoMessages();
        when(findIterable.map(any(Function.class))).thenAnswer((Answer<MongoIterable>) invocationOnMock -> {
            Object[] arguments = invocationOnMock.getArguments();
            Function argument = (Function<Document, ConversationEntity>) arguments[0];

            Object apply = argument.apply(getDocumentWithNoMessages());
            assertThat(apply).isEqualTo(expectedConversationEntity);
            return mongoIterable;
        });
        when(mongoIterable.spliterator()).thenReturn(asList(expectedConversationEntity).spliterator());

        new TestKit(actorSystem) {{
            final Props props = Props.create(ConversationRepositoryActor.class, db);
            final ActorRef subject = actorSystem.actorOf(props);

            subject.tell("2", getRef());

            expectMsg(duration("5 second"), expectedConversationEntity);
            verify(dbCollection).find(any(Document.class));
            verify(findIterable).map(any(Function.class));
        }};
    }

    @Ignore
    public void shouldSaveConversationFromOneUserToAnother() throws Exception {
        //given
        new TestKit(actorSystem) {{
            final Props props = Props.create(ConversationRepositoryActor.class, db);
            final ActorRef subject = actorSystem.actorOf(props);

            ConversationSaveDTO msg = saveDTO();
            subject.tell(msg, getRef());

            verify(dbCollection).find(new Document("userId", msg.getSenderId()));
            verify(dbCollection).insertOne(any(Document.class));
            expectMsg(Boolean.TRUE);
        }};
    }

    private ConversationSaveDTO saveDTO() {
        return ConversationSaveDTO.builder()
                .senderId("a")
                .recipientId("a")
                .message(ConversationSaveDTO.MessageDetail.builder()
                        .contentType(ContentType.APPLICATION_JSON)
                        .content("Hello World")
                        .build())
                .build();
    }

    private ConversationEntity getConversationEntity() {
        return ConversationEntity.builder()
                .userId(VALID_USER_ID)
                .messages(ConversationEntity.Messages.builder()
                        .senderId("senderId")
                        .messageDetails(asList(ConversationEntity.MessageDetailEntity.builder()
                                .received(true)
                                .timestamp(234878234L)
                                .content("Hello World")
                                .contentType(ConversationEntity.ContentTypeEntity.TEXT_PLAIN_UTF8)
                                .build()))
                        .build())
                .build();
    }

    private Document getSearchDocument() {
        return new Document("userId", VALID_USER_ID)
                .append("messages", new Document("senderId", "senderId")
                        .append("messageDetails", asList(
                                new Document("content", "Hello World")
                                        .append("received" , true)
                                        .append("timestamp", 234878234L)
                                        .append("contentType", ConversationEntity.ContentTypeEntity.TEXT_PLAIN_UTF8.toString())
                        )));
    }

    private ConversationEntity getConversationEntityWithNoMessages() {
        return ConversationEntity.builder().userId(VALID_USER_ID)
                .messages(ConversationEntity.Messages.builder()
                        .messageDetails(Collections.emptyList())
                        .build())
                .build();
    }

    private Document getDocumentWithNoMessages() {
        return new Document("userId", VALID_USER_ID);
    }
}