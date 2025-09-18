package com.develatter.linkshortener.service;

import com.develatter.linkshortener.dto.ShortURLRequest;
import com.develatter.linkshortener.dto.ShortURLResponse;
import com.develatter.linkshortener.model.ShortURL;
import com.develatter.linkshortener.repository.ShortURLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.OffsetDateTime;

@Service
public class ShortURLService {

    private final ShortURLRepository repository;

    @Autowired
    public ShortURLService(ShortURLRepository repository) {
        this.repository = repository;
    }

    public ShortURLResponse createShortURL(ShortURLRequest request) {
        var entity = ShortURL.builder()
                .longUrl(request.longURL())
                .customAlias(request.customAlias())
                .isActive(true)
                .createdAt(OffsetDateTime.now())
                .expiresAt(request.expiresAt())
                .build();
        ShortURL savedEntity;

        int attempts = 0;

        while (true) {
            String code = randomCode(Instant.now().toEpochMilli());
            entity.setShortCode(code);
            try {
                savedEntity = repository.saveAndFlush(entity);
                break;
            } catch (DataIntegrityViolationException e) {
                if (++attempts > 5) throw e;
            }
        }
        //TODO: map entity to response
        return ShortURLResponse.builder()
                .shortCode(savedEntity.getShortCode())
                .customAlias(savedEntity.getCustomAlias())
                .longURL(savedEntity.getLongUrl())
                .createdAt(savedEntity.getCreatedAt())
                .expiresAt(savedEntity.getExpiresAt())
                .build();
    }

    private String randomCode(Long id) {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder();
        long value = id + System.currentTimeMillis();
        for (int i = 0; i < 8; i++) {
            int index = (int) (value % chars.length());
            code.append(chars.charAt(index));
            value /= chars.length();
        }
        return code.toString();
    }

}
