package org.gt.chat.domain.main;

import lombok.*;
import org.gt.chat.domain.HealthStatus;

@Builder
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class HealthCheck {
    private HealthStatus mainApplication;
    private HealthStatus auditServer;
}
