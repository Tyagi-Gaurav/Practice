#!/bin/sh

mvn rpm:rpm

rpm -q --filesbypkg -p ./target/rpm/my-kafka-pkg/RPMS/noarch/my-kafka-pkg-1.0-1.noarch.rpm