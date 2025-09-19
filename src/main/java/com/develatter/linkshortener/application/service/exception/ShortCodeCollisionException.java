package com.develatter.linkshortener.application.service.exception;

public class ShortCodeCollisionException extends RuntimeException {
    public ShortCodeCollisionException() {
        super("Shortcode couldn't be generated.");
    }
}

