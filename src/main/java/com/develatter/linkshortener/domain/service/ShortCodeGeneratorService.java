package com.develatter.linkshortener.domain.service;

import java.util.Objects;

public class ShortCodeGeneratorService {

    public static String genShortCode() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder();
        long value = System.currentTimeMillis();

        for (int i = 0; i < 8; i++) {
            int index = (int) (value % chars.length());
            code.append(chars.charAt(index));
            value /= chars.length();
        }
        return code.toString();
    }
}
