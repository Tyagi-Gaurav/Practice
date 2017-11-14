package org.gt.chat.service;

import org.gt.chat.domain.ConversationAggregate;
import org.gt.chat.repos.MessageRepository;
import org.gt.chat.response.Conversations;
import org.gt.chat.response.Conversation;

import java.util.List;
import java.util.stream.Collectors;

public class ChatMessageService implements ConversationService {
    private final MessageRepository repository;

    public ChatMessageService(MessageRepository repository) {
        this.repository = repository;
    }

    @Override
    public Conversations getMessagesFor(String userId) {
        ConversationAggregate messageAggregate = repository.getMessages(userId);
        List<Conversation> collect = messageAggregate
                .getMessageEntityList()
                .stream()
                .map(Conversation::from)
                .collect(Collectors.toList());

        return new Conversations(collect);
    }
}
