#!/bin/sh

echo "Arguments: $1 $2"
KV_STORE=$1
ID=$2
IP_ADDRESS=`ifconfig | grep -i eth0 -A5 | awk '/inet addr/{print substr($2,6)}'`
echo "Update VM"
yum update -y >/dev/null

echo "Install & Configure Kafka"
wget "http://mirror.cc.columbia.edu/pub/software/apache/kafka/0.10.0.1/kafka_2.11-0.10.0.1.tgz" -O /tmp/kafka.tgz
mkdir -p /opt/kafka/config && cd /opt/kafka && \
cp /tmp/kafka.tgz . && \
tar xvzf kafka.tgz --strip 1 && \
ls -l /opt/kafka && \
rm kafka.tgz

cp /vagrant/provision/kafka_server.properties /opt/kafka/config/server.properties
echo "zookeeper.connect=$KV_STORE" >> /opt/kafka/config/server.properties
echo "broker.id=$ID" >> /opt/kafka/config/server.properties
export KAFKA_HEAP_OPTS="-Xmx512M -Xms128M"
/opt/kafka/bin/kafka-server-start.sh /opt/kafka/config/server.properties &
