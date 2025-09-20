package com.develatter.linkshortener.application.service.exception;

/**
 * Exception thrown when a requested short code is not found in the system.
 */
public class ShortCodeNotFoundException extends RuntimeException {

    /**
     * Constructs a new exception with a message indicating the short code that was not found.
     * @param message the short code that was not found
     */
    public ShortCodeNotFoundException(String message) {
        super("Short code could not be found: " + message);
    }
}
