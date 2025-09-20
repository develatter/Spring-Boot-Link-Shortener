package com.develatter.linkshortener.domain.model;

import java.time.OffsetDateTime;

/**
 * Domain model representing a shortened URL.
 *
 * @param id          the unique identifier of the shortened URL
 * @param shortCode   the generated short code for the URL
 * @param longUrl     the original long URL
 * @param customAlias an optional custom alias for the short URL
 * @param isActive    indicates if the short URL is active
 * @param createdAt   the timestamp when the short URL was created
 * @param expiresAt   the timestamp when the short URL will expire
 * @param clickCount  the number of times the short URL has been accessed
 */
public record ShortURL(
        Long id,
        String shortCode,
        String longUrl,
        String customAlias,
        boolean isActive,
        OffsetDateTime createdAt,
        OffsetDateTime expiresAt,
        long clickCount
) {
}