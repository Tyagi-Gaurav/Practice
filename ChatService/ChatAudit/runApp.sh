#!/bin/sh

./provisionApp.sh
ERR=`docker rm -f chat_audit`
container=`docker run --shm-size=1024m -d -p 9090:25520 -p 9090:25520/udp --env-file /Users/gauravt/workspace/Practice/ChatService/ChatEnv/src/main/resources/chatlocal.conf --net=chatservice_webnet --name chat_audit chat_audit:master`
docker logs -f $container
