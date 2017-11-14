package org.gt.chat.response;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.gt.chat.domain.ConversationEntity;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
public class Conversation {
    private String id;
    private long epochTimeStamp;
    private ConversationType conversationType;
    private String groupId;
    private String senderId;
    private String content;

    public static Conversation from(ConversationEntity messageEntity) {
        return new Conversation(
                String.valueOf(messageEntity.getMessageId()),
                messageEntity.getReceivedTimeStamp(),
                ConversationType.ONE2ONE,
                messageEntity.getGroupId(),
                messageEntity.getSenderId(),
                messageEntity.getContent());
    }
}
