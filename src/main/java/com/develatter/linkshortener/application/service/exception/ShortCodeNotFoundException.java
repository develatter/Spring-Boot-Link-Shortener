package com.develatter.linkshortener.application.service.exception;

public class ShortCodeNotFoundException extends RuntimeException {
    public ShortCodeNotFoundException(String message) {
        super("Short code could not be found: " + message);
    }
}
