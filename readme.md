# API

# Overview

* Swagger [http://178.128.203.91/swagger-ui.html](http://178.128.203.91/swagger-ui.html)
* API specification [https://github.com/Qarantinno/api/wiki/API](https://github.com/Qarantinno/api/wiki/API)

# Setup

### Requirements
* linux / darwin
* docker
* docker-compose

### First time install
* copy `cp ./env-example ./.env`, update it
* copy `cp ./docker_env/env-example ./docker_env/.env` , update it
* `cd ./docker_env && docker-compoose up -d --build nginx postgres api`
* watch the log `docker-compose logs -f api`

### Start
* `cd ./docker_env && docker-compoose up -d nginx`

### Stop / Down
* `cd ./docker_env && docker-compoose down` or `cd ./docker_env && docker-compoose stop` (save containers)
