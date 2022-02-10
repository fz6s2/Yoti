package com.yoti.test.exception;

public class MapperException extends RuntimeException{
    public MapperException(String message, Throwable ex) {
        super(message, ex);
    }
}
