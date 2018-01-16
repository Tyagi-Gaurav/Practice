package org.gt.chat.main.audit.domain;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class HealthCheckResponse {
    private String result;
}
