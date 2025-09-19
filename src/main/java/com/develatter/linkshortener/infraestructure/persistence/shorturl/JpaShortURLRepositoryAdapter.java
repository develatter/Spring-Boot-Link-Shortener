package com.develatter.linkshortener.infraestructure.persistence.shorturl;

import com.develatter.linkshortener.application.port.out.ShortenURLPort;
import com.develatter.linkshortener.domain.model.ShortURL;
import org.springframework.stereotype.Repository;

@Repository
public class JpaShortURLRepositoryAdapter implements ShortenURLPort {
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
}
