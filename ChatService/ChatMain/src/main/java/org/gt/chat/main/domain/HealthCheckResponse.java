package org.gt.chat.main.domain;

import lombok.*;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@ToString(of = {"result"})
@EqualsAndHashCode
public class HealthCheckResponse {
    private String name;
    private String result;

    private List<HealthCheckResponse> dependencies;
}

