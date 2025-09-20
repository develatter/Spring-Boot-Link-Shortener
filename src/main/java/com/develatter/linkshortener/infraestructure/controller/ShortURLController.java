package com.develatter.linkshortener.infraestructure.controller;

import com.develatter.linkshortener.application.port.in.ResolveShortURLUseCase;
import com.develatter.linkshortener.application.port.in.ShortenURLUseCase;
import com.develatter.linkshortener.domain.model.ShortURL;
import com.develatter.linkshortener.infraestructure.controller.dto.ShortURLRequest;
import com.develatter.linkshortener.infraestructure.controller.dto.ShortURLResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

/**
 * REST controller for handling URL shortening and redirection.
 */
@RestController
public class ShortURLController {

    private final ShortenURLUseCase shortenURLUseCase;
    private final ResolveShortURLUseCase resolveShortURLUseCase;

    /**
     * Constructor for ShortURLController.
     * @param shortenURLUseCase use case for shortening URLs
     * @param resolveShortURLUseCase use case for resolving short URLs
     */
    public ShortURLController(ShortenURLUseCase shortenURLUseCase, ResolveShortURLUseCase resolveShortURLUseCase) {
        this.shortenURLUseCase = shortenURLUseCase;
        this.resolveShortURLUseCase = resolveShortURLUseCase;
    }


    /**
     * Endpoint to create a shortened URL.
     * @param request the request body containing the long URL and optional custom alias and expiration
     * @return the response entity with the created short URL details
     */
    @PostMapping("/shorten")
    public ResponseEntity<ShortURLResponse> shorten(
            @Valid
            @RequestBody
            ShortURLRequest request
    ) {
        final ShortURL shortURL = new ShortURL(
                null,
               null,
                request.longURL(),
                request.customAlias(),
                true,
                OffsetDateTime.now(),
                request.expiresAt(),
                0L
        );
        final ShortURL createdShortURL = shortenURLUseCase.createShortURL(shortURL);
        final ShortURLResponse response = new ShortURLResponse(
                createdShortURL.shortCode(),
                createdShortURL.longUrl(),
                createdShortURL.customAlias(),
                createdShortURL.createdAt(),
                createdShortURL.expiresAt()
        );
        return ResponseEntity.ok(response);
    }


    /**
     * Endpoint to resolve a short URL using its short code.
     * @param code the short code of the URL
     * @return a redirect response to the original long URL
     */
    @GetMapping("/s/{code}")
    public ResponseEntity<String> resolveWithShortCode(@PathVariable  String code) {
        String longUrl = resolveShortURLUseCase.resolveWithCode(code);
        return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT)
                .header("Location", longUrl)
                .build();
    }

    /**
     * Endpoint to resolve a short URL using its custom alias.
     * @param customAlias the custom alias of the URL
     * @return a redirect response to the original long URL
     */
    @GetMapping("/a/{customAlias}")
    public ResponseEntity<String> resolveWithCustomAlias(@PathVariable String customAlias) {
        String longUrl = resolveShortURLUseCase.resolveWithAlias(customAlias);
        return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT)
                .header("Location", longUrl)
                .build();
    }

}
