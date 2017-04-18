/**
 * Simple Server running on Port 3000. This is located on the VM in '/home/sengstudent/myapp'.
 *
 * To start/stop the app, cd into the direction on the VM (SSH in) and run the following commands:
 *
 * To Start:    'forever start index.js'
 * To Stop:     'forever stop index.js'
 */
var express = require('express');
var app = express();

app.get('/', function(req,res) {
    res.send('Hello World & Tim!');
});

app.listen(3000, function() {
    console.log('Example app listening on port 3000!');
});

