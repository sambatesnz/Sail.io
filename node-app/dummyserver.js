/**
 * Simple Server running on Port 3000. This is located on the VM in '/home/sengstudent/myapp'.
 *
 * To start/stop the app, cd into the direction on the VM (SSH in) and run the following commands:
 *
 * To Start:    'forever start index.js'
 * To Stop:     'forever stop index.js'
 */
var express = require('express');
var path = require('path')
var app = express();
var http = require('http').Server(app);
var io = require('socket.io')(http);


app.get('/', function(req,res) {
    var a = {
        info: "Hey tim tam "
    }
    res.send(a);
});


app.get('/index', function(req, res) {
    console.log(path.join(__dirname, "ac35.bin"));
    res.send("Hey bro");
})


/*
 try localhost:3000/static/dummerserver.js
 */
app.use('/static', express.static(__dirname))


/*
 This bad boy sends the file to client so is better then serving a static directory
 see localhost:3000/files
 */
app.get('/files', function(req, res) {
    res.sendFile(__dirname +'/dummyserver.js');
})

io.on('connection', function(socket) {
    console.log("a user has connected");
})

app.listen(3000, function() {
    console.log('Example app listening on port 3000!');
});