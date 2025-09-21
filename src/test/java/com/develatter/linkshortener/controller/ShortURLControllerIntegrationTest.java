package com.develatter.linkshortener.controller;

import com.develatter.linkshortener.LinkshortenerApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = LinkshortenerApplication.class)
@AutoConfigureMockMvc
class ShortURLControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("POST /shorten - must shorten a URL successfully")
    void shortenUrl_success() throws Exception {
        String requestBody = "{\"longURL\":\"https://www.google.com\"}";
        mockMvc.perform(post("/shorten")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shortCode").exists())
                .andExpect(jsonPath("$.longURL").value("https://www.google.com"));
    }

    @Test
    @DisplayName("POST /shorten - must fail when custom alias already exists")
    void shortenUrl_customAliasExists() throws Exception {
        String alias = "alias-test-" + System.currentTimeMillis();
        String requestBody = String.format("{\"longURL\":\"https://www.google.com\",\"customAlias\":\"%s\"}", alias);
        // must succeed
        mockMvc.perform(post("/shorten")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk());
        // must fail
        mockMvc.perform(post("/shorten")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value(org.hamcrest.Matchers.containsString("already taken")));
    }

    @Test
    @DisplayName("GET /s/{shortCode} - should redirect to the original URL using shortCode")
    void redirectWithShortCode_success() throws Exception {
        String requestBody = "{\"longURL\":\"https://www.wikipedia.org\"}";
        String response = mockMvc.perform(post("/shorten")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String shortCode = response.replaceAll(".*\"shortCode\":\"(.*?)\".*", "$1");
        mockMvc.perform(get("/s/" + shortCode))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("https://www.wikipedia.org"));
    }

    @Test
    @DisplayName("GET /a/{customAlias} - should redirect to the original URL using customAlias")
    void redirectWithCustomAlias_success() throws Exception {
        String alias = "alias-redirect-" + System.currentTimeMillis();
        String requestBody = String.format("{\"longURL\":\"https://www.github.com\",\"customAlias\":\"%s\"}", alias);
        mockMvc.perform(post("/shorten")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk());
        mockMvc.perform(get("/a/" + alias))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("https://www.github.com"));
    }

    @Test
    @DisplayName("GET /s/{shortCode} - should return 404 if the short code does not exist")
    void redirectWithShortCode_notFound() throws Exception {
        mockMvc.perform(get("/s/NOEXISTE"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    @DisplayName("GET /a/{customAlias} - should return 404 if the custom alias does not exist")
    void redirectWithCustomAlias_notFound() throws Exception {
        mockMvc.perform(get("/a/NOEXISTEALIAS"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").exists());
    }
}
