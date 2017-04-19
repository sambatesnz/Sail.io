var net = require('net');
var fs = require('fs');
var HOST = '132.181.12.155'; // parameterize the IP of the Listen
var PORT = 3001; // TCP LISTEN port


// Create an instance of the Server and waits for a conexão
net.createServer(function(sock) {


    // Receives a connection - a socket object is associated to the connection automatically
    console.log('CONNECTED: ' + sock.remoteAddress +':'+ sock.remotePort);


    var readStream = fs.createReadStream('./ac35.bin');
    readStream.on('data', function (chunk) {
        sock.write(chunk);
    })


    readStream.on('end', function() {
        console.log(`Ending stream`)
        sock.end("END OF STREAM")
    })

    sock.on('connection', function(){
        console.log("Connection made");
    })

    // Add a 'data' - "event handler" in this socket instance
    sock.on('data', function(data) {
        console.log("got some data");
        // data was received in the socket
        // Writes the received message back to the socket (echo)
        sock.write(data);
    });


    // Add a 'close' - "event handler" in this socket instance
    sock.on('close', function(data) {
        // closed connection
        console.log('CLOSED: ' + sock.remoteAddress +' '+ sock.remotePort);
    });


}).listen(PORT, HOST);


console.log('Server listening on ' + HOST +':'+ PORT);