package org.gt.chat.exception;

import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.ExceptionHandler;
import com.google.inject.Singleton;

import java.util.function.Supplier;

import static akka.http.javadsl.server.Directives.complete;

@Singleton
public class MessageExceptionHandler implements Supplier<ExceptionHandler> {
    @Override
    public ExceptionHandler get() {
        return ExceptionHandler.newBuilder()
                .match(InvalidUserException.class,
                        ex -> complete(StatusCodes.NOT_FOUND,
                                new ErrorResponse("CHT_0001", "Invalid User: " + ex.getMessage()),
                                Jackson.marshaller()))
                .build();
    }
}
