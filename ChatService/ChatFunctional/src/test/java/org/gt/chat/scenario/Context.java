package org.gt.chat.scenario;

import com.google.inject.Inject;
import cucumber.runtime.java.guice.ScenarioScoped;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.gt.chat.scenario.ConfigVariables.HOST;

@ScenarioScoped
public class Context {
    @Inject
    private ScenarioConfig config;

    private User user;
    private Client client = ClientBuilder.newClient();
    private Response response;

    public void createAuthenticatedUser() {
        user = new User("2");
    }

    public void requestFor(String path) {
        response = client.target(config.getString(HOST) + path)
                .request(MediaType.APPLICATION_JSON)
                .get();
    }

    public Response response() {
        return response;
    }

    public User user() {
        return user;
    }
}
