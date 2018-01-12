package org.gt.chat.main.audit.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
public class ConversationEntity {
    private final long messageId;
    private final String content;
    private final long receivedTimeStamp;
    private final String groupId;
    private final String senderId;
}
