#!/bin/sh

docker rm -f  `docker ps -a | grep -i "Exited" | awk '{print $1}'`
docker rmi -f  `docker images -a | grep -i "<none>" | awk '{print $3}'`
