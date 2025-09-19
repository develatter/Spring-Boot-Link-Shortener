package com.develatter.linkshortener.infraestructure.controller.dto;

import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
public record ShortURLResponse (
    String shortCode,
    String longURL,
    String customAlias,
    OffsetDateTime createdAt,
    OffsetDateTime expiresAt
){}
