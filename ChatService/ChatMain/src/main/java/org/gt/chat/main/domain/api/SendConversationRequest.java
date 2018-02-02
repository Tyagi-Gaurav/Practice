package org.gt.chat.main.domain.api;

import lombok.*;
import org.gt.chat.main.domain.ContentType;

@EqualsAndHashCode
@Getter
@AllArgsConstructor
@ToString
@Builder
public class SendConversationRequest {
    private String senderId;
    private String recipientUserId;
    private SendMessageDetail messageDetail;

    @Builder
    @EqualsAndHashCode
    @ToString
    @Getter
    public static class SendMessageDetail {
        private String content;
        private ContentType contentType;
    }
}
