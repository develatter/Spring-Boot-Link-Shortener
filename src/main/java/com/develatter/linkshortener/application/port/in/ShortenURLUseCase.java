package com.develatter.linkshortener.application.port.in;

import com.develatter.linkshortener.domain.model.ShortURL;

public interface ShortenURLUseCase {
    ShortURL createShortURL(ShortURL url);
}
