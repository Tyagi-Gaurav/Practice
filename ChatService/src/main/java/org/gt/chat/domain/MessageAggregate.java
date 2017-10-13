package org.gt.chat.domain;

import java.util.List;

public class MessageAggregate {
    private final List<MessageEntity> messageEntityList;

    public MessageAggregate(List<MessageEntity> messageEntityList) {
        this.messageEntityList = messageEntityList;
    }

    public List<MessageEntity> getMessageEntityList() {
        return messageEntityList;
    }
}
