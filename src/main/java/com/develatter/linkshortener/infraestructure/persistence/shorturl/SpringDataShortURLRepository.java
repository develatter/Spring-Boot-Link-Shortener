package com.develatter.linkshortener.infraestructure.persistence.shorturl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for ShortURLEntity.
 * Provides methods to perform CRUD operations and custom queries on ShortURLEntity.
 */
@Repository
public interface SpringDataShortURLRepository extends JpaRepository<ShortURLEntity, Long> {

    /**
     * Finds a ShortURLEntity by its ID.
     * @param id the ID of the ShortURLEntity
     * @return an Optional containing the found ShortURLEntity, or empty if not found
     */
    Optional<ShortURLEntity> findShortURLById(Long id);

    /**
     * Finds a ShortURLEntity by its short code.
     * @param shortCode the short code of the ShortURLEntity
     * @return an Optional containing the found ShortURLEntity, or empty if not found
     */
    Optional<ShortURLEntity> findShortURLByShortCode(String shortCode);

    /**
     * Finds a ShortURLEntity by its custom alias, ignoring case.
     * @param customAlias the custom alias of the ShortURLEntity
     * @return an Optional containing the found ShortURLEntity, or empty if not found
     */
    Optional<ShortURLEntity> findShortURLByCustomAliasIgnoreCase(String customAlias);

    /**
     * Finds a ShortURLEntity by its active status.
     * @param isActive the active status of the ShortURLEntity
     * @return an Optional containing the found ShortURLEntity, or empty if not found
     */
    Optional<ShortURLEntity> findShortURLByIsActive (boolean isActive);

    /**
     * Checks if a ShortURLEntity exists by its short code.
     * @param shortCode the short code to check
     * @return true if a ShortURLEntity with the given short code exists, otherwise false
     */
    boolean existsByShortCode(String shortCode);

    /**
     * Checks if a ShortURLEntity exists by its custom alias, ignoring case.
     * @param customAlias the custom alias to check
     * @return true if a ShortURLEntity with the given custom alias exists, otherwise false
     */
    boolean existsByCustomAliasIgnoreCase(String customAlias);
}
