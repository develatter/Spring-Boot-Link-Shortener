package com.develatter.linkshortener.application.service;

import com.develatter.linkshortener.application.port.in.ShortenURLUseCase;
import com.develatter.linkshortener.application.port.out.ShortenURLPort;
import com.develatter.linkshortener.application.service.exception.CustomAliasAlreadyExistsException;
import com.develatter.linkshortener.application.service.exception.ShortCodeCollisionException;
import com.develatter.linkshortener.domain.model.ShortURL;
import com.develatter.linkshortener.domain.service.ShortCodeGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ShortURLService implements ShortenURLUseCase {

    private final ShortenURLPort shortenURLPort;


    @Autowired
    public ShortURLService(ShortenURLPort shortenURLPort) {
        this.shortenURLPort = shortenURLPort;
    }




    @Override
    public ShortURL createShortURL(ShortURL url) {
        final int MAX_RETRIES = 5;
        if (url.customAlias() != null && !url.customAlias().isBlank()) {
            if (shortenURLPort.existsByCustomAliasIgnoreCase(url.customAlias())) {
                throw new CustomAliasAlreadyExistsException(url.customAlias());
            }
            if (url.shortCode() != null && shortenURLPort.existsByShortCode(url.shortCode())) {
                throw new ShortCodeCollisionException();
            }
            return shortenURLPort.save(url);
        } else {
            String shortCode = url.shortCode();
            int attempts = 0;
            do {
                if (shortCode == null || shortenURLPort.existsByShortCode(shortCode)) {
                    shortCode = ShortCodeGeneratorService.genShortCode();
                    attempts++;
                } else {
                    break;
                }
            } while (attempts < MAX_RETRIES);
            if (shortenURLPort.existsByShortCode(shortCode)) {
                throw new ShortCodeCollisionException();
            }
            ShortURL urlWithCode = new ShortURL(
                url.id(),
                shortCode,
                url.longUrl(),
                null,
                url.isActive(),
                url.createdAt(),
                url.expiresAt(),
                url.clickCount()
            );
            return shortenURLPort.save(urlWithCode);
        }
    }
}
