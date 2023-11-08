package com.starxmind.boot.response.exceptions;

import com.starxmind.boot.response.exceptions.supperclass.CustomerException;

/**
 * 访问受限异常
 */
public class ForbiddenException extends CustomerException {

    private static final long serialVersionUID = -3954810951845756670L;

    public ForbiddenException() {
    }

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    public ForbiddenException(Throwable cause) {
        super(cause);
    }

    public ForbiddenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
