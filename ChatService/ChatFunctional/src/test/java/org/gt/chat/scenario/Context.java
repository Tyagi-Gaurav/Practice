package org.gt.chat.scenario;

import com.google.inject.Inject;
import cucumber.runtime.java.guice.ScenarioScoped;
import org.gt.chat.domain.main.TestSendConversationRequest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.UUID;

import static org.gt.chat.scenario.ConfigVariables.HOST;

@ScenarioScoped
public class Context {
    private ScenarioConfig config;
    private Object responseObject;

    @Inject
    public Context(ScenarioConfig config) {
        this.config = config;
        requestId = UUID.randomUUID().toString();
    }

    private Client client = ClientBuilder.newClient();
    private Response response;
    private String requestId;

    public void getRequestFor(String path) {
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

    public void postRequestFor(String path, TestSendConversationRequest conversationRequest) {
        response = client.target(config.getString(HOST) + path)
                .request(MediaType.APPLICATION_JSON)
                .header("X-request-id", requestId)
                .post(Entity.entity(conversationRequest, MediaType.APPLICATION_JSON));
    }

    public <T> T readResponse(Class<T> targetClass) {
        responseObject = response.readEntity(targetClass);
        return (T) responseObject;
    }

    public Object getResponseObject() {
        return responseObject;
    }

    public Response getResponse() {
        return response;
    }
}
