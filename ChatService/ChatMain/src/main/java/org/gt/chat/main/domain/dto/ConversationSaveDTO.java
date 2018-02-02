package org.gt.chat.main.domain.dto;

import lombok.*;
import org.gt.chat.main.domain.ContentType;

@Builder
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ConversationSaveDTO {
    private String senderId;
    private String recipientId;
    private MessageDetail message;

    @Builder
    @EqualsAndHashCode
    @ToString
    @Getter
    public static class MessageDetail {
        private String content;
        private ContentType contentType;
    }
}
