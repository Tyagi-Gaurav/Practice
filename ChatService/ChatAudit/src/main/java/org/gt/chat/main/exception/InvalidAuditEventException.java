package org.gt.chat.main.exception;

import java.io.Serializable;

public class InvalidAuditEventException extends RuntimeException implements Serializable {
    public InvalidAuditEventException(String message) {
        super(message);
    }
}
