package com.develatter.linkshortener.application.port.in;

import com.develatter.linkshortener.domain.model.ShortURL;

/**
 * Use case for creating shortened URLs.
 */
public interface ShortenURLUseCase {

    /**
     * Creates a shortened URL.
     *
     * @param url the URL details to shorten
     * @return the created ShortURL object
     */
    ShortURL createShortURL(ShortURL url);
}
