#!/bin/sh

./provisionApp.sh
ERR=`docker rm -f chat_audit`
container=`docker run --shm-size=1024m -d -p 9090:25520 --env-file /Users/gauravt/workspace/Practice/ChatService/ChatEnv/src/main/resources/chatlocal.conf --name chat_audit chat_audit:master`
docker logs -f $container
