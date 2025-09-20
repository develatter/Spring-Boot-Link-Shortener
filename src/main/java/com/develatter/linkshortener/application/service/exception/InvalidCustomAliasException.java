package com.develatter.linkshortener.application.service.exception;

public class InvalidCustomAliasException extends RuntimeException {
    public InvalidCustomAliasException(String message) {
        super("Provided custom alias is invalid" + message);
    }
}
