package org.gt.chat.main.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class ConversationAggregate {
    private final List<ConversationEntity> messageEntityList;
}
