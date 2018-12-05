package org.gt.chat.main.service;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.testkit.TestActor;
import akka.testkit.TestProbe;
import akka.testkit.javadsl.TestKit;
import org.gt.chat.domain.HealthCheckRequest;
import org.gt.chat.domain.HealthCheckResponse;
import org.gt.chat.main.domain.ContentType;
import org.gt.chat.main.domain.ConversationEntity;
import org.gt.chat.main.domain.api.ConversationRequest;
import org.gt.chat.main.domain.api.GetConversationResponse;
import org.gt.chat.main.domain.api.SendConversationRequest;
import org.gt.chat.main.domain.dto.ConversationSaveDTO;
import org.gt.chat.main.repos.ConversationRepository;
import org.gt.chat.main.util.ActorSystemTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static akka.pattern.PatternsCS.ask;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConversationActorTest extends ActorSystemTest {

    @Mock
    private CompletionStage<ActorRef> auditRefCompletionStage;

    @Mock
    private CompletableFuture<ActorRef> actorRefCompletableFuture;

    @Mock
    private Function<ActorContext, CompletionStage<ActorRef>> auditProvider;

    @Mock
    private ConversationRepository conversationRepository;

    private static final String GLOBAL_REQUEST_ID = "Test-request-Id";
    private static final String VALID_USER_ID = "2";
    private TestProbe auditTestProbe;
    private static final String ACTOR_NAME = "ConversationActor";
    private Props props;

    @Before
    public void setUp() throws Exception {
        auditTestProbe = new TestProbe(actorSystem);
        CompletableFuture<ActorRef> actorRefCompletableFuture = new CompletableFuture<>();
        actorRefCompletableFuture.complete(auditTestProbe.ref());
        when(auditRefCompletionStage.whenCompleteAsync(any(BiConsumer.class)))
                .thenReturn(actorRefCompletableFuture);

        when(auditProvider.apply(any(ActorContext.class))).thenReturn(auditRefCompletionStage);

        when(conversationRepository.getConversationsFor("2"))
                .thenReturn(getConversationEntity());

        props = Props.create(ConversationActor.class, auditProvider, conversationRepository);
    }

    @Test
    public void getMessagesForUser() {
        //Given
        GetConversationResponse conversations = getExpectedConversations();

        //When
        new TestKit(actorSystem) {{
            final ActorRef subject = actorSystem.actorOf(props);

            subject.tell(userWithId("2"), getRef());

            expectMsg(duration("5 second"), conversations);
            verify(auditRefCompletionStage).whenCompleteAsync(any(BiConsumer.class));
            verify(conversationRepository).getConversationsFor("2");
        }};
    }

        @Test
    public void healthCheckShouldBeSuccessfulWhenActorIsRunning() throws Exception {
        //Given
        HealthCheckResponse dependenciesHealthCheck =
                HealthCheckResponse.builder().name("audit").result("OK").build();

        when(auditRefCompletionStage.toCompletableFuture()).thenReturn(actorRefCompletableFuture);
        when(actorRefCompletableFuture.get()).thenReturn(auditTestProbe.ref());
        when(actorRefCompletableFuture.isDone()).thenReturn(true);

        //When
        new TestKit(actorSystem) {{
            auditTestProbe.setAutoPilot(new TestActor.AutoPilot() {
                @Override
                public TestActor.AutoPilot run(ActorRef sender, Object msg) {
                    sender.tell(dependenciesHealthCheck, ActorRef.noSender());
                    return noAutoPilot();
                }
            });

            final ActorRef subject = actorSystem.actorOf(props);

            CompletionStage<Object> ask = ask(subject, HealthCheckRequest.builder().build(), 5000);
            Object healthCheckResult = ask.toCompletableFuture().get();

            assertThat(healthCheckResult).isInstanceOf(HealthCheckResponse.class);
            HealthCheckResponse healthCheckResponse = (HealthCheckResponse) healthCheckResult;
            assertThat(healthCheckResponse.getName()).isEqualTo(ACTOR_NAME);
            assertThat(healthCheckResponse.getResult()).isEqualTo("OK");
            auditTestProbe.expectMsgClass(HealthCheckRequest.class);
        }};
    }

    @Test
    public void saveConversationForUserInDatabase() throws Exception {
        //when
        new TestKit(actorSystem) {{
            final ActorRef subject = actorSystem.actorOf(props);

            ConversationSaveDTO msg = saveDTO();
            subject.tell(msg, getRef());

            ArgumentCaptor<ConversationEntity> argumentCaptor =
                    ArgumentCaptor.forClass(ConversationEntity.class);
            verify(conversationRepository).saveConversation(argumentCaptor.capture());
            ConversationEntity conversationEntity = argumentCaptor.getValue();

            assertThat(conversationEntity.getContent()).
                    isEqualTo(msg.getContent());
        }};
    }


    private GetConversationResponse getExpectedConversations() {
        return GetConversationResponse.builder()
                .globalRequestId(GLOBAL_REQUEST_ID)
                .messageDetails(asList(GetConversationResponse.MessageDetail.builder()
                        .timestamp(234878234L)
                        .content("Hello World")
                        .received(true)
                        .contentType(ContentType.TEXT_PLAIN_UTF8)
                        .senderId("3")
                        .recipientId(VALID_USER_ID)
                        .build()))
                .build();
    }

    private List<ConversationEntity> getConversationEntity() {
        return asList(ConversationEntity.builder()
                .senderId("3")
                .recipientId(VALID_USER_ID)
                .received(true)
                .timestamp(234878234L)
                .content("Hello World")
                .contentType(ConversationEntity.ContentTypeEntity.TEXT_PLAIN_UTF8)
                .build());
    }

    private ConversationRequest userWithId(String userId) {
        return ConversationRequest
                .builder()
                .globalRequestId(GLOBAL_REQUEST_ID)
                .userId(userId).build();
    }

    private ConversationSaveDTO saveDTO() {
        return ConversationSaveDTO.builder()
                .content("Hello World")
                .build();
    }

}