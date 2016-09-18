#!/bin/sh

CUR_DIR=`pwd`

export VAGRANT_CWD=$CUR_DIR/DependencyVagrant
DEPHOSTS[0]=dep-host-1

for host in ${DEPHOSTS[*]}; do
    vagrant destroy $host --force
done

export VAGRANT_CWD=$CUR_DIR/DevTestNodeVagrant
DTHOSTS[0]=dev-host-1

for host in ${DTHOSTS[*]}; do
    vagrant destroy $host --force
done

export VAGRANT_CWD=$CUR_DIR/KafkaVagrant
NHOSTS[0]=kafka-host-1
NHOSTS[1]=kafka-host-2
NHOSTS[2]=kafka-host-3

for host in ${NHOSTS[*]}; do
    vagrant destroy $host --force
done

export VAGRANT_CWD=$CUR_DIR/ZooKeeperVagrant

HOSTS[0]=zookeeper-host-1
HOSTS[1]=zookeeper-host-2
HOSTS[2]=zookeeper-host-3

for host in ${HOSTS[*]}; do
    vagrant destroy $host --force
done

