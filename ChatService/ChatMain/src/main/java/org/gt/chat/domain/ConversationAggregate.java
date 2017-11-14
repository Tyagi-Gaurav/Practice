package org.gt.chat.domain;

import java.util.List;

public class ConversationAggregate {
    private final List<ConversationEntity> messageEntityList;

    public ConversationAggregate(List<ConversationEntity> messageEntityList) {
        this.messageEntityList = messageEntityList;
    }

    public List<ConversationEntity> getMessageEntityList() {
        return messageEntityList;
    }
}
