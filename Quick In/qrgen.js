window.onload = function loadContIcons() {

	chrome.storage.local.get('extensionUniqueId', function(result){
	        new QRCode(document.getElementById("qrcode"), ""+result.extensionUniqueId);
	});

	
	
    
}

