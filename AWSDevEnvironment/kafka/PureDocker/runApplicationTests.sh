#!/bin/sh


CUR_DIR=`pwd`
export VAGRANT_CWD=$CUR_DIR/../DependencyVagrant
DEPENDENCYHOST=`vagrant ssh-config dep-host-1 | grep -i hostname | awk '{print $2}'`

CUR_DIR=`pwd`
export VAGRANT_CWD=$CUR_DIR/../KafkaVagrant
kafkaHost1=`vagrant ssh-config kafka-host-1 | grep -i hostname | awk '{print $2}'`
kafkaHost2=`vagrant ssh-config kafka-host-2 | grep -i hostname | awk '{print $2}'`
kafkaHost3=`vagrant ssh-config kafka-host-3 | grep -i hostname | awk '{print $2}'`

CUR_DIR=`pwd`
export VAGRANT_CWD=$CUR_DIR/../DevTestNodeVagrant
TX_NODE=`vagrant ssh-config dev-host-1 | grep -i hostname | awk '{print $2}'`

PDS_SERVICE_URL=$DEPENDENCYHOST:8081
PZ_SERVICE_URL=$DEPENDENCYHOST:8082
TRANSFORMER_HEALTH_CHECK_URL=$TX_NODE:8081
KAFKA_BROKERS=$kafkaHost1:9092,$kafkaHost2:9092,$kafkaHost3:9092

#echo $DEPENDENCYHOST
#echo $TX_NODE
#echo $KAFKA_BROKERS

sed -i -e "s/PDS_SERVICE_URL/$PDS_SERVICE_URL/g" /vagrant/target/ean-eonh-transformer/ean-eonh-transformer-functional-tests/src/main/resources/NotificationsConfig.groovy
sed -i -e "s/PZ_SERVICE_URL/$PZ_SERVICE_URL/g" /vagrant/target/ean-eonh-transformer/ean-eonh-transformer-functional-tests/src/main/resources/NotificationsConfig.groovy
sed -i -e "s/TRANSFORMER_HEALTH_CHECK_URL/$TRANSFORMER_HEALTH_CHECK_URL/g" /vagrant/target/ean-eonh-transformer/ean-eonh-transformer-functional-tests/src/main/resources/NotificationsConfig.groovy
sed -i -e "s/KAFKA_BROKERS/$KAFKA_BROKERS/g" /vagrant/target/ean-eonh-transformer/ean-eonh-transformer-functional-tests/src/main/resources/NotificationsConfig.groovy

CUR_DIR=`pwd`
cd $CUR_DIR/../../target/ean-eonh-transformer/
./gradlew clean ean-eonh-transformer-functional-tests:testTransformerFunctional -Dtest.env=dev

cd $CUR_DIR