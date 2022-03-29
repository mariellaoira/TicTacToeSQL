var randomize = Math.random().toString(36).substr(2, 8); //GENERATES RANDOM LETTERS AND NUMBERS (FOR GAME ID)
var playerRandom = Math.random().toString(36).substr(2, 8);
var uuidRandom = Math.random().toString(36).substr(2, 8);
var chosenPlayerId = playerRandom;
var chosenId = randomize;
var chosenUuid = uuidRandom;
var btnNew = document.getElementById("btnNew");
var btnJoin = document.getElementById("btnJoin");
var btnSpectate = document.getElementById("btnSpectate");

document.addEventListener('DOMContentLoaded', function(event) {
    
    btnNew.addEventListener("click", function() {
        var player = "X";
        sessionStorage.setItem("player", player);
        sessionStorage.setItem("gamekey", chosenId);
        sessionStorage.setItem("playerId", playerRandom);
        sessionStorage.setItem("gameRound", 1);
        sessionStorage.setItem("uuid", uuidRandom);
        createNewGame();
        window.location.href = "game.html";
    });

    btnJoin.addEventListener("click", function() {
        window.location.href = "joingame.html";    
    });

    btnViewHistory.addEventListener("click", function() {
        window.location.href = "viewhistory.html";    
    });

});

//CREATE NEW GAME (PLAYER X)
var url = "http://localhost:8080/tictactoe/tictactoeserver/createGame?key=" + chosenId;
function createNewGame(){
    fetch(url)
    .then( response => {
        console.log(response);
    } );
}
