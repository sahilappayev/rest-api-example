package com.example.rest.error;

public class CustomNotFoundException extends RuntimeException {

    public CustomNotFoundException(Class clazz, Object id) {
        super("Could not find id " + id + " for " + clazz.getSimpleName());
    }

    public CustomNotFoundException(String message) {
        super(message);
    }
}
