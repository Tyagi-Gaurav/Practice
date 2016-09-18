
import unittest
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

class DependencyContainersTest(unittest.TestCase):
    def setUp(self):
        myprops = dict(line.strip().split('=') for line in open('tests/DependencyTest.properties'))
        self.depHost = myprops["host"]

    def test_checkIfPDSIsAccessible(self):
        response = check_server(self.depHost, 8081)
        self.assertTrue(response)

    def test_checkIfPZIsAccessible(self):
        response = check_server(self.depHost, 8082)
        self.assertTrue(response)

    def test_checkIfHComIsAccessible(self):
        response = check_server(self.depHost, 8083)
        self.assertTrue(response)

    def test_checkIfEurekaIsAccessible(self):
        response = check_server(self.depHost, 8084)
        self.assertTrue(response)
