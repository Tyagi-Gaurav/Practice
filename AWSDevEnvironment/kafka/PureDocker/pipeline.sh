#!/bin/sh

PIPELINE_DIR=`pwd`
START=$(date +%s)

cowsay "Creating ZOOKEEPER Cluster" &&
../zooKeeperVMCreate.sh &&
cowsay "ZOOKEEPER Cluster Created...Preparing to test the cluster now...." &&
./createZookeeperHostPropertiesFile.sh &&
cd .. &&
python -m unittest tests.ZookeeperTest &&
cd $PIPELINE_DIR &&
cowsay "ZOOKEEPER Cluster Tested...Creating KAFKA cluster now" &&
../createKafkaCluster.sh &&
cowsay "KAFKA Cluster Created...Preparing to test it now" &&
./createKafkaHostPropertiesFile.sh &&
cd .. &&
python -m unittest tests.DevKafkaTest &&
cd $PIPELINE_DIR &&
cowsay "KAFKA Cluster Tested...Creating Dependency VM and Containers" &&
../createApplicationDependencyVM.sh &&
cowsay "Dependency Container created...Testing dependencies now" &&
sleep 5s
./createDependencyHostPropertiesFile.sh &&
cd .. &&
python -m unittest tests.DependencyContainersTest &&
cd $PIPELINE_DIR &&
./createApplicationRPM.sh &&
cowsay "Creating VM for functional Test now And Deploying Application" &&
../DevTestVMCreate.sh &&
cowsay "Application Deployed..Running Functional Tests now" &&
./runApplicationTests.sh &&
cd .. &&
./destroyAllVms.sh &&
cd $PIPELINE_DIR

END=$(date +%s)
DIFF=$(( $END - $START ))

cowsay -f elephant "Pipeline execution took $DIFF seconds"

