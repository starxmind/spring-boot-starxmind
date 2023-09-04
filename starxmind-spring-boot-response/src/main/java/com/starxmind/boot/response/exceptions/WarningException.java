package com.starxmind.boot.response.exceptions;

/**
 * 告警
 */
public class WarningException extends RuntimeException {

    private static final long serialVersionUID = -2516618330171029835L;

    public WarningException() {
    }

    public WarningException(String message) {
        super(message);
    }

    public WarningException(String message, Throwable cause) {
        super(message, cause);
    }

    public WarningException(Throwable cause) {
        super(cause);
    }

    public WarningException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
