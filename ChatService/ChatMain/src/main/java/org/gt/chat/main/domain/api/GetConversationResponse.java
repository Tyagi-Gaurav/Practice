package org.gt.chat.main.domain.api;

import lombok.*;
import org.gt.chat.main.domain.ContentType;

import java.util.List;

@EqualsAndHashCode
@Getter
@AllArgsConstructor
@ToString
@Builder
public class GetConversationResponse {
    private String globalRequestId;
    private List<MessageDetail> messageDetails;

    @Builder
    @EqualsAndHashCode
    @ToString
    @Getter
    public static class MessageDetail {
        private String senderId;
        private String recipientId;
        private boolean received;
        private String content;
        private ContentType contentType;
        private long timestamp;
    }
}
