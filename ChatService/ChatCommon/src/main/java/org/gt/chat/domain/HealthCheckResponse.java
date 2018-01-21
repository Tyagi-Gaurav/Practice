package org.gt.chat.domain;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@ToString(of = {"result"})
@EqualsAndHashCode
public class HealthCheckResponse implements Serializable {
    private String name;
    private String result;

    private List<HealthCheckResponse> dependencies;
}

