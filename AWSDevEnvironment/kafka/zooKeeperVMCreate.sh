
#!/bin/sh

CUR_DIR=`pwd`
export VAGRANT_CWD=$CUR_DIR/../ZooKeeperVagrant

vagrant up --provider=aws

HOSTS[0]=zookeeper-host-1
HOSTS[1]=zookeeper-host-2
HOSTS[2]=zookeeper-host-3

ec2Host1=`vagrant ssh-config ${HOSTS[0]} | grep -i hostname | awk '{print $2}'`
echo $ec2Host1
ec2Host2=`vagrant ssh-config ${HOSTS[1]} | grep -i hostname | awk '{print $2}'`
echo $ec2Host2
ec2Host3=`vagrant ssh-config ${HOSTS[2]} | grep -i hostname | awk '{print $2}'`
echo $ec2Host3

vagrant ssh zookeeper-host-1 -c "sudo /vagrant/provision/script/zookeeper/zooKeeperInit.sh 1"
vagrant ssh zookeeper-host-2 -c "sudo /vagrant/provision/script/zookeeper/zooKeeperInit.sh 2 $ec2Host1"
vagrant ssh zookeeper-host-3 -c "sudo /vagrant/provision/script/zookeeper/zooKeeperInit.sh 3 $ec2Host1"