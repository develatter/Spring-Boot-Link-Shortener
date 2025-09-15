CREATE EXTENSION IF NOT EXISTS citext;

CREATE TABLE IF NOT EXISTS short_url (
                                         id              BIGSERIAL PRIMARY KEY,
                                         short_code      VARCHAR(12) NOT NULL UNIQUE,
                                         long_url        TEXT NOT NULL CHECK (char_length(long_url) <= 2048),
                                         custom_alias    CITEXT UNIQUE,
                                         is_active       BOOLEAN NOT NULL DEFAULT TRUE,
                                         created_at      TIMESTAMPTZ NOT NULL DEFAULT now(),
                                         expires_at      TIMESTAMPTZ,
                                         click_count     BIGINT NOT NULL DEFAULT 0
);

CREATE INDEX IF NOT EXISTS idx_short_url_active ON short_url (is_active);
CREATE INDEX IF NOT EXISTS idx_short_url_expires ON short_url (expires_at) WHERE expires_at IS NOT NULL;
