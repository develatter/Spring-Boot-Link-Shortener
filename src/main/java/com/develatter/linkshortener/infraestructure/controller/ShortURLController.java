package com.develatter.linkshortener.infraestructure.controller;

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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;

@RestController
@RequestMapping("/api/shorten")
public class ShortURLController {

    private final ShortenURLUseCase shortenURLUseCase;

    public ShortURLController(ShortenURLUseCase shortenURLUseCase) {
        this.shortenURLUseCase = shortenURLUseCase;
    }

    @PostMapping()
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
}
