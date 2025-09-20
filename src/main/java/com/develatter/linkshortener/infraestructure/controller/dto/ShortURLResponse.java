package com.develatter.linkshortener.infraestructure.controller.dto;

import lombok.Builder;

import java.time.OffsetDateTime;

/**
 * Response DTO for a shortened URL.
 * @param shortCode The generated short code for the URL.
 * @param longURL The original long URL.
 * @param customAlias The custom alias if provided.
 * @param createdAt The creation timestamp of the short URL.
 * @param expiresAt The expiration timestamp of the short URL, if any.
 */
@Builder
public record ShortURLResponse (
    String shortCode,
    String longURL,
    String customAlias,
    OffsetDateTime createdAt,
    OffsetDateTime expiresAt
){}
