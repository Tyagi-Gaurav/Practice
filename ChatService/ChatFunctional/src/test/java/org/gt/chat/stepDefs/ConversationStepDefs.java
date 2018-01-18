package org.gt.chat.stepDefs;

import com.google.inject.Inject;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.java.guice.ScenarioScoped;
import org.gt.chat.domain.main.TestConversation;
import org.gt.chat.domain.main.TestConversationType;
import org.gt.chat.domain.main.TestConversations;
import org.gt.chat.scenario.Context;

import javax.ws.rs.core.Response;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@ScenarioScoped
public class ConversationStepDefs {
    private final Context context;

    @Inject
    public ConversationStepDefs(Context context) {
        this.context = context;
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
        TestConversation conversation = new TestConversation(
                "2",
                234878234L,
                TestConversationType.ONE2ONE,
                "groupId",
                "senderId",
                "Hello World"
                );
        TestConversations conversations = new TestConversations(context.getRequestId(),
                Arrays.asList(conversation));
        Response response = context.response();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.readEntity(TestConversations.class)).isEqualTo(conversations);
    }
}
