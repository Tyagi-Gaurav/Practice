package org.gt.chat.resource;

import akka.actor.ActorRef;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.ExceptionHandler;
import akka.http.javadsl.server.Route;
import org.gt.chat.exception.MessageExceptionHandler;

import java.util.function.Supplier;

import static akka.http.javadsl.server.Directives.*;
import static akka.http.javadsl.server.PathMatchers.segment;
import static akka.pattern.PatternsCS.ask;
import static java.util.regex.Pattern.compile;
import static scala.compat.java8.JFunction.func;

public class MessageResourceAkka {
    private ActorRef messageActor;
    private Supplier<ExceptionHandler> messageExceptionHandler;

    public MessageResourceAkka(ActorRef messageActor, Supplier<ExceptionHandler> messageExceptionHandler) {
        this.messageActor = messageActor;
        this.messageExceptionHandler = messageExceptionHandler;
    }

    public final Route route = route(
        path(segment("conversations").slash(segment(compile("\\d+"))), (String value) ->
                handleExceptions(messageExceptionHandler.get(),
                        () -> get(() ->
                onComplete(() -> ask(messageActor, value, 1000L),
                    functionResult ->
                        functionResult
                            .map(func(result -> complete(StatusCodes.OK, result, Jackson.marshaller())))
                            .get())
            ))
        ));
}
