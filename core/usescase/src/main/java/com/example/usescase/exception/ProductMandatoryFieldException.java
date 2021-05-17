package com.example.usescase.exception;

public class ProductMandatoryFieldException extends RuntimeException {
    public ProductMandatoryFieldException(String field) {
        super("The field "+field+" of Product Entity is mandatory");
    }
}
