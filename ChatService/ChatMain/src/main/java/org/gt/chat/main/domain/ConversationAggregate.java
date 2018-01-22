package org.gt.chat.main.domain;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Builder
public class ConversationAggregate {
    private final List<ConversationEntity> messageEntityList;
}
