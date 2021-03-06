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
    private String content;
    private ContentType contentType;
}
