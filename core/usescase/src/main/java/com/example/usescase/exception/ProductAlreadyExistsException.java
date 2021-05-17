package com.example.usescase.exception;

public class ProductAlreadyExistsException extends RuntimeException {
    public ProductAlreadyExistsException(Integer id) {
        super("The product with id : "+id+" already exist");
    }
}
