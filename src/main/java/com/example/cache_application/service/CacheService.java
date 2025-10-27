package com.example.cache_application.service;

import com.example.cache_application.model.CacheEntry;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CacheService {

    private final Map<String, CacheEntry> cache = new ConcurrentHashMap<>();

    /**
     * Inserts value into the cache with specified TTL
     *
     * @param key        the key of the stored value
     * @param value      the value to cache
     * @param ttlSeconds the time-to-live in seconds
     */
    public void put(String key, String value, long ttlSeconds) {

        if (key == null || key.trim().isEmpty()) {
            throw new IllegalArgumentException("La clé ne peut pas être vide");
        }
        if (ttlSeconds <= 0) {
            throw new IllegalArgumentException("Le TTL doit être positif");
        }

        CacheEntry entry = new CacheEntry(value, Instant.now().plusSeconds(ttlSeconds));
        cache.put(key, entry);
    }


    /**
     * Retrieves the cached value associated with the given key
     *
     * @param key the key associated with the value to retrieve
     * @return the cached value if present and valid
     */
    public Optional<String> get(String key) {
        CacheEntry entry = cache.get(key);

        if (entry == null) {
            return Optional.empty();
        }

        if (entry.isExpired()) {
            cache.remove(key);
            return Optional.empty();
        }

        return Optional.of(entry.value());
    }

    /**
     * Delete the cached value associated with the given key
     *
     * @param key
     * @return true if an entry was removed; false if no entry found for the key
     */
    public boolean delete(String key) {
        return cache.remove(key) != null;
    }

    /**
     * Clear the cache
     */
    public void clear() {
        cache.clear();
    }

}

