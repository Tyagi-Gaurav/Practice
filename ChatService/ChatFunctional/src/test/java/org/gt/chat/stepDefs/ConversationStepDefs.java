package org.gt.chat.stepDefs;

import com.google.inject.Inject;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.java.guice.ScenarioScoped;


import org.gt.chat.domain.main.TestGetConversationsResponse;
import org.gt.chat.domain.main.TestMessageContentType;
import org.gt.chat.domain.main.TestSendConversationRequest;
import org.gt.chat.scenario.Context;
import org.gt.chat.stepDefs.service.MockDatabaseService;
import org.gt.chat.stepDefs.service.MockUserService;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.gt.chat.domain.main.TestMessageContentType.TEXT_PLAIN_UTF8;

@ScenarioScoped
public class ConversationStepDefs {
    private final Context context;
    private MockDatabaseService mockDatabaseService;
    private MockUserService mockUserService;

    @Inject
    public ConversationStepDefs(Context context,
                                MockDatabaseService mockDatabaseService,
                                MockUserService mockUserService) {
        this.context = context;
        this.mockDatabaseService = mockDatabaseService;
        this.mockUserService = mockUserService;
    }

    @Given("^user (.*) is successfully authenticated$")
    public void aUserIsSuccessfullyAuthenticated(String userName) throws Throwable {
        //TODO Implement Me.
    }

    @When("^user (.*) tries to access their conversations$")
    public void aUserTriesToAccessTheirConversations(String userName) throws Throwable {
        context.getRequestFor("/conversations/" + mockUserService.getUserIdFor(userName));
    }

    @Then("^the user (.*) should be able to receive their conversations in the response$")
    public void theUserShouldBeAbleToReceiveTheirConversationsInTheResponse(String userName) throws Throwable {
        TestGetConversationsResponse conversation = TestGetConversationsResponse.builder()
                .globalRequestId(context.getRequestId())
                .userId(mockUserService.getUserIdFor(userName))
                .messages(TestGetConversationsResponse.TestMessages.builder()
                        .senderId("senderId")
                        .messageDetails(Arrays.asList(
                                TestGetConversationsResponse.TestMessageDetail.builder()
                                        .content("Hello World")
                                        .timestamp(234878234L)
                                        .contentType(TEXT_PLAIN_UTF8)
                                        .received(true)
                                        .build()
                        ))
                        .build())
                .build();

        TestGetConversationsResponse responseEntity =
                context.readResponse(TestGetConversationsResponse.class);
        assertThat(context.getResponse().getStatus()).isEqualTo(200);
        assertThat(responseEntity).isEqualTo(conversation);
    }

    @And("^the user (.*) has some conversations available on the server$")
    public void theUserHasSomeConversationsAvailableOnTheServer(String userName) throws Throwable {
        mockDatabaseService.createConversationsForUser(mockUserService.getUserIdFor(userName));
    }

    @When("^user (.*) sends a message '(.*)' to user (.*) successfully$")
    public void userAliceTriesToSendAMessageToUserBob(String userA, String message, String userB) throws Throwable {
        TestSendConversationRequest conversationRequest =
                TestSendConversationRequest.builder()
                        .senderId(mockUserService.getUserIdFor(userA))
                        .recipientUserId(mockUserService.getUserIdFor(userB))
                        .messageDetail(TestSendConversationRequest.TestMessageDetail.builder()
                                .content(message)
                                .contentType(TestMessageContentType.TEXT_PLAIN_UTF8)
                                .build())
                        .build();
        context.postRequestFor("/conversations/", conversationRequest);
        assertThat(context.getResponse().getStatus()).isEqualTo(202);
    }

    @Then("^user (.*) should be able to receive the message '(.*)' from (.*)$")
    public void userBobShouldBeAbleToReceiveTheConversation(String recipient, String message, String sender) throws Throwable {
        TestGetConversationsResponse responseEntity =
                context.readResponse(TestGetConversationsResponse.class);
        assertThat(context.getResponse().getStatus()).isEqualTo(200);
        assertThat(responseEntity.getUserId()).isEqualTo(mockUserService.getUserIdFor(recipient));
        assertThat(responseEntity.getMessages()).isNotNull();
        assertThat(responseEntity.getMessages().getSenderId()).isEqualTo(mockUserService.getUserIdFor(sender));
        
    }
}
