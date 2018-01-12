package org.gt.chat.main.resource;

import akka.actor.ActorRef;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.HttpHeader;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.ExceptionHandler;
import akka.http.javadsl.server.Route;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.*;
import io.swagger.jaxrs.Reader;
import io.swagger.jaxrs.config.DefaultReaderConfig;
import io.swagger.models.Swagger;
import io.swagger.util.Json;
import org.gt.chat.main.audit.domain.ConversationRequest;
import org.gt.chat.main.response.Conversations;

import javax.ws.rs.Path;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

import static akka.http.javadsl.server.PathMatchers.segment;
import static akka.pattern.PatternsCS.ask;
import static java.util.regex.Pattern.compile;
import static scala.compat.java8.JFunction.func;

@Api(value = "Conversations", produces = "application/json")
@Path("/")
public class MessageResourceAkka extends AllDirectives {
    private ActorRef messageActor;
    private Supplier<ExceptionHandler> messageExceptionHandler;
    private DefaultReaderConfig readerConfig = new DefaultReaderConfig();

    public MessageResourceAkka(ActorRef messageActor, Supplier<ExceptionHandler> messageExceptionHandler) {
        this.messageActor = messageActor;
        this.messageExceptionHandler = messageExceptionHandler;
    }

    private Route swaggerApiDocsRoute() {
        return path(segment("api-docs").slash(segment("swagger.json")),
                () -> get(() -> complete(swaggerJson())));
    }

    private Route swaggerRoute() {
        return path(segment("swagger"),
                () -> getFromResource("swagger-ui/index.html"));
    }

    final Function<HttpHeader, Optional<Object>> extractHostPort = header -> {
        return Optional.ofNullable(header);
    };

    @Path("/conversations/{userId}")
    @ApiOperation(value = "Return conversations for a user", code = 200, httpMethod = "GET", response = Conversations.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", required = true, dataType = "integer", paramType = "path", value = "ID of user that needs conversations")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public Route conversationsRoute() {
        return optionalHeaderValueByName("X-request-id", requestId ->
                path(segment("conversations").slash(segment(compile("\\d+"))), (String value) ->
                        handleExceptions(messageExceptionHandler.get(),
                                () -> get(() -> {
                                    String requestIdString = requestId.orElse(UUID.randomUUID().toString());
                                    ConversationRequest conversationRequest = ConversationRequest.builder()
                                            .globalRequestId(requestIdString)
                                            .userId(value).build();
                                    return onComplete(() -> ask(messageActor, conversationRequest, 1000L),
                                            functionResult ->
                                                    functionResult
                                                            .map(func(result -> complete(StatusCodes.OK, result, Jackson.marshaller())))
                                                            .get());
                                }))
                ));
    }


    public Route getRoute() {
        return route(
                conversationsRoute(),
                swaggerApiDocsRoute(),
                swaggerRoute(),
                getFromResourceDirectory("swagger-ui"));
    }

    private String swaggerJson() {
        try {
            final Swagger swaggerConfig = new Swagger();
            final Reader reader = new Reader(swaggerConfig, readerConfig);
            final Swagger swagger = reader.read(MessageResourceAkka.class);
            return Json.pretty().writeValueAsString(swagger);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}