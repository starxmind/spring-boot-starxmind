package com.starxmind.boot.pageable.exceptions;

/**
 * 页大小过大异常
 *
 * @author pizzalord
 * @since 1.0
 */
public class PageSizeTooLargeException extends RuntimeException {
    public PageSizeTooLargeException() {
        super("页大小过大");
    }
}
