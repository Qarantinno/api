#!/bin/bash

echo "Starting the app"

#Dot env
if [[ ! -f /app/.env ]]; then
  echo ".env file not found! Starting failed!"
  exit 1
else
  set -a
  . "/app/.env"
  set +a
fi

cd /app &&
  ./gradlew clean build &&
  ./gradlew bootRun -Pargs=--db.url=jdbc:postgresql://${DATA_DB_HOST}:${DATA_DB_PORT}/${DATA_DB_NAME},--db.username=${DATA_DB_USER},--db.password=${DATA_DB_PASSWORD},--db.max-pool-size=10,--auth.client-token=${DEFAULT_CLIENT_ID},--port=${APP_SERVICE_PORT}
