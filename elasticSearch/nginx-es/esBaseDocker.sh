#!/bin/sh

eval $(docker-machine env dev)

docker images | grep -i "es-base-image" | awk '{print $3}' | xargs docker rmi --force

# Clean up old images.
docker images | grep -i "<none" | awk '{print $3}' | xargs docker rmi --force

# Create Image
docker build --force-rm --no-cache -f ./BaseDockerFile -t es-base-image:latest .

