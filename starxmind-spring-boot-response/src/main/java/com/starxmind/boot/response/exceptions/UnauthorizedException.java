package com.starxmind.boot.response.exceptions;

import com.starxmind.boot.response.exceptions.supperclass.CustomerException;

/**
 * 未授权异常
 */
public class UnauthorizedException extends CustomerException {

    private static final long serialVersionUID = 1692568899966933211L;

    public UnauthorizedException() {
    }

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedException(Throwable cause) {
        super(cause);
    }

    public UnauthorizedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
