package org.gt.chat.service;

import org.gt.chat.response.Messages;

public interface MessageService {
    Messages getMessagesFor(String userId);
}
