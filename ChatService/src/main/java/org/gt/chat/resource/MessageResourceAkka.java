package org.gt.chat.resource;

import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.Route;
import org.gt.chat.service.MessageService;

import static akka.http.javadsl.server.Directives.*;
import static akka.http.javadsl.server.PathMatchers.segment;
import static java.util.regex.Pattern.compile;

public class MessageResourceAkka  {
    private MessageService messageService;

    public MessageResourceAkka(MessageService messageService) {
        this.messageService = messageService;
    }

    public final  Route route = route(
            path(segment("message").slash("users").slash(segment(compile("\\d+"))), (value) ->
                    get(() -> complete(
                            StatusCodes.OK,
                            messageService.getMessagesFor(value),
                            Jackson.marshaller())
                    )
            )
    );


}
