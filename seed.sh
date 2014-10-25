docker run \
  --detach \
  --name postgres \
  --publish 127.0.0.1:5432:5432 \
  stackbrew/postgres:latest 

psql \
  -U postgres \
  -h localhost \
  -f seed.sql

