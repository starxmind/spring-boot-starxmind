package com.starxmind.boot.response.exceptions.supperclass;

/**
 * 用户输入异常
 *
 * @author pizzalord
 * @since 1.0
 */
public class CustomerException extends RuntimeException {
    private static final long serialVersionUID = -3462451914024338974L;

    public CustomerException() {
    }

    public CustomerException(String message) {
        super(message);
    }

    public CustomerException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomerException(Throwable cause) {
        super(cause);
    }

    public CustomerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
