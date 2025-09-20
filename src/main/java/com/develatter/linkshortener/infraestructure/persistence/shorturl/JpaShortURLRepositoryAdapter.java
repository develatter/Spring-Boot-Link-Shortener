package com.develatter.linkshortener.infraestructure.persistence.shorturl;

import com.develatter.linkshortener.application.port.out.ResolveShortURLPort;
import com.develatter.linkshortener.application.port.out.ShortenURLPort;
import com.develatter.linkshortener.application.service.exception.CustomAliasNotFoundException;
import com.develatter.linkshortener.application.service.exception.ShortCodeNotFoundException;
import com.develatter.linkshortener.domain.model.ShortURL;
import jakarta.transaction.Transactional;
import org.hibernate.annotations.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;


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

    @Transactional
    @Override
    @Cacheable(value = "shortUrls", key = "#shortCode")
    public String findOriginalURLByShortCode(String shortCode) {
        var entity = repository.findShortURLByShortCode(shortCode).orElseThrow(
                () -> new ShortCodeNotFoundException(shortCode)
        );
        entity.setClickCount(entity.getClickCount() + 1);
        return repository.save(entity).getLongUrl();
    }

    @Transactional
    @Override
    @Cacheable(value = "shortUrls", key = "#customAlias")
    public String findOriginalURLByCustomAliasIgnoreCase(String customAlias) {
        var entity = repository.findShortURLByCustomAliasIgnoreCase(customAlias).orElseThrow(
                () -> new CustomAliasNotFoundException(customAlias)
        );
        entity.setClickCount(entity.getClickCount() + 1);
        return repository.save(entity).getLongUrl();
    }
}
