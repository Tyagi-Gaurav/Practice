package org.gt.chat.repos;

import org.gt.chat.domain.MessageAggregate;

public interface MessageRepository {
    MessageAggregate getMessages(String userId);
}
