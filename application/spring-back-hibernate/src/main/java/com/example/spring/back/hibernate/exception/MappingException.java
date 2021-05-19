package com.example.spring.back.hibernate.exception;

public class MappingException extends RuntimeException{

    public MappingException(String message) {
        super("Error when trying to map " + message);
    }
}
