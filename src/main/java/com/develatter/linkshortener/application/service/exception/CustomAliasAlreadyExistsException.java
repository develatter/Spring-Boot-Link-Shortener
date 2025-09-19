package com.develatter.linkshortener.application.service.exception;

public class CustomAliasAlreadyExistsException extends RuntimeException {
    public CustomAliasAlreadyExistsException(String alias) {
        super("Provided alias is already taken: " + alias);
    }
}

