package org.gt.chat.main.domain;

import lombok.*;

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

    @Getter
    public enum ContentType {
        APPLICATION_JSON("application/json"),
        TEXT_PLAIN_UTF8("text/plain-utf8"),
        APPLICATION_OCTET_STREAM("application/octet-stream");

        private String value;

        ContentType(String value) {
            this.value = value;
        }
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
