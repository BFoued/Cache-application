package com.example.cache_application.controller;

import com.example.cache_application.dto.CacheRequest;
import com.example.cache_application.dto.CacheResponse;
import com.example.cache_application.service.CacheService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/cache")
public class CacheController {

    private final CacheService cacheService;

    public CacheController(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @PutMapping("/{key}")
    public ResponseEntity<CacheResponse> put(@PathVariable String key, @RequestBody CacheRequest request) {

        try {
            cacheService.put(key, request.getValue(), request.getTtl());
            CacheResponse response = new CacheResponse(true, "Valeur mise en cache avec succès");
            response.setKey(key);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            CacheResponse response = new CacheResponse(false, e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/{key}")
    public ResponseEntity<CacheResponse> get(@PathVariable String key) {
        Optional<String> value = cacheService.get(key);

        if (value.isPresent()) {
            CacheResponse response = new CacheResponse(key, value.get());
            return ResponseEntity.ok(response);
        } else {
            CacheResponse response = new CacheResponse(false, "Clé non trouvée ou expirée");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/{key}")
    public ResponseEntity<CacheResponse> delete(@PathVariable String key) {
        boolean deleted = cacheService.delete(key);

        if (deleted) {
            CacheResponse response = new CacheResponse(true, "Clé supprimée avec succès");
            return ResponseEntity.ok(response);
        } else {
            CacheResponse response = new CacheResponse(false, "Clé non trouvée");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

}

