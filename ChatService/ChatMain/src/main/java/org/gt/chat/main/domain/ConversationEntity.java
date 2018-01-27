package org.gt.chat.main.domain;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
@Builder
public class ConversationEntity {
    private String userId;
    private Messages messages;

    @Builder
    @EqualsAndHashCode
    @ToString
    @Getter
    public static class MessageDetailEntity {
        private String content;
        private ContentTypeEntity contentType;
        private boolean received;
        private long timestamp;
    }

    @Getter
    public enum ContentTypeEntity {
        APPLICATION_JSON("application/json"),
        TEXT_PLAIN_UTF8("text/plain-utf8"),
        APPLICATION_OCTET_STREAM("application/octet-stream");

        private String value;

        ContentTypeEntity(String value) {
            this.value = value;
        }
    }

    @Builder
    @EqualsAndHashCode
    @ToString
    @Getter
    public static class Messages {
        private String senderId;
        private List<MessageDetailEntity> messageDetails;
    }
}
