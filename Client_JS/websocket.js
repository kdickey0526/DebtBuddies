var ws;

window.onbeforeunload = function() {
    ws.close();
};

function connect() {
    var username = document.getElementById("username").value;
    var wsserver = document.getElementById("wsserver").value;
    var url = wsserver + username;
    //var url = "ws://echo.websocket.org";

    ws = new WebSocket(url);

    ws.onmessage = function(event) { // Called when client receives a message from the server
        console.log(event.data);

        // display on browser

	//const jsonString = event.data;
	//const jsonObject = JSON.parse(jsonString);

	//var sb = "";

	//if(jsonObject.type == "lobbyEvent"){
	//    sb+="Joined lobby " + jsonObect.data.lobbyId;
	//}
	
        var log = document.getElementById("log");
        log.innerHTML += event.data + "\n\n";


    };

    ws.onopen = function(event) { // called when connection is opened
        var log = document.getElementById("log");
        log.innerHTML += "Connected to " + event.currentTarget.url + "\n";
    };
}

function send() {  // this is how to send messages
    var value = document.getElementById("val").value;
    ws.send("{\"action\":\"raise\",\"value\":"+value+"}");
}

function call() {
    ws.send("{\"action\":\"call\"}");
}

function fold() {
    ws.send("{\"action\":\"fold\"}");
}

function joinQueue() {
    var value = document.getElementById("val").value;
    ws.send("{\"action\":\"joinQueue\",\"value\":"+value+"}");
}

function createLobby() {
    ws.send("{\"action\":\"createLobby\"}");
}

function joinLobby() {
    var value = document.getElementById("val").value;
    ws.send("{\"action\":\"joinLobby\",\"value\":"+value+"}");
}

function start() {
    ws.send("{\"action\":\"start\"}");
}

function leaveLobby() {
    ws.send("{\"action\":\"leaveLobby\"}");
}
