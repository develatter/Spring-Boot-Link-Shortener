package com.develatter.linkshortener.domain.service;

/**
 * Service for generating short codes for URLs.
 */
public class ShortCodeGeneratorService {

    /**
     * Generates a unique short code based on the current timestamp.
     * The generated code consists of 8 characters chosen from a set of
     * lowercase letters, uppercase letters, and digits.
     *
     * @return a unique short code
     */
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
