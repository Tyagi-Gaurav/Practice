package org.gt.chat.domain;

import lombok.*;

import java.io.Serializable;

@Getter
@Builder
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class AuditEvent implements Serializable {
    static final long serialVersionUID = 1L;

    private String globalRequestId;
    private long eventPublishEpochTimeStamp;
    private AuditEventType auditEventType;
}
