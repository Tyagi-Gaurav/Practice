package org.gt.chat.repos;

import org.gt.chat.domain.ConversationAggregate;
import org.gt.chat.domain.ConversationEntity;

import java.util.ArrayList;
import java.util.List;

public class ChatMessageRepository implements MessageRepository {

    @Override
    public ConversationAggregate getMessages(String userId) {
        ConversationEntity messageEntity = new ConversationEntity(
                2L,
                "Hello World",
                234878234L,
                "groupId",
                "senderId"
        );
        List<ConversationEntity> entityList = new ArrayList<>();
        entityList.add(messageEntity);
        ConversationAggregate aggregate = new ConversationAggregate(entityList);
        return aggregate;
    }
}
