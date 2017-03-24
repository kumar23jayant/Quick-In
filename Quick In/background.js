
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

	  
	//Pass the recieved username and password along with the id of website to login 
	  
	//var myUsername = data.name; 
	//var myPassword = data.pass;
	var myUsername = 'rayofhope.gs@gmail.com'; 
	var myPassword = 'itachi123';
	var myId = 'stackoverflow'; 
	var newURL ;

	// deciding on the url to use based on the webste id
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
		case 'pinterest': 
			newURL = "https://www.pinterest.com/login";
			
		break;
		case 'github': 
			newURL = "https://www.github.com/login";
			
		break;
		case 'dropbox': 
			newURL = "https://www.dropbox.com/login";
	}

	//using chrome local storage to communicate variables.
	chrome.storage.local.set({'email': myUsername});
	chrome.storage.local.set({'pass': myPassword});
	chrome.storage.local.set({'id': myId});


	//opening the new tab with the specified url	
	chrome.tabs.create({ url: newURL });
	
  });

  
