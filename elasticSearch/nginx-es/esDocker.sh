#!/bin/sh

eval $(docker-machine env dev)

# Clean up old images.
docker images | grep -i "es-image" | awk '{print $3}' | xargs docker rmi --force 2>/dev/null
docker images | grep -i "es-nginx-image" | awk '{print $3}' | xargs docker rmi --force 2>/dev/null
docker ps -a | grep -i "nginx-server" | awk '{print $3}' | xargs docker rm -f 2>/dev/null
docker ps -a | grep -i "es-server" | awk '{print $3}' | xargs docker rm -f 2>/dev/null

# Create Image
docker build --force-rm --no-cache -f ./NginxDockerFile -t es-nginx-image:latest .
#docker build --force-rm --no-cache -f ./ElasticSearchDockerFile -t es-image:latest .

# Clean up old images.
docker images | grep -i "<none" | awk '{print $3}' | xargs docker rmi --force 2>/dev/null

#docker rm -f es-server && docker run -d -p 9800:9200 -p 9900:9300 --name=es-server es-image:latest
#docker inspect <es-container>
#docker run -d -p 8080:80 -p 9443:443 --name=nginx-server --link es-server es-nginx-image:latest
#docker run -d -p 8080:80 -p 9443:443 --name=nginx-server es-nginx-image:latest

#curl -i -vvvv -XPUT 192.168.99.100:8080/test-index -d '
#{
#    "settings" : {
#        "index" : {
#            "number_of_shards" : 1,
#            "number_of_replicas" : 1
#        }
#    }
#}
#'
#
#curl -i -vvvv -XPUT 192.168.99.100:8080/cat/indices