var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);
var HashMap = require('hashmap');
crypto = require("crypto");


var myMap = new HashMap();


io.on('connection', function(socket){

  socket.on('preCheckId', function(data){
  console.log(data.extensionUniqueId);
  
  		if(data.extensionUniqueId === 'nothing'){
			var s = crypto.randomBytes(32).toString("hex");
  			socket.emit('getUniqueId', { 'id': s });
  			myMap.set(s, socket);
  			console.log(s);
  		}
  		else{
			myMap.set(data.extensionUniqueId, socket);
  		}
  });

  
  socket.on('cred', function (data) {

	var sendsock = myMap.get(data.ss);

	sendsock.emit("openUrl", data.url);
  	
    console.log(data);
  });

  
  console.log("connected");
  
});



http.listen(3000, function(){
  console.log('listening on *:3000');
});
