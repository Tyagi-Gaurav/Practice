package org.gt.chat.main.resource;

import akka.actor.ActorRef;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.HttpEntity;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.ExceptionHandler;
import akka.http.javadsl.server.Route;
import akka.http.javadsl.unmarshalling.Unmarshaller;
import io.swagger.annotations.*;
import org.gt.chat.main.domain.api.ConversationRequest;
import org.gt.chat.main.domain.api.GetConversationResponse;
import org.gt.chat.main.domain.api.SendConversationRequest;
import org.gt.chat.main.domain.dto.ConversationSaveDTO;

import javax.ws.rs.Path;
import java.util.UUID;
import java.util.function.Supplier;

import static akka.http.javadsl.model.StatusCodes.ACCEPTED;
import static akka.http.javadsl.server.PathMatchers.segment;
import static akka.pattern.PatternsCS.ask;
import static java.util.regex.Pattern.compile;
import static scala.compat.java8.JFunction.func;

@Api(value = "GetConversationResponse", produces = "application/json")
@Path("/")
public class MessageResourceAkka extends AllDirectives {
    private final DocumentationRoute documentationRoute;
    private ActorRef messageActor;
    private Supplier<ExceptionHandler> messageExceptionHandler;

    public MessageResourceAkka(ActorRef messageActor,
                               Supplier<ExceptionHandler> messageExceptionHandler,
                               DocumentationRoute documentationRoute) {
        this.messageActor = messageActor;
        this.messageExceptionHandler = messageExceptionHandler;
        this.documentationRoute = documentationRoute;
    }

    @Path("/conversations/{userId}")
    @ApiOperation(value = "Return conversations for a user", code = 200, httpMethod = "GET", response = GetConversationResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", required = true, dataType = "integer", paramType = "path", value = "ID of user that needs conversations")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public Route getConversationsRoute() {
        return optionalHeaderValueByName("X-request-id", requestId ->
                path(segment("conversations").slash(segment(compile(".*"))), (String value) ->
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

    public Route postConversationsRoute() {
        final Unmarshaller<HttpEntity, SendConversationRequest> unmarshaller = Jackson.unmarshaller(SendConversationRequest.class);

        return optionalHeaderValueByName("X-request-id", requestId ->
                path(segment("conversations"), () ->
                    handleExceptions(messageExceptionHandler.get(),
                            () -> post(() ->
                                    entity(unmarshaller, sendConversation ->
                                            onComplete(() ->
                                                ask(messageActor, ConversationSaveDTO.builder()
                                                        .senderId(sendConversation.getSenderId())
                                                        .recipientId(sendConversation.getRecipientUserId())
                                                        .message(ConversationSaveDTO.MessageDetail.builder()
                                                                .contentType(sendConversation.getMessageDetail().getContentType())
                                                                .content(sendConversation.getMessageDetail().getContent())
                                                                .build())
                                                        .build(), 1000L),
                                                functionResult -> functionResult.map(func(result ->
                                                        complete(StatusCodes.ACCEPTED, result, Jackson.marshaller()))).get()
                            )))
        )));
    }

    public Route getRoute() {
        return route(
                getConversationsRoute(),
                postConversationsRoute(),
                documentationRoute.routes());
    }
}
