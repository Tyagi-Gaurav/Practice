package org.gt.pc.exception;

public class UnSupportedParentalControlLevelException extends RuntimeException {
    public UnSupportedParentalControlLevelException() {
    }

    public UnSupportedParentalControlLevelException(String message) {
        super(message);
    }

    public UnSupportedParentalControlLevelException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnSupportedParentalControlLevelException(Throwable cause) {
        super(cause);
    }

    public UnSupportedParentalControlLevelException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
