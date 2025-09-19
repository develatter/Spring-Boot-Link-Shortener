# LinkShortener

A personal URL shortener built with Spring Boot, PostgreSQL, and Redis.

## Features
- Shortens long URLs and generates unique short codes.
- Supports custom aliases for links.
- Persistence with PostgreSQL and caching with Redis.
- Database migrations managed with Flyway.

## Main Endpoints
- `POST /shorten` — Shorten a URL.
- `GET /s/{shortCode}` — Redirects to the original URL based on short code.
- `GET /a/{customAlias}` — Redirects to the original URL based on custom alias.

## License
MIT License.
