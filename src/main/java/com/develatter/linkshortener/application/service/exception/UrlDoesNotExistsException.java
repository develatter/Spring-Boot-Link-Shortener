package com.develatter.linkshortener.application.service.exception;

/**
 * Exception thrown when a requested URL does not exist in the system.
 */
public class UrlDoesNotExistsException extends RuntimeException {

    /**
     * Constructs a new exception with a message indicating the URL that does not exist.
     * @param message the URL that does not exist
     */
    public UrlDoesNotExistsException(String message) {
        super("The URL does not exists: " + message);
    }
}
