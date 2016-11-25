
var http = require('http');
var fs = require('fs');

http.createServer(function(req, res) {
    if (req.url == '/') {
        fs.readFile('./public/titles.json', function (err, data) {
            if (err) {
                hadError(err, res);
            } else {
                getTemplate(data, res);
            }
        })
    }
}).listen(8000, '127.0.0.1');

function hadError(err, res) {
    console.error(err);
    res.end('Server Error');
}

function formatHtml(data, titles, res) {
    var templ = data.toString();
    var html = templ.replace('%', titles.join('<li></li>'));
    res.writeHead(200, {'Content-Type': 'text/html'});
    res.end(html);
}

function getTemplate(data, res) {
    var titles = JSON.parse(data.toString());

    fs.readFile('./public/template.html', function (err, data) {
        if (err) {
            hadError(err, res);
        } else {
            formatHtml(data, titles, res);
        }
    });
}

