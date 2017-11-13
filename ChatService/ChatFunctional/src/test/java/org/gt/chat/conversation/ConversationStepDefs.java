package org.gt.chat.conversation;

import com.google.inject.Inject;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.gt.chat.scenario.Context;

import static org.assertj.core.api.Assertions.assertThat;

public class ConversationStepDefs {

    @Inject
    private Context context;

    @Given("^a user is successfully authenticated$")
    public void aUserIsSuccessfullyAuthenticated() throws Throwable {
        //TODO Implement Me.
        context.createAuthenticatedUser();
    }

    @When("^a user tries to access their conversations$")
    public void aUserTriesToAccessTheirConversations() throws Throwable {
        context.requestFor("/conversation/"+123);
    }

    @Then("^the user should be able to receive their conversations in the response$")
    public void theUserShouldBeAbleToReceiveTheirConversationsInTheResponse() throws Throwable {
        String expectedResponse = "Hello World";
        assertThat(context.response()).isEqualTo(expectedResponse);
    }
}
