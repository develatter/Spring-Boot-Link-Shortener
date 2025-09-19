package com.develatter.linkshortener.domain.model;

import java.time.OffsetDateTime;

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