#!/bin/sh

: ${AWS_ACCESS_KEY_ID?"Need to provide AWS_ACCESS_KEY_ID"}
: ${AWS_SECRET_ACCESS_KEY?"Need to provide AWS_SECRET_ACCESS_KEY"}

EXTRA_VARS="--extra-vars class_tag=integration_test_instance --extra-vars tier_tag_param=wordpress"

export ANSIBLE_HOST=./ec2.py
export EC2_INI_PATH=./ec2.ini

#Create VM
#ansible-playbook -i localhost playbooks/createCloudInstances.yml $EXTRA_VARS

#Refresh Ansible Cache so it has latest ec2 inventory information.
$ANSIBLE_HOST --refresh-cache > /dev/null

#Do Stuff for Integration here
#DEBUG: ansible-playbook -vvvv -i $ANSIBLE_HOST playbooks/doStuff.yml $EXTRA_VARS
ansible-playbook -i $ANSIBLE_HOST playbooks/doWordPressStuff.yml $EXTRA_VARS

#TearDown VMs
#ansible-playbook -i localhost playbooks/teardownIntegrationInstances.yml $EXTRA_VARS


