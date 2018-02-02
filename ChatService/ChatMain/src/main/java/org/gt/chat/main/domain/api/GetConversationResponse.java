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
    private String userId;
    private Messages messages;
    private MessageCategoryGroup group;

    @Builder
    @EqualsAndHashCode
    @ToString
    @Getter
    public static class MessageDetail {
        private String content;
        private ContentType contentType;
        private boolean received;
        private long timestamp;
    }

    @Builder
    @EqualsAndHashCode
    @ToString
    @Getter
    public static class Messages {
        private String senderId;
        private List<MessageDetail> messageDetails;
    }

    @Builder
    @EqualsAndHashCode
    @ToString
    @Getter
    public static class MessageCategoryGroup {
        private String groupId;
        private List<MessageDetail> messages;
    }
}
