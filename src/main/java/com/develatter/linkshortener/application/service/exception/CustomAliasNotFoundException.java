package com.develatter.linkshortener.application.service.exception;

public class CustomAliasNotFoundException extends RuntimeException {
    public CustomAliasNotFoundException(String message) {
        super("Custom alias not found: " + message);
    }
}
