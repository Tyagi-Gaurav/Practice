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
        private TestMessageContentType contentType;
        private boolean received;
        private long timestamp;
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
