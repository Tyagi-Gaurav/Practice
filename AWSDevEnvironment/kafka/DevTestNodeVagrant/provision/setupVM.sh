#!/bin/sh

#echo "Arguments: $1 $2"
KAFKA_HOST=$1
DEP_HOST=$2
IP_ADDRESS=`ifconfig | grep -i eth0 -A5 | awk '/inet addr/{print substr($2,6)}'`
echo "Update VM"
yum update -y >/dev/null

# Install Docker
echo "Install Docker"
yum install docker -y >/dev/null

# Start Docker
echo "Start Docker Service"
service docker start

echo "Assign Permissions"
usermod -a -G docker ec2-user

echo "Installing App"
yum install -y /vagrant/rpm/my-kafka-pkg-2.0-1.noarch.rpm

echo "Installing Java8"
wget --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/8u60-b27/jdk-8u60-linux-x64.rpm"
yum localinstall -y jdk-8u60-linux-x64.rpm
rm jdk-8u60-linux-x64.rpm

#echo "Arguments: $KAFKA_HOST $DEP_HOST"
echo "Provisioning Transformer with test instances"
sed -i -e "s/KAFKA_BROKER/$KAFKA_HOST/g" /vagrant/jar/dev-config.yml
sed -i -e "s/PDS_HOST/$DEP_HOST:8081/g" /vagrant/jar/dev-config.yml
sed -i -e "s/EUREKA_HOST/$DEP_HOST:8084/g" /vagrant/jar/dev-config.yml
sed -i -e "s/HCOM_HOST/$DEP_HOST:8083/g" /vagrant/jar/dev-config.yml

echo "Copying PDS Keys"
cp /vagrant/keys/eanPDSClient.jks /tmp

echo "Starting Transformer"
chown -R ec2-user:ec2-user /opt/transformer
chmod -R 766 /opt/transformer
cd /opt/transformer
#java -jar ./bin/ean-eonh-transformer server /vagrant/jar/dev-config.yml

