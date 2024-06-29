package com.starxmind.boot.response.anno;

public interface FieldAdvice<T> {
    void handle(T result);
}
