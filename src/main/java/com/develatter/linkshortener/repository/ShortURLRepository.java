package com.develatter.linkshortener.repository;

import com.develatter.linkshortener.model.ShortURL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShortURLRepository extends JpaRepository<ShortURL, Long> {
    Optional<ShortURL> findShortURLById(Long id);
    Optional<ShortURL> findShortURLByShortCode(String shortCode);
    Optional<ShortURL> findShortURLByCustomAliasIgnoreCase(String customAlias);
    Optional<ShortURL> findShortURLByIsActive (boolean isActive);
}
