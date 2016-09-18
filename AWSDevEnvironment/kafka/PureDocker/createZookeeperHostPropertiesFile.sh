#!/bin/sh

CUR_DIR=`pwd`
export VAGRANT_CWD=$CUR_DIR/../ZooKeeperVagrant

HOSTS[0]=zookeeper-host-1
HOSTS[1]=zookeeper-host-2
HOSTS[2]=zookeeper-host-3

ec2Host1=`vagrant ssh-config ${HOSTS[0]} | grep -i hostname | awk '{print $2}'`
ec2Host2=`vagrant ssh-config ${HOSTS[1]} | grep -i hostname | awk '{print $2}'`
ec2Host3=`vagrant ssh-config ${HOSTS[2]} | grep -i hostname | awk '{print $2}'`
echo "host=$ec2Host1,$ec2Host2,$ec2Host3" > ../tests/ZookeeperTest.properties

