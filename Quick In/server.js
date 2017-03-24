window.onload = function(){

var socket = io.connect('http://localhost:3000');
  socket.on('news', function (data) {
    console.log(data);
    //document.getElementById("text").innerHTML = data;
    socket.emit('message', { my: 'data' });
  });

new QRCode(document.getElementById("qrcode"), "jitu madarchod hai");

}


