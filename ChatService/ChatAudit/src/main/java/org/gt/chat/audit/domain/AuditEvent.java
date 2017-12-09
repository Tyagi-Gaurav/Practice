package org.gt.chat.audit.domain;

import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class AuditEvent {
    private long eventPublishEpochTimeStamp;
    private AuditEventType auditEventType;
}
