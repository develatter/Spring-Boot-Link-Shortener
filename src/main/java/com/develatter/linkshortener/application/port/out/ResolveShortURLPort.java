package com.develatter.linkshortener.application.port.out;


/**
 * Port for resolving short URLs to their original URLs.
 */
public interface ResolveShortURLPort {

    /**
     * Finds the original URL by its short code.
     * @param shortCode the short code to look up
     * @return the original URL if found, otherwise null
     */
    String findOriginalURLByShortCode(String shortCode);

    /**
     * Finds the original URL by its custom alias, ignoring case.
     * @param customAlias the custom alias to look up
     * @return the original URL if found, otherwise null
     */
    String findOriginalURLByCustomAliasIgnoreCase(String customAlias);

    /**
     * Checks if a short code exists.
     * @param shortCode the short code to check
     * @return true if the short code exists, otherwise false
     */
    boolean existsByShortCode(String shortCode);

    /**
     * Checks if a custom alias exists, ignoring case.
     * @param customAlias the custom alias to check
     * @return true if the custom alias exists, otherwise false
     */
    boolean existsByCustomAliasIgnoreCase(String customAlias);
}
