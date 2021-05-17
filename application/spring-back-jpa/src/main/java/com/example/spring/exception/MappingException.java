package com.example.spring.exception;

public class MappingException extends RuntimeException{

    public MappingException(String message) {
        super("Error when trying to map " + message);
    }
}
