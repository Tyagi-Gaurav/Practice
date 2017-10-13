package org.gt.chat.resource;

import org.gt.chat.response.Messages;
import org.gt.chat.service.MessageService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("message")
public class MessageResource {
    private MessageService messageService;

    public MessageResource(MessageService messageService) {
        this.messageService = messageService;
    }

    @Path("users/{userId}")
    @GET
    @Produces("application/json")
    public Messages getMessages(@PathParam("userId") String userId) {
        return messageService.getMessagesFor(userId);
    }
}
