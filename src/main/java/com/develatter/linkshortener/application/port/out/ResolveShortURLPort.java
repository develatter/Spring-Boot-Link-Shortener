package com.develatter.linkshortener.application.port.out;

import java.util.Optional;

public interface ResolveShortURLPort {
    String findOriginalURLByShortCode(String shortCode);
    String findOriginalURLByCustomAliasIgnoreCase(String customAlias);
}
