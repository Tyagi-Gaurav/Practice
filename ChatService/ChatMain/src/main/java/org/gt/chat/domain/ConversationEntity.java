package org.gt.chat.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class ConversationEntity {
    private final long messageId;
    private final String content;
    private final long receivedTimeStamp;
    private final String groupId;
    private final String senderId;
}
