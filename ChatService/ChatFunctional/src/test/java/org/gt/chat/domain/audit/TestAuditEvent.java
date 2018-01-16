package org.gt.chat.domain.audit;

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
