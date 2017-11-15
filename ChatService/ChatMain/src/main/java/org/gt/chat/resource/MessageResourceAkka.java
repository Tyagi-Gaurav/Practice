package org.gt.chat.resource;

import akka.actor.ActorRef;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.Route;
import akka.japi.pf.PFBuilder;

import static akka.http.javadsl.server.Directives.*;
import static akka.http.javadsl.server.PathMatchers.segment;
import static akka.pattern.PatternsCS.ask;
import static java.util.regex.Pattern.compile;
import static scala.compat.java8.JFunction.func;

public class MessageResourceAkka  {
    private ActorRef messageActor;

    public MessageResourceAkka(ActorRef messageActor) {
        this.messageActor = messageActor;
    }

    public final  Route route = route(
            path(segment("conversations").slash(segment(compile("\\d+"))), (String value) ->
                get(() ->
                   onComplete(() -> ask(messageActor, value, 1000L),
                    functionResult ->
                        functionResult
                            .map(func(result -> complete(StatusCodes.OK, result, Jackson.marshaller())))
                            .recover(new PFBuilder<Throwable, Route>()
                                .matchAny(ex -> complete(StatusCodes.INTERNAL_SERVER_ERROR, ex.getMessage()))
                                .build())
                            .get())
            )
    ));
}
