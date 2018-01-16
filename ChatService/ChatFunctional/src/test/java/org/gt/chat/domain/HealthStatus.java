package org.gt.chat.domain;

import lombok.*;

@Builder
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class HealthStatus {
    private String status;
}
