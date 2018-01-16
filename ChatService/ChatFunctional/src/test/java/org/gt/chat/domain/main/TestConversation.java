package org.gt.chat.domain.main;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class TestConversation {
    private String id;
    private long epochTimeStamp;
    private TestConversationType conversationType;
    private String groupId;
    private String senderId;
    private String content;
}
