package com.develatter.linkshortener.application.service.exception;

/**
 * Exception thrown when a provided custom alias is invalid.
 */
public class InvalidCustomAliasException extends RuntimeException {

    /**
     * Constructs a new exception with a message indicating the reason for invalidity.
     * @param message the reason why the custom alias is invalid
     */
    public InvalidCustomAliasException(String message) {
        super("Provided custom alias is invalid" + message);
    }
}
