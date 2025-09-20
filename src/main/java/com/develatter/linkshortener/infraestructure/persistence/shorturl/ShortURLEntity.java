package com.develatter.linkshortener.infraestructure.persistence.shorturl;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

/**
 * Entity representing a shortened URL.
 * This entity is mapped to the "short_url" table in the database.
 */
@Entity
@Table(
        name = "short_url",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_short_code", columnNames = "short_code"),
                @UniqueConstraint(name = "uk_custom_alias", columnNames = "custom_alias")
        }
)
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShortURLEntity {
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "short_code", nullable = false, length = 12)
        private String shortCode;

        @Column(name = "long_url", nullable = false, columnDefinition = "text")
        private String longUrl;

        @Column(name = "custom_alias", columnDefinition = "citext")
        private String customAlias;

        @Column(name =  "is_active", nullable = false)
        private boolean isActive = true;

        @Column(name = "created_at", nullable = false)
        private OffsetDateTime createdAt = OffsetDateTime.now();

        @Column(name = "expires_at")
        private OffsetDateTime expiresAt;

        @Column(name = "click_count")
        private long clickCount = 0L;
}