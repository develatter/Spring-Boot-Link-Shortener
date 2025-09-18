package com.develatter.linkshortener.dto;

import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
public record ShortURLResponse (
    String shortCode,
    String customAlias,
    String longURL,
    OffsetDateTime createdAt,
    OffsetDateTime expiresAt
){}
