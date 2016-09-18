#!/bin/sh

echo "Arguments: $1"

KV_STORE=$1
IP_ADDRESS=`ifconfig | grep -i eth0 -A5 | awk '/inet addr/{print substr($2,6)}'`
echo "Update VM"
yum update -y >/dev/null

echo "Installing Java8"
wget --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/8u60-b27/jdk-8u60-linux-x64.rpm"
yum localinstall -y jdk-8u60-linux-x64.rpm
rm jdk-8u60-linux-x64.rpm

echo "Install Docker"
yum install docker -y >/dev/null
service docker start

wget https://releases.hashicorp.com/vagrant/1.8.5/vagrant_1.8.5_x86_64.rpm
yum install vagrant_1.8.5_x86_64.rpm -y > /dev/null
yum install -y pip > /dev/null
pip install kafka-python
pip install kazoo
yum install -y fortune cowsay > /dev/null
pip install --upgrade pip

echo "Installing maven"
sudo wget http://repos.fedorapeople.org/repos/dchen/apache-maven/epel-apache-maven.repo -O /etc/yum.repos.d/epel-apache-maven.repo
sudo sed -i s/\$releasever/6/g /etc/yum.repos.d/epel-apache-maven.repo
sudo yum install -y apache-maven
mvn --version
yum install -y rpm-build > /dev/null

vagrant plugin install vagrant-aws

KEY_DIR=/Users/gtyagi/personalWorkspace/online/DockerEnvironments/kafka
KEY=$KEY_DIR/gtyagiawskey.pem

sudo mkdir -p $KEY_DIR
sudo cp /vagrant/gtyagiawskey.pem $KEY_DIR
sudo chown -R ec2-user:ec2-user $KEY_DIR
sudo chmod -R +x $KEY_DIR
sudo chmod 444 $KEY

sudo mkdir -p /home/ec2-user/.m2/repository
cp -R /vagrant/source/* /home/ec2-user/.gradle

# Create Swap Space
sudo /bin/dd if=/dev/zero of=/var/swap.1 bs=1M count=1024
sudo /sbin/mkswap /var/swap.1
sudo /sbin/swapon /var/swap.1
