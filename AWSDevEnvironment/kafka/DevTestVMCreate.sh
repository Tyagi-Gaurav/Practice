#!/bin/sh

CUR_DIR=`pwd`
export VAGRANT_CWD=$CUR_DIR/../DependencyVagrant
export DEPENDENCYHOST=`vagrant ssh-config dep-host-1 | grep -i hostname | awk '{print $2}'`

CUR_DIR=`pwd`
export VAGRANT_CWD=$CUR_DIR/../KafkaVagrant
export KAFKAHOST=`vagrant ssh-config kafka-host-1 | grep -i hostname | awk '{print $2}'`

#echo "KAFKAHOST : $KAFKAHOST"
#echo "DEPENDENCYHOST: $DEPENDENCYHOST"

CUR_DIR=`pwd`
export VAGRANT_CWD=$CUR_DIR/../DevTestNodeVagrant
vagrant up --provider=aws
