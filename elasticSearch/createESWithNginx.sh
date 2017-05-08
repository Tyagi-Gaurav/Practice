#!/bin/sh

docker-machine start dev

eval $(docker-machine env dev)

TAG="ubuntu-es-with-nginx-image"

docker images | grep -i $TAG | awk '{print $3}' | xargs docker rmi --force

# Clean up old images & dead containers.
docker ps -a | grep -i "Exited" | awk '{print $1}' | xargs docker rm --force
docker ps -a | grep -i "Created" | awk '{print $1}' | xargs docker rm --force
docker images | grep -i "<none" | awk '{print $3}' | xargs docker rmi --force

# Create Image
docker build --force-rm --no-cache -f ./DockerFile -t $TAG:latest .

docker run -p 8080:80 -p 9443:443 -it ubuntu-es-with-nginx-image:latest /bin/bash



