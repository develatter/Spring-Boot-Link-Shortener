package com.develatter.linkshortener.infraestructure.persistence.shorturl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataShortURLRepository extends JpaRepository<ShortURLEntity, Long> {
    Optional<ShortURLEntity> findShortURLById(Long id);
    Optional<ShortURLEntity> findShortURLByShortCode(String shortCode);
    Optional<ShortURLEntity> findShortURLByCustomAliasIgnoreCase(String customAlias);
    Optional<ShortURLEntity> findShortURLByIsActive (boolean isActive);
    boolean existsByShortCode(String shortCode);
    boolean existsByCustomAliasIgnoreCase(String customAlias);
}
