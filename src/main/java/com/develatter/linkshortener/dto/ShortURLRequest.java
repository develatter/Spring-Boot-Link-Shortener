package com.develatter.linkshortener.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.hibernate.validator.constraints.URL;

import java.time.OffsetDateTime;

@Builder
public record ShortURLRequest (
    @NotBlank @Size(max = 2048) @URL(regexp = "^(?i)(http|https)://.*$")
    String longURL,

    @Pattern(regexp = "^[A-Za-z0-9_-]{3,50}$", message = "invalid alias")
    String customAlias,

    OffsetDateTime expiresAt
){}

