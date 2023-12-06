var ws;

window.addEventListener('beforeunload', function() {
    if (ws) {
        ws.onclose = function () {}; // Disable onclose handler first
        ws.close();
    }
});

function connect() {
    var username = document.getElementById("username").value;
    var wsserver = document.getElementById("wsserver").value;
    var url = wsserver + username;
    //var url = "ws://echo.websocket.org";

    ws = new WebSocket(url);

    ws.onmessage = function(event) { // Called when client receives a message from the server
        console.log(event.data);

        // display on browser

	/*

	var sb = "";

	try{

	const jsonString = event.data;
	const jsonObject = JSON.parse(jsonString);

	if(jsonObject.type == "lobbyEvent"){
	    if(jsonObject.data.type == "joinQueue"){
		sb+="Joined lobby " + jsonObject.data.lobbyId;
	    }
	}else if(jsonObject.type == "startInfo"){
	    sb+="Hand:\n" + jsonObject.data.hand[0].rank + " of " + jsonObject.data.hand[0].suit + "\n" + jsonObject.data.hand[1].rank + " of " + jsonObject.data.hand[1].suit;
	}else if(jsonObject.type == "turnInfo"){

	}else if(jsonObject.type == "stageInfo"){

	}else if(jsonObject.type == "endInfo"){

	}
	
	}catch(error){
	    console.error("json error");
	}

	console.log(sb);

	*/
	
        var log = document.getElementById("log");
        log.innerHTML += event.data + "\n\n";


    };

    ws.onopen = function(event) { // called when connection is opened
        var log = document.getElementById("log");
        log.innerHTML += "Connected to " + event.currentTarget.url + "\n";
    };

    ws.onclose = function(event) {
        console.error('WebSocket closed:', event);
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
