var fs = require('fs');


var readStream = fs.createReadStream('./test.txt');
readStream.on('data', function (chunk) {
    console.log(chunk);
})

readStream.on('end', function(data) {
    console.log("done")
})

console.log("Hi there");