package com.example.cache_application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CacheServiceTest {

    private CacheService cacheService;

    @BeforeEach
    void setUp() {
        cacheService = new CacheService();
        cacheService.clear();
    }

    @Test
    void putAndGetShouldReturnStoredValue() {
        String key = "key-1";
        String value = "value-1";
        long ttl = 10000;

        cacheService.put(key, value, ttl);
        Optional<String> result = cacheService.get(key);

        assertTrue(result.isPresent());
        assertEquals(value, result.get());
    }

    @Test
    void ttlShouldExpireEntry() throws InterruptedException {
        String key = "expiring-key-1";
        String value = "expiring-value-1";
        long ttl = 1;

        cacheService.put(key, value, ttl);
        Thread.sleep(1100);
        Optional<String> result = cacheService.get(key);

        assertFalse(result.isPresent());
    }

}