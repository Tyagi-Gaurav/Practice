package org.gt.chat.conversation;

import com.google.inject.Inject;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.gt.chat.domain.Conversation;
import org.gt.chat.domain.ConversationType;
import org.gt.chat.domain.Conversations;
import org.gt.chat.scenario.Context;

import java.time.LocalTime;
import java.util.Arrays;

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
        context.requestFor("/features/conversations/" + context.user().getId());
    }

    @Then("^the user should be able to receive their conversations in the response$")
    public void theUserShouldBeAbleToReceiveTheirConversationsInTheResponse() throws Throwable {
        Conversation conversation = new Conversation(
                "id123",
                LocalTime.now(),
                ConversationType.GROUP,
                "groupId123",
                "sender123",
                "Hello World"
                );
        Conversations conversations = new Conversations(Arrays.asList(conversation));
        assertThat(context.response()).isEqualTo(conversations);
    }
}
