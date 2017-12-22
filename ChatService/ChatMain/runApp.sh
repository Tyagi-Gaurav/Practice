#!/bin/sh

./provisionApp.sh
ERR=`docker rm -f chat_main`
container=`docker run --shm-size=1024m -d -p 8080:8080 --name chat_main chat_main:master`
docker logs -f $container
