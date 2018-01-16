package org.gt.chat.main.audit.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Builder
@ToString
@EqualsAndHashCode
@Getter
public class HealthCheckRequest implements Serializable {
}
