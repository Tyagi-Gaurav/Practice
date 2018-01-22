package org.gt.chat.main.domain;

import lombok.*;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
@Builder
public class ConversationEntity {
    private final String messageId;
    private final String content;
    private final long receivedTimeStamp;
    private final String groupId;
    private final String senderId;
}
