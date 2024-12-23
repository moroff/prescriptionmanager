#! /bin/bash
set -e

# Netwerk starten
docker network create pmNet

# Docker mit postgres starten ...
docker run --net pmNet --name pmDB --volume "$PWD"/:/opt/pm/ --volume $(pwd)/datadir:/var/lib/postgresql/data -p 5432:5432 -e POSTGRES_PASSWORD=password1 -d postgres

# .. Warten bis Datenbank gestartet ist, dann lokale Datenbank pm anlegen
until PGPASSWORD=$POSTGRES_PASSWORD docker exec -it pmDB psql -U "postgres" -c '\q'; do
  >&2 echo "Postgres is unavailable - sleeping"
  sleep 1
done
>&2 echo "Postgres is up - executing command"
docker exec -it pmDB psql -U postgres -c 'create database pm;'
