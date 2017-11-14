package org.gt.chat.service;

import org.gt.chat.response.Conversations;

public interface ConversationService {
    Conversations getMessagesFor(String userId);
}
