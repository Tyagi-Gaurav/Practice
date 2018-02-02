package org.gt.chat.domain.main;

import lombok.*;

@EqualsAndHashCode
@Getter
@AllArgsConstructor
@ToString
@Builder
public class TestSendConversationRequest {
    private String senderId;
    private String recipientUserId;
    private TestMessageDetail messageDetail;

    @Builder
    @EqualsAndHashCode
    @ToString
    @Getter
    public static class TestMessageDetail {
        private String content;
        private TestMessageContentType contentType;
    }
}
