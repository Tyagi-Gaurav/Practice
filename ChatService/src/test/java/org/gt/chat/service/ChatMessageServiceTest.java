package org.gt.chat.service;

import org.gt.chat.domain.MessageAggregate;
import org.gt.chat.domain.MessageEntity;
import org.gt.chat.repos.MessageRepository;
import org.gt.chat.response.Message;
import org.gt.chat.response.Messages;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ChatMessageServiceTest {
    private MessageRepository repository = mock(MessageRepository.class);
    private MessageService messageService = new ChatMessageService(repository);

    @Test
    public void getMessagesForUser() {
        // Given
        String userId = "1";
        MessageEntity messageEntity = new MessageEntity(
                2L,
                "Hello World",
                234878234L
                );
        List<MessageEntity> entityList = new ArrayList<>();
        entityList.add(messageEntity);
        MessageAggregate aggregate = new MessageAggregate(entityList);

        Message expectedMessage = new Message(
                "2",
                "Hello World",
                234878234L);
        List<Message> messageList = new ArrayList<>();
        messageList.add(expectedMessage);
        Messages messages = new Messages(messageList);

        when(repository.getMessages(userId)).thenReturn(aggregate);

        // Then
        assertThat(messageService.getMessagesFor(userId)).isEqualTo(messages);
    }

}