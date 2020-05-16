#!/usr/bin/env bash

SCRIPT_PATH="$( cd "$(dirname "$0")" || exit 1 ; pwd -P )"
cd "${SCRIPT_PATH}" || exit 1

#parse config liines note
#${DOMAINS_BECOME_SSL%%;*}
#${DOMAINS_BECOME_SSL//;/  -d }

MAIN_DOMAIN_SSL=api.outcrowd.by
DOMAINS_LINE=api.outcrowd.by
LETSENCRYPT_EMAIL=ssl_0@outcrowd.by

exists=$(certbot certificates | grep "${MAIN_DOMAIN_SSL}")
if [[ -z $exists ]]; then
    echo "There are no certs for domain ${MAIN_DOMAIN_SSL}, emit new"
    certbot certonly  --webroot --preferred-challenges http \
        -w "${SCRIPT_PATH}/webroot/letsencrypt" \
        -d ${DOMAINS_LINE} --agree-tos \
        --email "${LETSENCRYPT_EMAIL}" \
        --non-interactive --text
# use --staging for testing
else
    echo "The certs was found, no need to emit"
fi

