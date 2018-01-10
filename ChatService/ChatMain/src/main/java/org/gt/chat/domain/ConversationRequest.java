package org.gt.chat.domain;

import lombok.*;

@Builder
@ToString
@EqualsAndHashCode
@Getter
@AllArgsConstructor
public class ConversationRequest {
    private String globalRequestId;
    private String userId;
}
