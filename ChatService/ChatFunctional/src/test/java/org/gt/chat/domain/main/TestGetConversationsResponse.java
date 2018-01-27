package org.gt.chat.domain.main;


import lombok.*;

import java.util.List;

@EqualsAndHashCode
@Getter
@AllArgsConstructor
@ToString
@Builder
public class TestGetConversationsResponse {
    private String globalRequestId;
    private String userId;
    private TestMessages messages;
    private TestMessageCategoryGroup group;

    @Builder
    @EqualsAndHashCode
    @ToString
    @Getter
    public static class TestMessageDetail {
        private String content;
        private TestContentType contentType;
        private boolean received;
        private long timestamp;
    }

    @Getter
    public enum TestContentType {
        APPLICATION_JSON("application/json"),
        TEXT_PLAIN_UTF8("text/plain-utf8"),
        APPLICATION_OCTET_STREAM("application/octet-stream");

        private String value;

        TestContentType(String value) {
            this.value = value;
        }
    }

    @Builder
    @EqualsAndHashCode
    @ToString
    @Getter
    public static class TestMessages {
        private String senderId;
        private List<TestMessageDetail> messageDetails;
    }

    @Builder
    @EqualsAndHashCode
    @ToString
    @Getter
    public static class TestMessageCategoryGroup {
        private String groupId;
        private List<TestMessageDetail> messages;
    }
}
