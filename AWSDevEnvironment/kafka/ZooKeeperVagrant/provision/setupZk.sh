#!/bin/sh

MYID=$1
ZK=$2

echo "Update VM"
yum update -y >/dev/null

echo "Installing Zookeeper"
wget http://mirror.ox.ac.uk/sites/rsync.apache.org/zookeeper/zookeeper-3.5.2-alpha/zookeeper-3.5.2-alpha.tar.gz -O /tmp/zookeeper.tgz
mkdir -p /opt/zookeeper && cd /opt/zookeeper
cp /tmp/zookeeper.tgz .
tar xvzf zookeeper.tgz --strip 1

echo "Copying and customizing Configuration"
cp /vagrant/provision/config/zookeeper/zoo.cfg /opt/zookeeper/conf
chown -R 1000:1000 /opt/zookeeper/conf
chmod -R 666 /opt/zookeeper/conf
echo "dynamicConfigFile=/opt/zookeeper/conf/zoo.cfg.dynamic" >> /opt/zookeeper/conf/zoo.cfg
mkdir -p /var/lib/zookeeper
chmod +x /vagrant/provision/script/zookeeper/zooKeeperInit.sh

#/vagrant/provision/script/zookeeper/zooKeeperInit.sh $MYID $ZK

