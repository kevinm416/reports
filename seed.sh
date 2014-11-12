# create and run a container named postgres
docker run \
  --detach \
  --name postgres \
  --publish 127.0.0.1:5432:5432 \
  stackbrew/postgres:9.3 

# open sql shell in container
docker exec \
  --tty \
  --interactive \
  bash

psql \
  -U postgres \
  -h localhost \
  -f seed.sql

