#!/bin/sh

CUR_DIR=`pwd`
export VAGRANT_CWD=$CUR_DIR/../DependencyVagrant

vagrant up --provider=aws
