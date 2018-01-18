package org.gt.chat.main.resource;

import akka.actor.ActorRef;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.gt.chat.main.domain.HealthCheckRequest;
import org.gt.chat.main.domain.HealthCheckResponse;

import javax.ws.rs.Path;

import static akka.http.javadsl.server.PathMatchers.segment;
import static akka.pattern.PatternsCS.ask;
import static scala.compat.java8.JFunction.func;

@Api(value = "HealthCheck", produces = "application/json")
@Path("/private/healthcheck")
public class HealthCheckResource extends AllDirectives {
    private ActorRef conversationActorRef;
    private DocumentationRoute documentationRoute;

    public HealthCheckResource(ActorRef ref,
                               DocumentationRoute documentationRoute) {
        conversationActorRef = ref;
        this.documentationRoute = documentationRoute;
    }

    public Route getRoute() {
        return route(
                healthCheckRoute(),
                documentationRoute.routes());
    }

    @Path("/private/healthcheck")
    @ApiOperation(value = "Perform healthcheck on the actor", code = 200, httpMethod = "GET", response = HealthCheckResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public Route healthCheckRoute() {
        return path(segment("private")
                .slash(segment("healthcheck")), () ->
                get(() -> onComplete(() -> ask(conversationActorRef, new HealthCheckRequest(), 5000L),
                        functionResult ->
                                functionResult
                                        .map(func(result -> complete(StatusCodes.OK, result, Jackson.marshaller())))
                                        .get()))
        );
    }
}
