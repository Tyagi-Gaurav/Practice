package org.gt.chat.domain;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalTime;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Conversation {
    private String id;
    private long epochTimeStamp;
    private ConversationType conversationType;
    private String groupId;
    private String senderId;
    private String content;
}
