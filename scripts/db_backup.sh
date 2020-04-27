#!/bin/bash

SCRIPT_PATH="$( cd "$(dirname "$0")" ; pwd -P )"

#Dot env
if [[ ! -f "${SCRIPT_PATH}/../.env" ]]; then
    echo ".env file not found! Backup script has been failed";
    exit 1
else
  set -a
  . "${SCRIPT_PATH}/../.env"
  set +a
fi;

cd "${SCRIPT_PATH}/../docker_env" || exit 1

NOW_DATE_TAG=`date +%Y-%m-%d-%H-%M-%S`
DUMP_CMD=$(docker-compose exec -T postgres which pg_dump)

FILE_NAME=${DATA_DB_NAME}.${NOW_DATE_TAG}.sql
BAK_PATH="${SCRIPT_PATH}/../backups/db"

mkdir -p ${BAK_PATH}

echo "+ Start dumping"

DUMPED=0
docker-compose exec -T postgres bash -c "PGPASSWORD=${DATA_DB_PASSWORD} ${DUMP_CMD} -U ${DATA_DB_USER} -F p -n qarantinno ${DATA_DB_NAME} > /${FILE_NAME}" \
    && DUMPED=1

if [[ ${DUMPED} -gt 0 ]]; then
    echo "+ Dumped in /${FILE_NAME} in postgres container"
else
    echo "x Can't dump"
    exit 1
fi;

COPIED_OUT=0
docker cp "$(docker-compose ps -q postgres)":/${FILE_NAME} "${BAK_PATH}/${FILE_NAME}" \
    && COPIED_OUT=1

if [[ ${COPIED_OUT} > 0 ]]; then
    echo "+ Extracted ${BAK_PATH}/${FILE_NAME}"
else
    echo "x Can't extract from the container"
    exit 1
fi;

echo "Clean up..."

#TODO: add checking of success
docker-compose exec -T postgres rm /${FILE_NAME}
find ${BAK_PATH} -type f -name \*${DB_NAME}\*.sql -mtime +30 -exec rm -f {} \;

echo "+ Done"
