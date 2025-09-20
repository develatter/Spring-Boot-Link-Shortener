package com.develatter.linkshortener.application.service;

import com.develatter.linkshortener.application.port.in.ResolveShortURLUseCase;
import com.develatter.linkshortener.application.port.in.ShortenURLUseCase;
import com.develatter.linkshortener.application.port.out.ResolveShortURLPort;
import com.develatter.linkshortener.application.port.out.ShortenURLPort;
import com.develatter.linkshortener.application.service.exception.*;
import com.develatter.linkshortener.domain.model.ShortURL;
import com.develatter.linkshortener.domain.service.ShortCodeGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ShortURLService implements ShortenURLUseCase, ResolveShortURLUseCase {

    private final ShortenURLPort shortenURLPort;
    private final ResolveShortURLPort resolveShortURLPort;


    @Autowired
    public ShortURLService(ShortenURLPort shortenURLPort, ResolveShortURLPort resolveShortURLPort) {
        this.resolveShortURLPort = resolveShortURLPort;
        this.shortenURLPort = shortenURLPort;
    }


    @Override
    public ShortURL createShortURL(ShortURL url) {
        final int MAX_RETRIES = 5;

        if (!(url.customAlias() == null || url.customAlias().isBlank())
                && shortenURLPort.existsByCustomAliasIgnoreCase(url.customAlias())
        ) {
            throw new CustomAliasAlreadyExistsException(url.customAlias());
        }


        String shortCode = generateUniqueShortCode(MAX_RETRIES);

        ShortURL urlWithCode = new ShortURL(
                url.id(),
                shortCode,
                url.longUrl(),
                url.customAlias(),
                url.isActive(),
                url.createdAt(),
                url.expiresAt(),
                url.clickCount()
        );

        return shortenURLPort.save(urlWithCode);
    }


    private String generateUniqueShortCode(int maxRetries) {
        for (int attempts = 0; attempts < maxRetries; attempts++) {
            String shortCode = ShortCodeGeneratorService.genShortCode();
            if (!shortenURLPort.existsByShortCode(shortCode)) {
                return shortCode;
            }
        }
        throw new ShortCodeCollisionException();
    }

    @Override
    public String resolveWithCode(String code) {
        return resolveShortURLPort.findOriginalURLByShortCode(code);
    }

    @Override
    public String resolveWithAlias(String alias) {
        return resolveShortURLPort.findOriginalURLByCustomAliasIgnoreCase(alias);
    }
}
