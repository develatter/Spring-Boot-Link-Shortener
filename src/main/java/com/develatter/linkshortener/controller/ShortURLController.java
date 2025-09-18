package com.develatter.linkshortener.controller;

import com.develatter.linkshortener.dto.ShortURLRequest;
import com.develatter.linkshortener.dto.ShortURLResponse;
import com.develatter.linkshortener.service.ShortURLService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shorten")
public class ShortURLController {

    private final ShortURLService shortURLService;

    @Autowired
    public ShortURLController (ShortURLService shortURLService) {
        this.shortURLService = shortURLService;
    }


    @PostMapping()
    public ResponseEntity<ShortURLResponse> shorten(
            @Valid
            @RequestBody
            ShortURLRequest request
    ) {
        return ResponseEntity.ok(shortURLService.createShortURL(request));
    }
}
