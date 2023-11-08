package com.starxmind.boot.response.exceptions.supperclass;

/**
 * 服务异常
 *
 * @author pizzalord
 * @since 1.0
 */
public class ServerException extends RuntimeException {
    private static final long serialVersionUID = 2978129817298875766L;

    public ServerException() {
    }

    public ServerException(String message) {
        super(message);
    }

    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerException(Throwable cause) {
        super(cause);
    }

    public ServerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
