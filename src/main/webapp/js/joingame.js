var checkID;
var checkPlayerOne;
var txtGameID = document.getElementById("txtGameID");
var btnJoin = document.getElementById("btnJoin");
var playerRandom = Math.random().toString(36).substr(2, 8);
var uuidRandom = Math.random().toString(36).substr(2, 8);
var chosenPlayerId = playerRandom;
var chosenUuid = uuidRandom;
var getPlayerId = sessionStorage.getItem("playerId");

document.addEventListener('DOMContentLoaded', function(event) {
	console.log(getPlayerId);
    btnJoin.addEventListener("click", function() {
		var url = "http://localhost:8080/tictactoe/tictactoeserver/check?key=" + txtGameID.value;
    	fetch(url)
        .then(response => response.text())
        .then((response) => {
            checkID = response;
            //CHECK IF GAME IS ALREADY STARTING
            if (checkID === "true") {
        		spectateGame();
	        }  
	        else {
	        	
	        //CHECK IF PLAYER X IS ALREADY IN-GAME OR NOT
        	var checkPlayerOneURL = "http://localhost:8080/tictactoe/tictactoeserver/createGame?key=" + txtGameID.value;
	    	fetch(checkPlayerOneURL)
	        .then(response => response.text())
	        .then((response) => {
	            checkPlayerOne = response;
	            if (checkPlayerOne === "x" || checkPlayerOne === "X") {
	        	alert("Sorry! This Game ID does not exist.");
	        	var resetPlayerOneURL = "http://localhost:8080/tictactoe/tictactoeserver/reset?key=" + txtGameID.value;
		    	fetch(resetPlayerOneURL)
		        .then(response => response.text())
		        .then((response) => {
		            console.log(response)
		        })
		        //IF PLAYER X IS ALREADY IN-GAME, PLAYER O CAN ENTER THE GAME
		        .catch(err => console.log(err))	
		        } else if (checkPlayerOne === "o" || checkPlayerOne === "O"){
		        	var player = "O";
					sessionStorage.setItem("player", player);
					sessionStorage.setItem("gamekey", txtGameID.value);
					if (getPlayerId == null || getPlayerId == "") {
						sessionStorage.setItem("playerId", playerRandom);
					} else {
						sessionStorage.getItem("playerId");
					}
					sessionStorage.setItem("gameRound", 1);
					sessionStorage.setItem("uuid", chosenUuid); 
					window.location.href = "game.html";
			    } else {
			    	alert("Sorry! This Game ID does not exist.");
			    }
	        })
	        .catch(err => console.log(err))	      
        	}
        })
        .catch(err => console.log(err))
    });

    //METHOD FOR SPECTATING
    function spectateGame() {
  		let text = "Sorry! This room is full.\nDo you want to spectate?";
  		if (confirm(text) == true) {
    		sessionStorage.setItem("player", "spectator");
			sessionStorage.setItem("gamekey", txtGameID.value);
			window.location.href = "game.html";
  		} 
	}
});