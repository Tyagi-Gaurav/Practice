#!/bin/sh

export WORDPRESS_DB_PASSWORD=wordpress_pwd
DB_PASSWORD=my-secret-pw

docker-machine start dev
eval $(docker-machine env dev)

docker stop mysql_container 2> /dev/null
docker rm mysql_container 2> /dev/null
docker stop wordpress-container 2> /dev/null
docker rm wordpress-container 2> /dev/null

docker run --name mysql_container -e MYSQL_ROOT_PASSWORD=$DB_PASSWORD -d mysql/mysql-server:5.7

# Connecting to mysql container
# docker exec -it mysql_container mysql -uroot -p

docker run --name wordpress-container --link mysql_container:mysql_container -p 9091:80 -e WORDPRESS_DB_PASSWORD=$DB_PASSWORD -d wordpress
