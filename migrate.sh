#!/bin/bash
set -a
source .env
set +a

mvn flyway:migrate \
  -Dflyway.url=jdbc:postgresql://localhost:${POSTGRES_PORT}/${POSTGRES_DB} \
  -Dflyway.user=${POSTGRES_USER} \
  -Dflyway.password=${POSTGRES_PASSWORD}