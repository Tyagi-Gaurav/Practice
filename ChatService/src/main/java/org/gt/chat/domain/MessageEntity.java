package org.gt.chat.domain;

public class MessageEntity {
    private final long messageId;
    private final String content;
    private final long receivedTimeStamp;

    public MessageEntity(long messageId,
                         String content,
                         long receivedTimeStamp) {

        this.messageId = messageId;
        this.content = content;
        this.receivedTimeStamp = receivedTimeStamp;
    }

    public long getMessageId() {
        return messageId;
    }

    public String getContent() {
        return content;
    }

    public long getReceivedTimeStamp() {
        return receivedTimeStamp;
    }
}
