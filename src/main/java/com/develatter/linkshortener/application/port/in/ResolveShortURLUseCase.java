package com.develatter.linkshortener.application.port.in;

/**
 * Use case for resolving short URLs to their original URLs.
 */
public interface ResolveShortURLUseCase {
    /**
     * Resolves the original URL using the provided short code.
     *
     * @param code the short code to resolve
     * @return the original URL if found, otherwise null
     */
    String resolveWithCode(String code);

    /**
     * Resolves the original URL using the provided custom alias.
     *
     * @param alias the custom alias to resolve
     * @return the original URL if found, otherwise null
     */
    String resolveWithAlias(String alias);
}
