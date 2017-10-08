package org.gt.pc.movie;

public class TechnicalFailureException extends RuntimeException {
    public TechnicalFailureException(String message) {
        super(message);
    }

    public TechnicalFailureException(String message, Throwable cause) {
        super(message, cause);
    }

    public TechnicalFailureException(Throwable cause) {
        super(cause);
    }

    public TechnicalFailureException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
