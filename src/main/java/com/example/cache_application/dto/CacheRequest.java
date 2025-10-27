package com.example.cache_application.dto;

public class CacheRequest {
    private String value;
    private Long ttl;

    public CacheRequest() {
    }

    public CacheRequest(String value, Long ttl) {
        this.value = value;
        this.ttl = ttl;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getTtl() {
        return ttl != null ? ttl : 300L;
    }

    public void setTtl(Long ttl) {
        this.ttl = ttl;
    }
}