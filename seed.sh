# create and run a container named postgres
docker run \
  --detach \
  --name reports-postgres \
  --publish 127.0.0.1:5432:5432 \
  stackbrew/postgres:9.3 

# open shell in container
docker exec \
  --tty \
  --interactive \
  reports-postgres \
  bash

# open sql shell from container shell
psql \
  -U postgres \
  -h localhost \
  -f seed.sql

# create admin user w/ password password
insert into users (name, admin, deleted, pw_hash, salt) 
values ('admin', TRUE, FALSE, 'T1U8KXcwPmcn5OZob8FpccztBqLMZmmnPZ84lCz0yu8=', '');

# create some houses
insert into houses (name) values ('house1'), ('house2'), ('house3');
