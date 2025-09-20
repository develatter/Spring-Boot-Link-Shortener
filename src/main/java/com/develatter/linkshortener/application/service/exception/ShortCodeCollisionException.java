package com.develatter.linkshortener.application.service.exception;

public class ShortCodeCollisionException extends RuntimeException {
    public ShortCodeCollisionException() {
        super("Short code couldn't be generated.");
    }
}

