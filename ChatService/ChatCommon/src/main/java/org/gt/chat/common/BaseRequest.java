package org.gt.chat.common;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
@Builder
public class BaseRequest {
    protected String requestId;
}
