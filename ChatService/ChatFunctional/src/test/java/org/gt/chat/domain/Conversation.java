package org.gt.chat.domain;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Conversation {
    private String id;
    private LocalTime timestamp;
    private ConversationType conversationType;
    private String groupId;
    private String senderId;
    private String content;
}
