#!/bin/sh

MYID=$1
ZK=$2

HOSTNAME=`hostname`
IPADDRESS=`ip -4 addr show scope global dev eth0 | grep inet | awk '{print $2}' | cut -d / -f 1`
cd /opt/zookeeper
CONF="/opt/zookeeper/conf"
DYNAMIC=$CONF/zoo.cfg.dynamic

if [ -n "$ZK" ]
then
  sudo echo "`bin/zkCli.sh -server $ZK:80 get /zookeeper/config|grep ^server`" >> $DYNAMIC
  sudo echo "server.$MYID=$IPADDRESS:2888:3888:observer;80" >> $DYNAMIC
  sudo cp $DYNAMIC $DYNAMIC.org
  sudo /opt/zookeeper/bin/zkServer-initialize.sh --force --myid=$MYID
  sudo ZOO_LOG_DIR=/var/log ZOO_LOG4J_PROP='INFO,CONSOLE,ROLLINGFILE' /opt/zookeeper/bin/zkServer.sh start
  sudo /opt/zookeeper/bin/zkCli.sh -server $ZK:80 reconfig -add "server.$MYID=$IPADDRESS:2888:3888:participant;80"
  sudo /opt/zookeeper/bin/zkServer.sh stop
  sudo ZOO_LOG_DIR=/var/log ZOO_LOG4J_PROP='INFO,CONSOLE,ROLLINGFILE' /opt/zookeeper/bin/zkServer.sh start
else
  sudo echo "server.$MYID=$IPADDRESS:2888:3888;80" >> $DYNAMIC
  sudo /opt/zookeeper/bin/zkServer-initialize.sh --force --myid=$MYID
  sudo ZOO_LOG_DIR=/var/log ZOO_LOG4J_PROP='INFO,CONSOLE,ROLLINGFILE' /opt/zookeeper/bin/zkServer.sh start
fi