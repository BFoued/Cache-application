package com.example.cache_application.model;

import java.time.Instant;

public record CacheEntry(String value, Instant expirationTime) {

    public boolean isExpired() {
        return Instant.now().isAfter(expirationTime);
    }
}