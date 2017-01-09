#!/bin/sh

docker-machine start dev
eval $(docker-machine env dev)
docker run --name mysql_container -e MYSQL_ROOT_PASSWORD=my-secret-pw -d mysql/mysql-server:5.7

# Connecting to mysql container
# docker exec -it mysql_container mysql -uroot -p
