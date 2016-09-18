
import unittest
import re
import socket
from kazoo.client import KazooClient

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

class ZookeeperTest(unittest.TestCase):

    def setUp(self):
        myprops = dict(line.strip().split('=') for line in open('tests/ZookeeperTest.properties'))
        self.zookeeperHost = myprops["host"]
        hosts = re.split(",", self.zookeeperHost)
        for host in hosts:
            zk = KazooClient(hosts=host+":80")
            zk.start(timeout=5)
            zk.delete("/my/favorite/node", recursive=True)
            zk.stop()

    def test_checkIfZookeeperHostsAreAccessible(self):
        hosts = re.split(",", self.zookeeperHost)
        for host in hosts:
            # print host
            response = check_server(host, 80)
            self.assertTrue(response)

    def test_checkIfZookeeperIsAccessibleViaAClient(self):
        hosts = re.split(",", self.zookeeperHost)
        for host in hosts:
            zk = KazooClient(hosts=host+":80")
            try:
                zk.start(timeout=5)
                children = zk.get_children("/")
                self.assertIsNotNone(children)
                self.assertTrue("zookeeper" in children)
            finally:
                zk.stop()

    def test_checkIfZookeepersTalkToEachOther(self):
        hosts = re.split(",", self.zookeeperHost)
        zk = KazooClient(hosts=hosts[0]+":80")
        zk.start(timeout=5)
        zk.ensure_path("/my/favorite/node")
        zk.stop()

        zk = KazooClient(hosts=hosts[1]+":80")
        zk.start(timeout=5)
        node = zk.get("/my/favorite/node")
        # print node
        self.assertIsNotNone(node)
        zk.delete("/my/favorite/node", recursive=True)
        zk.stop()

    def test_thereAreOnlyPredefinedNumberOfZookeeperInstances(self):
        hosts = re.split(",", self.zookeeperHost)
        self.assertEquals(len(hosts), 3)