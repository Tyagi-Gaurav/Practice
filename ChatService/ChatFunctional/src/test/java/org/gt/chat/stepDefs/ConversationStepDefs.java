package org.gt.chat.stepDefs;

import com.google.inject.Inject;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.java.guice.ScenarioScoped;


import org.gt.chat.domain.main.TestGetConversationsResponse;
import org.gt.chat.scenario.Context;
import org.gt.chat.stepDefs.service.MockDatabaseService;

import javax.ws.rs.core.Response;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.gt.chat.domain.main.TestGetConversationsResponse.TestContentType.TEXT_PLAIN_UTF8;

@ScenarioScoped
public class ConversationStepDefs {
    private final Context context;
    private MockDatabaseService mockDatabaseService;

    @Inject
    public ConversationStepDefs(Context context, MockDatabaseService mockDatabaseService) {
        this.context = context;
        this.mockDatabaseService = mockDatabaseService;
    }

    @Given("^a user is successfully authenticated$")
    public void aUserIsSuccessfullyAuthenticated() throws Throwable {
        //TODO Implement Me.
        context.createAuthenticatedUser();
    }

    @When("^a user tries to access their conversations$")
    public void aUserTriesToAccessTheirConversations() throws Throwable {
        context.requestFor("/conversations/" + context.user().getId());
    }

    @Then("^the user should be able to receive their conversations in the response$")
    public void theUserShouldBeAbleToReceiveTheirConversationsInTheResponse() throws Throwable {
        TestGetConversationsResponse conversation = TestGetConversationsResponse.builder()
                .globalRequestId(context.getRequestId())
                .userId("2")
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

        Response response = context.response();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.readEntity(TestGetConversationsResponse.class)).isEqualTo(conversation);
    }

    @And("^the user has some conversations available on the server$")
    public void theUserHasSomeConversationsAvailableOnTheServer() throws Throwable {
        mockDatabaseService.createConversationsForUser();
    }
}
