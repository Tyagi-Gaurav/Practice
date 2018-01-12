package org.gt.chat.main.audit.exception;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class ErrorResponse {
    private String code;
    private String description;
}
