
    var myUsername = "";
	chrome.storage.local.get('email', function(result){
        myUsername = result.email;
        
   
	    var myPassword = "";
	    chrome.storage.local.get('pass', function(result){
	        myPassword = result.pass;


		   var myId = "";   
		   chrome.storage.local.get('id', function(result){
		        myId = result.id;
				

				var loginField=null;
				var passwordField=null;
				var loginForm = null;
				var defaultReplace = null;


				switch(myId){
					case 'stackoverflow' :
						loginField = document.getElementById('email');
						passwordField = document.getElementById('password');
						loginForm = document.getElementById('submit-button');
						defaultReplace = "https://stackoverflow.com";
					break;

					case 'facebook' :
						loginField = document.getElementById('email');
						passwordField = document.getElementById('pass');
						loginForm = document.getElementById('loginbutton');
						defaultReplace = "https://facebook.com";
					break;

					case 'quora' :
						loginField = document.getElementByName('email')[0];
						passwordField = document.getElementByName('password')[0];
						loginForm = document.querySelectorAll('[id*="submit_button"]');
						defaultReplace = null;
						alert(loginField.id);
					break;

					case 'twitter' :
						loginForm = document.querySelectorAll('button[type=submit]')[0];
						loginField = document.getElementsByClassName('js-username-field email-input js-initial-focus')[0];
						passwordField = document.getElementsByClassName('js-password-field')[0];
						defaultReplace = null;
					break;

					case 'pinterest' :
						loginForm = document.querySelectorAll('button[type=submit]')[0];
						loginField = document.querySelectorAll('input[type=email]')[0];
						loginForm = document.querySelectorAll('button[type=password]')[0];
						defaultReplace = null;
					break;
						
					case 'github' :
						loginField = document.getElementById('login_field');
						passwordField = document.getElementById('password');
						loginForm = document.getElementsByName( 'commit' )[0];
						defaultReplace = "https://github.com";
					break;

					default:
						alert("id not defined!");

				}



				if(loginField == null || passwordField == null || loginForm ==null  ){
					
					if(defaultReplace != null ){
						location.replace(defaultReplace);
					}
				}
				else {
					loginField.value = myUsername;
					passwordField.value = myPassword;
					loginForm.click();
				}



					
				});
	 	});

	});

