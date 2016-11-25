
var net = require('net');
var events = require('events')
var channel = new events.EventEmitter();

channel.clients = [];
channel.subscriptions = {};

channel.on('join', function (id, client) {
    console.log('Client joined on channel');
    this.clients[id] = client;
    this.subscriptions[id] = function(senderId, message) {
        console.log('Broadcasting Data from sender: ' + senderId + ', ' + message);
        if (id != senderId) {
            this.clients[id].write(message);
        }
    };

    this.on('broadcast', this.subscriptions[id]);
});

var server = net.createServer(function(client) {
    var id = client.remoteAddress + ':' + client.remotePort;
    console.log('Client id ' + id);
    channel.emit('join', id, client);

    client.on('data', function(data) {
        data = data.toString();
        console.log('Client Data Broadcasting: ' + data);
       channel.emit('broadcast', id, data);
    });
});

server.listen(8888);