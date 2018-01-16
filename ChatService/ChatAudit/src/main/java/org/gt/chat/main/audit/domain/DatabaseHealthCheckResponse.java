package org.gt.chat.main.audit.domain;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class DatabaseHealthCheckResponse {
    private String result;
}
