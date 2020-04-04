#! /bin/bash
set -e
#
# Docker mit postgres starten ...
docker run --name pmDB --volume "$PWD"/:/opt/pm/ --publish 5432:5432 -e POSTGRES_PASSWORD=password1 -d postgres

# .. Warten bis Datenbank gestartet ist, dann lokale Datenbank pm anlegen
until PGPASSWORD=$POSTGRES_PASSWORD docker exec -it pmDB psql -U "postgres" -c '\q'; do
  >&2 echo "Postgres is unavailable - sleeping"
  sleep 1
done
>&2 echo "Postgres is up - executing command"
docker exec -it pmDB psql -U postgres -c 'create database pm;'

# Zustand von Heroku herunterladen und lokal einspielen
heroku pg:backups:download
docker exec -it pmDB pg_restore --verbose --clean --no-acl --no-owner -h localhost -U postgres -d pm /opt/pm/latest.dump