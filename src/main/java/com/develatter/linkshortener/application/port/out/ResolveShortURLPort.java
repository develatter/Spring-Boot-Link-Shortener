package com.develatter.linkshortener.application.port.out;

public interface ResolveShortURLPort {
    String findOriginalURLByShortCode(String shortCode);
    String findOriginalURLByCustomAliasIgnoreCase(String customAlias);
}
