package com.develatter.linkshortener.application.port.in;

public interface ResolveShortURLUseCase {
    String resolveWithCode(String code);
    String resolveWithAlias(String alias);
}
