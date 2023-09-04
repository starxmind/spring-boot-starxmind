package com.starxmind.boot.response.exceptions;

/**
 * 参数非法
 *
 * @author pizzalord
 * @since 1.0
 */
public class IllegalRequestException extends RuntimeException{
    private static final long serialVersionUID = -1455046147356188500L;

    public IllegalRequestException() {
    }

    public IllegalRequestException(String message) {
        super(message);
    }

    public IllegalRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalRequestException(Throwable cause) {
        super(cause);
    }

    public IllegalRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
