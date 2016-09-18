#!/bin/sh

CUR_DIR=`pwd`
export VAGRANT_CWD=$CUR_DIR/../DependencyVagrant

HOSTS[0]=dep-host-1

ec2Host1=`vagrant ssh-config ${HOSTS[0]} | grep -i hostname | awk '{print $2}'`
export DEPENDENCYHOST=$ec2Host1
echo "host=$ec2Host1" > ../tests/DependencyTest.properties

