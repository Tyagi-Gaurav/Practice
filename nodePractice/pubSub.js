
var net = require('net');
var events = require('events')
var channel = new events.EventEmitter();

channel.clientSockets = [];
channel.subscriptions = {};

channel.on('join', function (id, client) {
    console.log('Client joined on channel');
    this.clientSockets[id] = client;
    this.subscriptions[id] = function(senderId, message) {
        console.log('Broadcasting Data from sender: ' + senderId + ', ' + message);
        if (id != senderId) {
            this.clientSockets[id].write(message);
        }
    };

    this.on('broadcast', this.subscriptions[id]);
});

channel.on('leave', function(id) {
   channel.removeListener('broadcast', this.subscriptions[id]);
    channel.emit('broadcast', id, id + ' has left the chat.');
});

var server = net.createServer(function(socket) {
    var id = socket.remoteAddress + ':' + socket.remotePort;
    console.log('Client id ' + id);
    channel.emit('join', id, socket);

    socket.on('data', function(data) {
        data = data.toString();
        console.log('Client Data Broadcasting: ' + data);
       channel.emit('broadcast', id, data);
    });

    socket.on('leave', function(id) {
       channel.emit('leave', id);
    });
});

server.listen(8888);