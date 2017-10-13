package org.gt.chat.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class Messages {
    private final List<Message> messageResponseList;

    @JsonCreator
    public Messages(@JsonProperty("messageResponses") List<Message> messageResponseList) {
        this.messageResponseList = messageResponseList;
    }

    public List<Message> getMessageResponseList() {
        return messageResponseList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Messages messages = (Messages) o;
        return Objects.equals(messageResponseList, messages.messageResponseList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageResponseList);
    }

    @Override
    public String toString() {
        return "Messages{" +
                "messageResponseList=" + messageResponseList +
                '}';
    }
}
