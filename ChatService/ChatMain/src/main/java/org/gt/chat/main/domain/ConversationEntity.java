package org.gt.chat.main.domain;

import lombok.*;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
@Builder
public class ConversationEntity {
    private String senderId;
    private String recipientId;
    private boolean received;
    private String content;
    private ContentTypeEntity contentType;
    private long timestamp;

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
}
