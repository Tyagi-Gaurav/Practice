#!/bin/sh


WIREMOCK_DOCKER_REPO=chonku/ubuntu-wiremock

echo "Update VM"
yum update -y >/dev/null

echo "Install Docker"
yum install docker -y >/dev/null

echo "Start Docker Service"
service docker start

echo "Assign Permissions"
usermod -a -G docker ec2-user

#Stop old containers
docker stop pds && docker rm pds
docker stop pz && docker rm pz
docker stop hcom && docker rm hcom
docker stop eureka && docker rm eureka

# Clean up old containers
echo "********** Clean up old containers **********"
docker ps -a | grep -i Exited | awk '{print $1}' | xargs docker rm
docker ps -a | grep -i Created | awk '{print $1}' | xargs docker rm

# Clean up old images.
echo "********** Clean up old images ************"
docker images | grep -i "<none" | awk '{print $3}' | xargs docker rmi --force
docker images | grep -i "ubuntu-wiremock" | awk '{print $3}' | xargs docker rmi --force

# Login to Docker
docker login -u <docker_login> -p <docker_pwd>

# Create Image
cd /vagrant/docker
docker build --force-rm --no-cache -f /vagrant/docker/DockerFile -t $WIREMOCK_DOCKER_REPO:latest .
cd -

# Create Containers
docker run -d -p 8081:8080 --name pds $WIREMOCK_DOCKER_REPO:latest sh -c "java -jar /opt/wiremock/wiremock-standalone.jar --root-dir /opt/mappings/pds"
docker run -d -p 8082:8080 --name pz $WIREMOCK_DOCKER_REPO:latest sh -c "java -jar /opt/wiremock/wiremock-standalone.jar --root-dir /opt/mappings/pz"
docker run -d -p 8083:8080 --name hcom $WIREMOCK_DOCKER_REPO:latest sh -c "java -jar /opt/wiremock/wiremock-standalone.jar --root-dir /opt/mappings/hcom"
docker run -d -p 8084:8080 --name eureka $WIREMOCK_DOCKER_REPO:latest sh -c "java -jar /opt/wiremock/wiremock-standalone.jar --root-dir /opt/mappings/eureka"
