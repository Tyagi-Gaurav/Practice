package org.gt.chat.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.gt.chat.domain.MessageEntity;

import java.util.Objects;

public class Message {
    private final String id;
    private final String content;
    private final Long timestamp;

    @JsonCreator
    public Message(@JsonProperty("id") String id,
                   @JsonProperty("content") String content,
                   @JsonProperty("timestamp") Long timestamp) {

        this.id = id;
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public static Message from(MessageEntity messageEntity) {
        return new Message(
            String.valueOf(messageEntity.getMessageId()),
                           messageEntity.getContent(),
                           messageEntity.getReceivedTimeStamp());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(id, message.id) &&
                Objects.equals(content, message.content) &&
                Objects.equals(timestamp, message.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, timestamp);
    }
}
