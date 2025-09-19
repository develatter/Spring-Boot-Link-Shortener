package com.develatter.linkshortener.infraestructure.controller;

import com.develatter.linkshortener.application.port.in.ResolveShortURLUseCase;
import com.develatter.linkshortener.application.port.in.ShortenURLUseCase;
import com.develatter.linkshortener.domain.model.ShortURL;
import com.develatter.linkshortener.domain.service.ShortCodeGeneratorService;
import com.develatter.linkshortener.infraestructure.controller.dto.ShortURLRequest;
import com.develatter.linkshortener.infraestructure.controller.dto.ShortURLResponse;
import com.develatter.linkshortener.application.service.exception.CustomAliasAlreadyExistsException;
import com.develatter.linkshortener.application.service.exception.ShortCodeCollisionException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

@CrossOrigin(origins = "*")
@RestController
public class ShortURLController {

    private final ShortenURLUseCase shortenURLUseCase;
    private final ResolveShortURLUseCase resolveShortURLUseCase;

    public ShortURLController(ShortenURLUseCase shortenURLUseCase, ResolveShortURLUseCase resolveShortURLUseCase) {
        this.shortenURLUseCase = shortenURLUseCase;
        this.resolveShortURLUseCase = resolveShortURLUseCase;
    }


    @PostMapping("/shorten")
    public ResponseEntity<ShortURLResponse> shorten(
            @Valid
            @RequestBody
            ShortURLRequest request
    ) {
        final ShortURL shortURL = new ShortURL(
                null,
                ShortCodeGeneratorService.genShortCode(),
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

    @ExceptionHandler(CustomAliasAlreadyExistsException.class)
    public ResponseEntity<String> handleCustomAliasExists(CustomAliasAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(ShortCodeCollisionException.class)
    public ResponseEntity<String> handleShortCodeCollision(ShortCodeCollisionException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }


    @GetMapping("/s/{code}")
    public ResponseEntity<String> resolveWithShortCode(@PathVariable  String code) {
        String longUrl = resolveShortURLUseCase.resolveWithCode(code);
        return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT)
                .header("Location", longUrl)
                .build();
    }

    @GetMapping("/a/{customAlias}")
    public ResponseEntity<String> resolveWithCustomAlias(@PathVariable String customAlias) {
        String longUrl = resolveShortURLUseCase.resolveWithAlias(customAlias);
        return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT)
                .header("Location", longUrl)
                .build();
    }
}
