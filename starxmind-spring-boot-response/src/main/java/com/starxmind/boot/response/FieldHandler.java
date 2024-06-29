package com.starxmind.boot.response;

public interface FieldHandler<T> {
    Object handle(T value);
}
