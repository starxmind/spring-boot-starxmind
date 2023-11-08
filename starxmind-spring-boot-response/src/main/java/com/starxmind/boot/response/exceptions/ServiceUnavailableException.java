package com.starxmind.boot.response.exceptions;

import com.starxmind.boot.response.exceptions.supperclass.ServerException;

/**
 * 服务不可用异常
 *
 * @author pizzalord
 * @since 1.0
 */
public class ServiceUnavailableException extends ServerException {

    private static final long serialVersionUID = -752880527769346944L;

    public ServiceUnavailableException() {
    }

    public ServiceUnavailableException(String message) {
        super(message);
    }

    public ServiceUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceUnavailableException(Throwable cause) {
        super(cause);
    }

    public ServiceUnavailableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
