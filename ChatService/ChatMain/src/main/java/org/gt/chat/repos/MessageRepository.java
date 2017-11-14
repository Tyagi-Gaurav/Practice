package org.gt.chat.repos;

import org.gt.chat.domain.ConversationAggregate;

public interface MessageRepository {
    ConversationAggregate getMessages(String userId);
}
