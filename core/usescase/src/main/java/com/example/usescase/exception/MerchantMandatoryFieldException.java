package com.example.usescase.exception;

public class MerchantMandatoryFieldException extends RuntimeException {
    public MerchantMandatoryFieldException(String field) {
        super("The field "+field+" of Merchant Entity is mandatory");
    }
}
