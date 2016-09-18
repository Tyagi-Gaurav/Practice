
import unittest
from kafka import KafkaConsumer
from kafka.client import KafkaClient
import re
import socket

def check_server(address, port):
    # Create a TCP socket
    s = socket.socket()
    # print "Attempting to connect to %s on port %s" % (address, port)
    try:
        s.connect((address, port))
        # print "Connected to %s on port %s" % (address, port)
        return True
    except socket.error, e:
        print "Connection to %s on port %s failed: %s" % (address, port, e)
        return False

class DevKafkaTest(unittest.TestCase):
    def setUp(self):
        myprops = dict(line.strip().split('=') for line in open('tests/KafkaTest.properties'))
        self.kafkaHost = myprops["host"]

    def test_checkIfKafkaBrokerIsAccessibleViaConsumer(self):
        hosts = re.split(",", self.kafkaHost)
        for host in hosts:
            consumer = KafkaConsumer(bootstrap_servers=host + ':9092')
            # print consumer
            self.assertIsNotNone(consumer)
            consumer.close()

    def test_checkIfKafkaBrokerIsAccessibleViaNetwork(self):
        hosts = re.split(",", self.kafkaHost)
        for host in hosts:
            response = check_server(host, 9092)
            self.assertTrue(response)

    # def test_checkIfKafkaBrokersAreConnectedViaZookeeper(self):
    #     hosts = re.split(",", self.kafkaHost)
    #     client = KafkaClient(bootstrap_servers=hosts[0] + ':9092')
    #     client.add_topic("testTopic")
    #     client.close()
    #     client = KafkaClient(bootstrap_servers=hosts[1] + ':9092')
    #     client.li
    #
    #     self.assertTrue(False)