package org.gt.chat.domain;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@EqualsAndHashCode
@Getter
@AllArgsConstructor
public class Conversations {
    private List<Conversation> conversationList;
}
