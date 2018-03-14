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
    private List<TestMessageDetail> messageDetails;

    @Builder
    @EqualsAndHashCode
    @ToString
    @Getter
    public static class TestMessageDetail {
        private String senderId;
        private String recipientId;
        private boolean received;
        private long timestamp;
        private String content;
        private TestMessageContentType contentType;
    }
}
