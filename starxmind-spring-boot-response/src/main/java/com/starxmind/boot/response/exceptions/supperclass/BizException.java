package com.starxmind.boot.response.exceptions.supperclass;

/**
 * 业务异常
 *
 * @author pizzalord
 * @since 1.0
 */
public class BizException extends RuntimeException {
    private static final long serialVersionUID = 1621475675911223541L;

    public BizException() {
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(Throwable cause) {
        super(cause);
    }

    public BizException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
