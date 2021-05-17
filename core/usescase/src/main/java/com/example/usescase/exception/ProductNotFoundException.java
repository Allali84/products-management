package com.example.usescase.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Integer id) {
        super("The product with id : "+id+" not found");
    }
}
