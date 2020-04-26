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

