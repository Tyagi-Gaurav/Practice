package org.gt.chat.domain;

import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class AuditEvent {
    private String requestId;
    private long eventPublishEpochTimeStamp;
    private AuditEventType auditEventType;
}
