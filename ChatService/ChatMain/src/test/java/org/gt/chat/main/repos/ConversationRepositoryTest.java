package org.gt.chat.main.repos;

import com.mongodb.Function;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValueFactory;
import org.bson.Document;
import org.gt.chat.main.domain.ConversationEntity;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.gt.chat.main.domain.ConversationEntity.ContentTypeEntity.TEXT_PLAIN_UTF8;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ConversationRepositoryTest {
    private static final String COLLECTION_VALUE = UUID.randomUUID().toString();
    private static final String COLLECTION_KEY = "repo.collection";
    private static final String VALID_USER_ID = UUID.randomUUID().toString();
    private static final String VALID_RECIPIENT_ID = UUID.randomUUID().toString();

    private ConversationRepository conversationRepository;

    private Supplier<LocalDateTime> timeProvider;

    @Mock
    private MongoDatabase db;

    @Mock
    private FindIterable<Document> findIterable = mock(FindIterable.class);

    @Mock
    private MongoCollection<Document> dbCollection;

    @Mock
    private MongoIterable<ConversationEntity> mongoIterable = mock(MongoIterable.class);
    private final Config config = ConfigFactory.systemProperties()
            .withValue(COLLECTION_KEY, ConfigValueFactory.fromAnyRef(COLLECTION_VALUE));

    private LocalDateTime NOW = LocalDateTime.now();

    @Before
    public void setUp() throws Exception {
        timeProvider = () -> NOW;
        conversationRepository = new ConversationRepository(db, config, timeProvider);
        ConversationEntity conversationEntity = getConversationEntity();

        when(db.getCollection(COLLECTION_VALUE)).thenReturn(dbCollection);
        doNothing().when(dbCollection).insertOne(any(Document.class));
        when(dbCollection.find(any(Document.class))).thenReturn(findIterable);
        when(findIterable.map(any(Function.class))).thenReturn(mongoIterable);
        when(mongoIterable.spliterator()).thenReturn(Arrays.asList(conversationEntity).spliterator());
    }

    @BeforeClass
    public static void beforeSetup() {
    }

    @Test
    public void getMessagesForUser() {
        //given
        ConversationEntity expectedConversationEntity = getConversationEntity();
        when(findIterable.map(any(Function.class))).thenAnswer((Answer<MongoIterable>) invocationOnMock -> {
            Object[] arguments = invocationOnMock.getArguments();
            Function argument = (Function<Document, ConversationEntity>) arguments[0];

            Object apply = argument.apply(getSearchDocument());
            assertThat(apply).isEqualTo(expectedConversationEntity);
            return mongoIterable;
        });

        //when
        conversationRepository.getConversationsFor("2");

        verify(dbCollection).find(any(Document.class));
        verify(findIterable).map(any(Function.class));
    }

    @Test
    public void shouldReturnEmptyConversationWhenUserDoesNotExist() throws ExecutionException, InterruptedException {
        //given
        ConversationEntity expectedConversationEntity = getConversationEntity();
        when(findIterable.map(any(Function.class))).thenAnswer((Answer<MongoIterable>) invocationOnMock -> {
            Object[] arguments = invocationOnMock.getArguments();
            Function argument = (Function<Document, ConversationEntity>) arguments[0];

            Object apply = argument.apply(getSearchDocument());
            assertThat(apply).isEqualTo(expectedConversationEntity);
            return mongoIterable;
        });

        //when
        conversationRepository.getConversationsFor("2");

        verify(dbCollection).find(any(Document.class));
        verify(findIterable).map(any(Function.class));
    }

    @Test
    public void shouldReturnSuccessWhenNoMessagesAvailableForUser() throws ExecutionException, InterruptedException {
        //given
        ConversationEntity expectedConversationEntity = ConversationEntity.builder().build();
        when(findIterable.map(any(Function.class))).thenAnswer((Answer<MongoIterable>) invocationOnMock -> {
            Object[] arguments = invocationOnMock.getArguments();
            Function argument = (Function<Document, ConversationEntity>) arguments[0];

            Object apply = argument.apply(new Document());
            assertThat(apply).isEqualTo(expectedConversationEntity);
            return mongoIterable;
        });

        //when
        conversationRepository.getConversationsFor("2");

        //then
        verify(dbCollection).find(any(Document.class));
        verify(findIterable).map(any(Function.class));
    }

    @Test
    public void shouldSaveConversationForAUser() throws Exception {
        //Given
        ConversationEntity conversationEntity =
                ConversationEntity.builder()
                        .senderId("senderId")
                        .recipientId("recipientId")
                        .contentType(TEXT_PLAIN_UTF8)
                        .content("Hello World")
                        .build();

        //When
        conversationRepository.saveConversation(conversationEntity);

        //Then
        ArgumentCaptor<Document> captor = ArgumentCaptor.forClass(Document.class);
        verify(dbCollection).insertOne(captor.capture());

        Document insertedDocument = captor.getValue();
        assertThat(insertedDocument.getString("senderId")).isEqualTo("senderId");
        assertThat(insertedDocument.getString("recipientId")).isEqualTo("recipientId");
        assertThat(insertedDocument.getString("contentType")).isEqualTo("TEXT_PLAIN_UTF8");
        assertThat(insertedDocument.getString("content")).isEqualTo("Hello World");
        assertThat(insertedDocument.getString("timestamp")).isEqualTo(timeProvider.get().toString());
    }

    private ConversationEntity getConversationEntity() {
        return ConversationEntity.builder()
                .timestamp(234878234L)
                .content("Hello World")
                .contentType(TEXT_PLAIN_UTF8)
                .senderId(VALID_USER_ID)
                .recipientId(VALID_RECIPIENT_ID)
                .build();
    }

    private Document getSearchDocument() {
        return new Document("senderId", VALID_USER_ID)
                .append("recipientId", VALID_RECIPIENT_ID)
                .append("content", "Hello World")
                .append("timestamp", 234878234L)
                .append("contentType", TEXT_PLAIN_UTF8.toString());
    }
}