package com.example.rest.error;

public class UnAuthenticationException extends RuntimeException {


    public UnAuthenticationException(String message) {
        super(message);
    }
}
