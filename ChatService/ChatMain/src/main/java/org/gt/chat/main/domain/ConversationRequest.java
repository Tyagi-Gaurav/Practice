package org.gt.chat.main.domain;

import lombok.*;

import java.io.Serializable;

@Builder
@ToString
@EqualsAndHashCode
@Getter
@AllArgsConstructor
public class ConversationRequest implements Serializable {
    private String globalRequestId;
    private String userId;
}
