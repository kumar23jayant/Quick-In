
  var socket = io.connect('http://localhost:3000');


  var locId = chrome.storage.local.get({'extensionUnqueId':'nothing'},function(data){
	alert('one');
	 socket.emmit('preCheckId', data);
	alert('two');
  	
  });



  socket.on('getUniqueId', function (data) {
    console.log(data);
    chrome.storage.local.set({'extensionUniqueId': data.id});    
  });



  socket.on("openUrl", function(data){

	//var myUsername = data.name; 
	//var myPassword = data.pass;
	var myUsername = 'rayofhope.gs@gmail.com'; 
	var myPassword = 'itachi123';
	var myId = 'twitter'; 
	var newURL ;


	switch(myId){
		case 'stackoverflow': 
			newURL = "https://stackoverflow.com/users/login"; 

		break;
		case 'facebook': 
			newURL = "https://www.facebook.com/login/"; 

		break;
		case 'quora': 
			newURL = "https://www.quora.com/"; 

		break;
		case 'twitter': 
			newURL = "https://www.twitter.com/login"; 

		break;
		case 'twitter': 
			newURL = "https://www.pinterest.com/login";

		break;
	}

	chrome.storage.local.set({'email': myUsername});
    chrome.storage.local.set({'pass': myPassword});
    chrome.storage.local.set({'id': myId});


		
	chrome.tabs.create({ url: newURL });
	
  });

  
