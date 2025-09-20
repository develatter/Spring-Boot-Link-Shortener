package com.develatter.linkshortener.infraestructure.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.hibernate.validator.constraints.URL;

import java.time.OffsetDateTime;

/**
 * Request DTO for creating a short URL.
 * @param longURL The original long URL to be shortened.
 * @param customAlias An optional custom alias for the short URL.
 * @param expiresAt An optional expiration date for the short URL.
 */
@Builder
public record ShortURLRequest (
    @NotBlank @Size(max = 2048) @URL(regexp = "^(?i)(http|https)://.*$")
    String longURL,

    @Pattern(regexp = "^[A-Za-z0-9_-]{3,50}$", message = "invalid alias")
    String customAlias,

    OffsetDateTime expiresAt
){}

