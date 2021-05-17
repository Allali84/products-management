package com.example.spring.boot.exception;

public class MappingException extends RuntimeException{

    public MappingException(String message) {
        super("Error when trying to map " + message);
    }
}
