package com.starxmind.boot.response.exceptions;

import com.starxmind.boot.response.exceptions.supperclass.ServerException;

/**
 * 内部服务异常
 */
public class InternalServerException extends ServerException {
    private static final long serialVersionUID = 7088543331135890817L;

    public InternalServerException() {
    }

    public InternalServerException(String message) {
        super(message);
    }

    public InternalServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public InternalServerException(Throwable cause) {
        super(cause);
    }

    public InternalServerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
