package org.gt.chat.resource;

import org.gt.chat.response.Message;
import org.gt.chat.response.Messages;
import org.gt.chat.service.MessageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MessageResourceTest {
    private MessageService messageService = mock(MessageService.class);
    private MessageResource messageResource = new MessageResource(messageService);

    @Test
    @DisplayName("")
    void getMessages() {
        //Given
        String userId = "1";
        Message expectedMessage = new Message(
                "2",
                "Hello World",
                234878234L);
        List<Message> messageList = new ArrayList<>();
        messageList.add(expectedMessage);
        Messages messages = new Messages(messageList);
        when(messageService.getMessagesFor(userId)).thenReturn(messages);

        //When & Then
        assertThat(messageResource.getMessages("1")).isEqualTo(messages);
    }
}