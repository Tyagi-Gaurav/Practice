package org.gt.chat.main.audit.domain;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@EqualsAndHashCode
@Getter
@AllArgsConstructor
@ToString
public class TestConversations {
    private String globalRequestId;
    private List<TestConversation> conversationList;
}
