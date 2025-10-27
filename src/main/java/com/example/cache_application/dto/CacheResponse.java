package com.example.cache_application.dto;

public class CacheResponse {
    private String key;
    private String value;
    private String message;
    private boolean success;

    public CacheResponse() {
    }

    public CacheResponse(String key, String value) {
        this.key = key;
        this.value = value;
        this.success = true;
    }

    public CacheResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}