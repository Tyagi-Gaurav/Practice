package org.gt.chat.main.resource;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.ContentTypes;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.testkit.JUnitRouteTest;
import akka.http.javadsl.testkit.TestRoute;
import akka.http.javadsl.testkit.TestRouteResult;
import akka.testkit.TestActor;
import akka.testkit.TestProbe;
import io.swagger.annotations.ApiOperation;
import io.swagger.jaxrs.config.DefaultReaderConfig;
import org.assertj.core.api.Assertions;
import org.gt.chat.main.domain.HealthCheckRequest;
import org.gt.chat.main.domain.HealthCheckResponse;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.Path;
import java.lang.reflect.Method;
import java.util.UUID;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class HealthCheckResourceTest extends JUnitRouteTest {

    private ActorSystem actorSystem = ActorSystem.create(UUID.randomUUID().toString());
    private TestProbe conversationActor = new TestProbe(actorSystem);
    private HealthCheckResource healthCheckResource;
    private TestRoute route;
    private DocumentationRoute documentationRoute;

    @Before
    public void setUp() throws Exception {
        DefaultReaderConfig readerConfig = new DefaultReaderConfig();
        documentationRoute = new DocumentationRoute(readerConfig);
        healthCheckResource = new HealthCheckResource(conversationActor.ref(), documentationRoute);
        route = testRoute(healthCheckResource.getRoute());
    }

    @Test
    public void shouldReturnOKWhenAllActorsAreRunning() throws Exception {
        conversationActor.setAutoPilot(new TestActor.AutoPilot() {
            @Override
            public TestActor.AutoPilot run(ActorRef sender, Object msg) {
                sender.tell(HealthCheckResponse.builder()
                                .name("ConversationActor")
                                .result("OK").build(),
                        ActorRef.noSender());
                return noAutoPilot();
            }
        });
        TestRouteResult run = route.run(HttpRequest.GET("/private/healthcheck"));
        run.assertStatusCode(200)
                .assertContentType(ContentTypes.APPLICATION_JSON);

        HealthCheckResponse entity = run.entity(Jackson.unmarshaller(HealthCheckResponse.class));
        assertThat(HealthCheckResponse.builder()
                .name("ConversationActor")
                .result("OK")
                .build()).isEqualTo(entity);
        conversationActor.expectMsg(new HealthCheckRequest());
    }

    @Test
    public void shouldGenerateSwaggerDocumentationForRoute() throws Exception {
        //Given
        Method healthCheckRouteMethod = healthCheckResource.getClass().getMethod("healthCheckRoute", null);
        Assertions.assertThat(healthCheckRouteMethod.isAnnotationPresent(Path.class)).isTrue();
        Assertions.assertThat(healthCheckRouteMethod.getAnnotation(Path.class).value()).isEqualTo("/private/healthcheck");

        Assertions.assertThat(healthCheckRouteMethod.isAnnotationPresent(ApiOperation.class)).isTrue();
        Assertions.assertThat(healthCheckRouteMethod.getAnnotation(ApiOperation.class).value()).isEqualTo("Perform healthcheck on the actor");
        Assertions.assertThat(healthCheckRouteMethod.getAnnotation(ApiOperation.class).code()).isEqualTo(200);
        Assertions.assertThat(healthCheckRouteMethod.getAnnotation(ApiOperation.class).httpMethod()).isEqualTo("GET");
        Assertions.assertThat(healthCheckRouteMethod.getAnnotation(ApiOperation.class).response()).isEqualTo(HealthCheckResponse.class);
    }
}
