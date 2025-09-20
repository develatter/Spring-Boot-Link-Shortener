package com.develatter.linkshortener.application.service.exception;

/**
 * Exception thrown when a short code collision occurs and a unique short code cannot be generated.
 */
public class ShortCodeCollisionException extends RuntimeException {

    /**
     * Constructs a new exception with a default message indicating that a short code couldn't be generated.
     */
    public ShortCodeCollisionException() {
        super("Short code couldn't be generated.");
    }
}

