package com.develatter.linkshortener.application.port.out;

import com.develatter.linkshortener.domain.model.ShortURL;

/**
 * Port for saving and checking existence of ShortURL entities.
 */
public interface ShortenURLPort {


    /**
     * Saves a ShortURL entity.
     * @param url the ShortURL entity to save
     * @return the saved ShortURL entity
     */
    ShortURL save(ShortURL url);

}
