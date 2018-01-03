package org.gt.chat.stepDefs;

import com.google.inject.Inject;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.java.guice.ScenarioScoped;
import org.gt.chat.domain.Conversation;
import org.gt.chat.domain.ConversationType;
import org.gt.chat.domain.Conversations;
import org.gt.chat.scenario.Context;

import javax.ws.rs.core.Response;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@ScenarioScoped
public class ConversationStepDefs {

    private final Context context;
    private final ChatMainApplcationController chatMainApplcationController;

    @Inject
    public ConversationStepDefs(Context context, ChatMainApplcationController chatMainApplcationController) {
        this.context = context;
        this.chatMainApplcationController = chatMainApplcationController;
    }

    @Given("^a user is successfully authenticated$")
    public void aUserIsSuccessfullyAuthenticated() throws Throwable {
        //TODO Implement Me.
        context.createAuthenticatedUser();
    }

    @When("^a user tries to access their conversations$")
    public void aUserTriesToAccessTheirConversations() throws Throwable {
        context.requestFor("conversations/" + context.user().getId());
    }

    @Then("^the user should be able to receive their conversations in the response$")
    public void theUserShouldBeAbleToReceiveTheirConversationsInTheResponse() throws Throwable {
        Conversation conversation = new Conversation(
                "2",
                234878234L,
                ConversationType.ONE2ONE,
                "groupId",
                "senderId",
                "Hello World"
                );
        Conversations conversations = new Conversations(Arrays.asList(conversation));
        Response response = context.response();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.readEntity(Conversations.class)).isEqualTo(conversations);
    }
}
