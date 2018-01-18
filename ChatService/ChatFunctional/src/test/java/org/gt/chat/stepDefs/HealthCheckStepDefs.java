package org.gt.chat.stepDefs;

import com.google.inject.Inject;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.java.guice.ScenarioScoped;
import org.gt.chat.domain.main.HealthStatus;
import org.gt.chat.scenario.Context;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import static org.assertj.core.api.Assertions.assertThat;

@ScenarioScoped
public class HealthCheckStepDefs {
    private Context context;
    private Client client = ClientBuilder.newClient();

    @Inject
    public HealthCheckStepDefs(Context context) {
        this.context = context;
    }

    @Then("^the healthcheck should be successful$")
    public void theHealthcheckShouldBeSuccessful() throws Throwable {
        assertThat(context.response().getStatus()).isEqualTo(200);
        HealthStatus actual = context.response().readEntity(HealthStatus.class);

        assertThat(actual.getResult()).isEqualTo("OK");
//        assertThat(actual.getAuditServer().getResult()).isEqualTo("OK");
    }

    @When("^healthcheck endpoint is accessed for main application$")
    public void healthcheckEndpointIsAccessedForMainApplication() throws Throwable {
        context.requestFor("/private/healthcheck");
    }
}
