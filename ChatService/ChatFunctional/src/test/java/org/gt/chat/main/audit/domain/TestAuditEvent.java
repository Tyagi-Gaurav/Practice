package org.gt.chat.main.audit.domain;

import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class TestAuditEvent {
    private String requestId;
    private TestAuditEventType auditEventType;
}
