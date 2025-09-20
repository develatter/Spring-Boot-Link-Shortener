package com.develatter.linkshortener.application.service.exception;

public class UrlDoesNotExistsException extends RuntimeException {
    public UrlDoesNotExistsException(String message) {
        super("The URL does not exists: " + message);
    }
}
