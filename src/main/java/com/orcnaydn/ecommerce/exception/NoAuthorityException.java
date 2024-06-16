package com.orcnaydn.ecommerce.exception;

public class NoAuthorityException extends RuntimeException {

    public NoAuthorityException(String message) {
        super(message);
    }
}