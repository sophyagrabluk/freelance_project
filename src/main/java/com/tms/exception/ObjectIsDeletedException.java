package com.tms.exception;

public class ObjectIsDeletedException extends RuntimeException{
    public ObjectIsDeletedException(String message) {
        super(message);
    }
}