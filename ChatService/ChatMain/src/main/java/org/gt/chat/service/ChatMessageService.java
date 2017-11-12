package org.gt.chat.service;

import org.gt.chat.domain.MessageAggregate;
import org.gt.chat.repos.MessageRepository;
import org.gt.chat.response.Message;
import org.gt.chat.response.Messages;

import java.util.List;
import java.util.stream.Collectors;

public class ChatMessageService implements MessageService {
    private final MessageRepository repository;

    public ChatMessageService(MessageRepository repository) {
        this.repository = repository;
    }

    @Override
    public Messages getMessagesFor(String userId) {
        MessageAggregate messageAggregate = repository.getMessages(userId);
        List<Message> collect = messageAggregate
                .getMessageEntityList()
                .stream()
                .map(Message::from)
                .collect(Collectors.toList());

        return new Messages(collect);
    }
}
