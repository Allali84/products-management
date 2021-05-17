package com.example.usescase.exception;

public class ProductLabelNotFoundException extends RuntimeException {
    public ProductLabelNotFoundException(String label) {
        super("The product with label : "+label+" not found");
    }
}
