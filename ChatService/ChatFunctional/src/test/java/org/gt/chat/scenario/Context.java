package org.gt.chat.scenario;

import com.google.inject.Inject;
import cucumber.runtime.java.guice.ScenarioScoped;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.UUID;

import static org.gt.chat.scenario.ConfigVariables.HOST;

@ScenarioScoped
public class Context {
    private ScenarioConfig config;

    @Inject
    public Context(ScenarioConfig config) {
        this.config = config;
        requestId = UUID.randomUUID().toString();
    }

    private User user;
    private Client client = ClientBuilder.newClient();
    private Response response;
    private String requestId;

    public void createAuthenticatedUser() {
        user = new User("2");
    }

    public void requestFor(String path) {
        response = client.target(config.getString(HOST) + path)
                .request(MediaType.APPLICATION_JSON)
                .header("X-request-id", requestId)
                .get();
    }

    public String getRequestId() {
        return requestId;
    }

    public Response response() {
        return response;
    }

    public User user() {
        return user;
    }
}
