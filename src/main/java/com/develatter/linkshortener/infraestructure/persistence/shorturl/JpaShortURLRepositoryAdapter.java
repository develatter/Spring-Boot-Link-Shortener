package com.develatter.linkshortener.infraestructure.persistence.shorturl;

import com.develatter.linkshortener.application.port.out.ResolveShortURLPort;
import com.develatter.linkshortener.application.port.out.ShortenURLPort;
import com.develatter.linkshortener.application.service.exception.CustomAliasNotFoundException;
import com.develatter.linkshortener.application.service.exception.ShortCodeNotFoundException;
import com.develatter.linkshortener.domain.model.ShortURL;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaShortURLRepositoryAdapter implements ShortenURLPort, ResolveShortURLPort {
    private final SpringDataShortURLRepository repository;

    public JpaShortURLRepositoryAdapter(SpringDataShortURLRepository repository) {
        this.repository = repository;
    }

    @Override
    public ShortURL save(ShortURL url) {
        var entity = ShortURLEntity.builder()
                .longUrl(url.longUrl())
                .customAlias(url.customAlias())
                .isActive(url.isActive())
                .createdAt(url.createdAt())
                .expiresAt(url.expiresAt())
                .shortCode(url.shortCode())
                .build();
        final var savedShortURLEntity = repository.save(entity);

        return new ShortURL(
                savedShortURLEntity.getId(),
                savedShortURLEntity.getShortCode(),
                savedShortURLEntity.getLongUrl(),
                savedShortURLEntity.getCustomAlias(),
                savedShortURLEntity.isActive(),
                savedShortURLEntity.getCreatedAt(),
                savedShortURLEntity.getExpiresAt(),
                savedShortURLEntity.getClickCount()
        );
    }

    @Override
    public boolean existsByShortCode(String shortCode) {
        return repository.existsByShortCode(shortCode);
    }

    @Override
    public boolean existsByCustomAliasIgnoreCase(String customAlias) {
        return repository.existsByCustomAliasIgnoreCase(customAlias);
    }

    @Override
    public String findOriginalURLByShortCode(String shortCode) {
        return repository.findShortURLByShortCode(shortCode).orElseThrow(
                () -> new ShortCodeNotFoundException(shortCode)
        ).getLongUrl();
    }

    @Override
    public String findOriginalURLByCustomAliasIgnoreCase(String customAlias) {
        return repository.findShortURLByCustomAliasIgnoreCase(customAlias).orElseThrow(
                () -> new CustomAliasNotFoundException(customAlias)
        ).getLongUrl();
    }
}
