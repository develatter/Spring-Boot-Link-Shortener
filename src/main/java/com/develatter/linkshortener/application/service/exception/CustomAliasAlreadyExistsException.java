package com.develatter.linkshortener.application.service.exception;

/**
 * Exception thrown when a provided custom alias already exists in the system.
 */
public class CustomAliasAlreadyExistsException extends RuntimeException {

    /**
     * Constructs a new exception with a message indicating the alias that already exists.
     * @param alias the custom alias that is already taken
     */
    public CustomAliasAlreadyExistsException(String alias) {
        super("Provided alias is already taken: " + alias);
    }
}

