#!/bin/sh

: ${AWS_ACCESS_KEY_ID?"Need to provide AWS_ACCESS_KEY_ID"}
: ${AWS_SECRET_ACCESS_KEY?"Need to provide AWS_SECRET_ACCESS_KEY"}

USER_DB_TIER_TAG="UserDatabase"
USER_APP_TIER_TAG="UserApplication"
EXTRA_VARS="--extra-vars class_tag=integration_test_instance --extra-vars db_tier_tag=$USER_DB_TIER_TAG --extra-vars app_tier_tag=$USER_APP_TIER_TAG"

#Create VM
ansible-playbook -i localhost playbooks/create_rds_instances.yml $EXTRA_VARS

export ANSIBLE_HOST=./ec2.py
export EC2_INI_PATH=./ec2.ini

#Refresh Ansible Cache so it has latest ec2 inventory information.
$ANSIBLE_HOST --refresh-cache > /dev/null

#Do Stuff for Integration here
#DEBUG: ansible-playbook -vvvv -i $ANSIBLE_HOST playbooks/doStuff.yml $EXTRA_VARS
ansible-playbook -i $ANSIBLE_HOST playbooks/doWordPressStuff.yml $EXTRA_VARS

#TearDown VMs
#ansible-playbook -i localhost playbooks/teardownIntegrationInstances.yml $EXTRA_VARS


