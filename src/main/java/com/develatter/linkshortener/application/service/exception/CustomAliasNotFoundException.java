package com.develatter.linkshortener.application.service.exception;

/**
 * Exception thrown when a requested custom alias is not found in the system.
 */
public class CustomAliasNotFoundException extends RuntimeException {

    /**
     * Constructs a new exception with a message indicating the alias that was not found.
     * @param message the custom alias that was not found
     */
    public CustomAliasNotFoundException(String message) {
        super("Custom alias not found: " + message);
    }
}
