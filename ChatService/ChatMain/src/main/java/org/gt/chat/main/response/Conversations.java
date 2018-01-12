package org.gt.chat.main.response;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@EqualsAndHashCode
@Getter
@AllArgsConstructor
@ToString
public class Conversations {
    private String globalRequestId;
    private final List<Conversation> conversationList;
}
