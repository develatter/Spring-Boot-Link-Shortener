package com.develatter.linkshortener.infraestructure.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

/**
 * Cache configuration class for setting up Redis as the caching provider.
 * This configuration enables caching in the application and defines a cache manager
 * that uses Redis with a default time-to-live (TTL) of 1 day for cached entries.
 */
@EnableCaching
@Configuration
public class CacheConfig {

    /**
     * Creates and configures a RedisCacheManager bean.
     * @param connectionFactory the Redis connection factory
     * @return the configured RedisCacheManager
     */
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory){
        var config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofDays(1))
                .serializeValuesWith(
                        RedisSerializationContext
                                .SerializationPair
                                .fromSerializer(
                                        new GenericJackson2JsonRedisSerializer()
                                )
                );
        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .build();
    }
}
