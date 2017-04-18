/**
 * This is the script used to create the VM server to serve binary data on port 4941 (in keeping with the AC35
 * test stream).
 *
 * To start/stop the app, cd into the direction on the VM (SSH in) and run the following commands:
 *
 * To Start:    'forever start index.js'
 * To Stop:     'forever stop index.js'
 */
var express = require('express');
var app = express();

app.get('circl.png', function(req,res) {

    var options = {
        // root: __dirname + '/public/',
        // dotfiles: 'deny',
        headers: {
            'x-timestamp': Date.now(),
            'x-sent': true
        }
    };

    res.send('You are now receiving data from team2\'s testing data!');

    res.sendFile('/home/sengstudent/ac35server4941/circl.png', options, function (err) {
        if (err) {
            next(err);
        } else {
            console.log('Sent File');
        }
    });

    res.download('/home/sengstudent/ac35server4941/circl.png', 'circl.png', function(err){
        if (err) {
            // Handle error, but keep in mind the response may be partially-sent
            // so check res.headersSent
        } else {
            // decrement a download credit, etc.
        }
    });
});

app.listen(4941, function() {
    console.log('Server Listening on port 4941!');
    console.log('Hopefully soon this will transmit data.');
});