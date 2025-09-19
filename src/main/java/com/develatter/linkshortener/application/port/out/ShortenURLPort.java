package com.develatter.linkshortener.application.port.out;

import com.develatter.linkshortener.domain.model.ShortURL;

public interface ShortenURLPort {
    ShortURL save(ShortURL url);
    boolean existsByShortCode(String shortCode);
    boolean existsByCustomAliasIgnoreCase(String customAlias);
}
