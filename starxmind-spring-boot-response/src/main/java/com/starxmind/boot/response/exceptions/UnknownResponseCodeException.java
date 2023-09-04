package com.starxmind.boot.response.exceptions;

/**
 * 未知返回代码异常
 */
public class UnknownResponseCodeException extends RuntimeException {

    private static final long serialVersionUID = -5006604996581757017L;

    public UnknownResponseCodeException() {
    }

    public UnknownResponseCodeException(String message) {
        super(message);
    }

    public UnknownResponseCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownResponseCodeException(Throwable cause) {
        super(cause);
    }

    public UnknownResponseCodeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
