#!/bin/sh

cowsay "Creating Application RPM"
mvn rpm:rpm
cowsay "RPM Created"

mkdir -p /vagrant/kafka/DevTestNodeVagrant/rpm
cp target/rpm/my-kafka-pkg/RPMS/noarch/*.rpm /vagrant/kafka/DevTestNodeVagrant/rpm
