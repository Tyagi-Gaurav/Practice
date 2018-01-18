package org.gt.chat.domain.main;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class HealthStatus {
    private String name;
    private String result;

    List<HealthStatus> dependencies;
}
