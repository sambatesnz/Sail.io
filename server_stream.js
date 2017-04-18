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