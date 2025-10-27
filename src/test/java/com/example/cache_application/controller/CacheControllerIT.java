package com.example.cache_application.controller;

import com.example.cache_application.dto.CacheRequest;
import com.example.cache_application.service.CacheService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class CacheControllerIT {

    private static final String BASE_URL = "/api/cache/";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        cacheService.clear();
    }

    @Test
    void shouldStoreAndRetrieveValueSuccessfully() throws Exception {
        String key = "key-1";
        CacheRequest request = new CacheRequest("value-1", 300L);

        mockMvc.perform(put(BASE_URL + key)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.key").value(key));

        mockMvc.perform(get(BASE_URL + key))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.key").value(key))
                .andExpect(jsonPath("$.value").value("value-1"));
    }

    @Test
    void shouldReturnNotFoundWhenValueIsExpired() throws Exception {
        String key = "key-2";
        CacheRequest request = new CacheRequest("value-2", 1L);

        mockMvc.perform(put(BASE_URL + key)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        Thread.sleep(1100);

        mockMvc.perform(get(BASE_URL + key))
                .andExpect(status().isNotFound());
    }

}
