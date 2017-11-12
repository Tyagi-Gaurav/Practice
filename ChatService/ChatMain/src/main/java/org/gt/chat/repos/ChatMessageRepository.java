package org.gt.chat.repos;

import org.gt.chat.domain.MessageAggregate;
import org.gt.chat.domain.MessageEntity;

import java.util.ArrayList;
import java.util.List;

public class ChatMessageRepository implements MessageRepository {

    @Override
    public MessageAggregate getMessages(String userId) {
        MessageEntity messageEntity = new MessageEntity(
                2L,
                "Hello World",
                234878234L
        );
        List<MessageEntity> entityList = new ArrayList<>();
        entityList.add(messageEntity);
        MessageAggregate aggregate = new MessageAggregate(entityList);
        return aggregate;
    }
}
