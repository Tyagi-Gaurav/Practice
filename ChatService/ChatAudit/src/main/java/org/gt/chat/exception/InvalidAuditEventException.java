package org.gt.chat.exception;

public class InvalidAuditEventException extends RuntimeException {
    public InvalidAuditEventException(String message) {
        super(message);
    }

    public InvalidAuditEventException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidAuditEventException(Throwable cause) {
        super(cause);
    }
}
