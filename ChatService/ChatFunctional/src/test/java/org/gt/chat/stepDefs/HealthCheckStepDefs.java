package org.gt.chat.stepDefs;

import com.google.inject.Inject;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.java.guice.ScenarioScoped;
import org.gt.chat.domain.main.HealthStatus;
import org.gt.chat.scenario.Context;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ScenarioScoped
public class HealthCheckStepDefs {
    private Context context;

    @Inject
    public HealthCheckStepDefs(Context context) {
        this.context = context;
    }

    @Then("^the healthcheck should be successful$")
    public void theHealthcheckShouldBeSuccessful() throws Throwable {
        assertThat(context.response().getStatus()).isEqualTo(200);
        HealthStatus actual = context.response().readEntity(HealthStatus.class);

        assertThat(actual.getResult()).isEqualTo("OK");
        checkAllDependenciesAreOk(actual.getDependencies());
    }

    private void checkAllDependenciesAreOk(List<HealthStatus> dependencies) {
        assertThat(dependencies).isNotNull();
        assertThat(dependencies.size()).isEqualTo(1);
        HealthStatus actual = dependencies.get(0);
        assertThat(actual.getResult()).isEqualTo("OK");
        assertThat(actual.getName()).isEqualTo("audit");
        List<HealthStatus> dependencies1 = actual.getDependencies();
        assertThat(dependencies1).isNotNull();
        assertThat(dependencies1.size()).isEqualTo(1);
        assertThat(dependencies1.get(0).getResult()).isEqualTo("OK");
        assertThat(dependencies1.get(0).getName()).isEqualTo("database");
        assertThat(dependencies1.get(0).getDependencies()).isNull();
    }

    @When("^healthcheck endpoint is accessed for main application$")
    public void healthcheckEndpointIsAccessedForMainApplication() throws Throwable {
        context.requestFor("/private/healthcheck");
    }
}
