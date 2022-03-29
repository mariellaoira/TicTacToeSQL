var keyId = sessionStorage.getItem("gamekey");
var player = sessionStorage.getItem("player");
var playerId = sessionStorage.getItem("playerId");
var uuid = sessionStorage.getItem("uuid");
var currentPlayer = "X";
var divGrid = document.getElementById("grid");
var btnExit = document.getElementById("btnExit");
var lblGameId = document.getElementById("gameId");
var lblPlayerId = document.getElementById("playerId");
var lblUuid = document.getElementById("uuid");
var playerDisplay = document.getElementById("playerDisplay");

//DivPlayAgain
var divPlayAgain = document.getElementById("divPlayAgain");
var btnNewGame = document.getElementById("btnNewGame");
var btnJoinGame = document.getElementById("btnJoinGame");
var btnExitThis = document.getElementById("btnExitThis");

document.addEventListener('DOMContentLoaded', function(event) {
    divPlayAgain.classList.add("hide");
    lblGameId.innerText = "Game ID: " + keyId;
    lblPlayerId.innerText = "Player ID: " + playerId;
    lblUuid.innerText = "Unique Game Key: " + uuid;
    checkPlayerTwo();  
    checkPlayerTurn();
});


//METHOD FOR CHECKING OPPONENT OR PLAYER O
function checkPlayerTwo() {
        var checkPlayerTwo = "http://localhost:8080/tictactoe/tictactoeserver/check?key=" + keyId;
        var check;
        fetch(checkPlayerTwo)
        .then(response => response.text())
        .then((response) => {
        check = response;
        if (check === "false") {
            alert("Don't start! You don't have an opponent yet. Invite a friend using your unique Game ID.");
            divGrid.style.pointerEvents = 'none';
        }
        })
        .catch(err => console.log(err))
}

//METHOD FOR DISABLING PLAYERS' GRID IF IT IS NOT THEIR TURN
function checkPlayerTurn() {
    var currentTurn = playerDisplay.textContent;
    console.log("Current Player: " +  currentTurn)
    if (player != currentTurn) {
        divGrid.classList.add("disable");
    }
    if(player == currentTurn) {
        divGrid.style = "";
        if (divGrid.classList.contains("disable")) {
            divGrid.classList.remove("disable");
        }
    }
}

//METHOD FOR RESETTING THE GAME ID
function resetAPI() {
    var resetPlayerOneURL = "http://localhost:8080/tictactoe/tictactoeserver/reset?key=" + keyId;
        fetch(resetPlayerOneURL)
        .then(response => response.text())
        .then((response) => {
            console.log(response)
            newGame(); //for X
            newGame(); //for O
        })
        .catch(err => console.log(err)) 
}

//METHOD FOR PLAYING AGAIN
function newGame() {
    var newGame = "http://localhost:8080/tictactoe/tictactoeserver/createGame?key=" + keyId;
    fetch(newGame)
    .then( response => {
        console.log(response);
    } );
}

window.addEventListener('DOMContentLoaded', () => {
    const tiles = Array.from(document.querySelectorAll('.tile'));
    const resetButton = document.querySelector('#reset');
    const announcer = document.querySelector('.announcer');
    let board = ['', '', '', '', '', '', '', '', ''];
    let isGameActive = true;
    let turn = true;
    const PLAYERX_WON = 'PLAYERX_WON';
    const PLAYERO_WON = 'PLAYERO_WON';
    const TIE = 'TIE';

    /*
        Indexes within the board
        [0] [1] [2]
        [3] [4] [5]
        [6] [7] [8]
    */

    const winningConditions = [
        [0, 1, 2],
        [3, 4, 5],
        [6, 7, 8],
        [0, 3, 6],
        [1, 4, 7],
        [2, 5, 8],
        [0, 4, 8],
        [2, 4, 6]
    ];

    //USED FOR REAL-TIME BOARD UPDATE FOR ALL PLAYERS AND SPECTATORS :)
    setInterval(loadBoard, 500);
    setInterval(checkPlayerTurn, 500);

    //METHOD FOR CHECKING VIEWERS ^_^
    if (player === "spectator") {
        divGrid.classList.add("disable");
        btnExit.removeEventListener("click", exitBoard);
        btnExit.addEventListener('click', exitSpectating);
        resetButton.classList.add('hide');
    } else {
        btnExit.addEventListener('click', exitBoard);
        resetButton.classList.add('hide');
    }

    //METHOD FOR CHECKING FOR WINNER
    function handleResultValidation() {
        let roundWon = false;
        for (let i = 0; i <= 7; i++) {
            const winCondition = winningConditions[i];
            const a = board[winCondition[0]];
            const b = board[winCondition[1]];
            const c = board[winCondition[2]];
            if (a === '' || b === '' || c === '') {
                continue;
            }
            if (a === b && b === c) {
                roundWon = true;
                break;
            }
        }

        if (roundWon) {
            announce(currentPlayer === 'X' ? PLAYERO_WON : PLAYERX_WON);
            isGameActive = false;
            return;
        } 
        if (!board.includes('')) {
            announce(TIE);
            if (divGrid.classList.contains("hide")) {
                divGrid.classList.remove("hide");
            }
        }  
    }

    //DISPLAY WINNING PLAYER USING A LABEL
    const announce = (type) => {
        switch(type){
            case PLAYERO_WON:
                announcer.innerHTML = 'Player <span class="playerO">O</span> Won';
                break;
            case PLAYERX_WON:
                announcer.innerHTML = 'Player <span class="playerX">X</span> Won';
                break;
            case TIE:
                announcer.innerText = 'Tie';
        }
        announcer.classList.remove('hide');
        if (divPlayAgain.classList.contains("hide")) {
            divPlayAgain.classList.remove("hide");
            console.log("Play Again?");
        }
    };

    //CHECK WHETHER PLAYER'S CHOSEN TILE IS ALREADY TAKEN OR NOT
    const isValidAction = (tile) => {
        if (tile.innerText === 'X' || tile.innerText === 'O'){
            return false;
        }
        return true;
    };

    //UPDATE BOARD FOR REAL-TIME PURPOSES
    const updateBoard =  (index) => {
        board[index] = currentPlayer;
    }

    //CHECK FOR PLAYER'S TURN
    const changePlayer = () => {
        playerDisplay.classList.remove(`player${currentPlayer}`);
        currentPlayer = currentPlayer === 'X' ? 'O' : 'X';
        playerDisplay.innerText = currentPlayer;
        playerDisplay.classList.add(`player${currentPlayer}`);
    }

    //POST PLAYER'S MOVE TO API
    function postPlayerMove(keyId, currentPlayer, yPos, xPos) {
        var moveURL = "http://localhost:8080/tictactoe/tictactoeserver/move?key=" + keyId + "&tile=" + currentPlayer + "&y=" + yPos + "&x=" + xPos;
            fetch(moveURL)
            .then(response => response.text())
            .then((response) => {
            console.log(response)
            })
            .catch(err => console.log(err))
    }

    //POST PLAYER'S MOVE TO API
    const userAction = (tile, index) => {
        if(isValidAction(tile) && isGameActive) {
            tile.innerText = currentPlayer;
            tile.classList.add(`player${currentPlayer}`);

            switch (index) {
                case 0:
                    //console.log('Player ' + currentPlayer + ' marked Y Position: 0, X Position: 0');
                    postPlayerMove(keyId, currentPlayer, "0", "0");
                    break;
                case 1:
                    //console.log('Player ' + currentPlayer + ' marked Y Position: 0, X Position: 1');
                    postPlayerMove(keyId, currentPlayer, "0", "1");
                    break;
                case 2:
                    //console.log('Player ' + currentPlayer + ' marked Y Position: 0, X Position: 2');
                    postPlayerMove(keyId, currentPlayer, "0", "2");
                    break;
                case 3:
                    //console.log('Player ' + currentPlayer + ' marked Y Position: 1, X Position: 0');
                    postPlayerMove(keyId, currentPlayer, "1", "0");
                    break;
                case 4:
                    //console.log('Player ' + currentPlayer + ' marked Y Position: 1, X Position: 1');
                    postPlayerMove(keyId, currentPlayer, "1", "1");
                    break;
                case 5:
                    //console.log('Player ' + currentPlayer + ' marked Y Position: 1, X Position: 2');
                    postPlayerMove(keyId, currentPlayer, "1", "2");
                    break;
                case 6:
                    //console.log('Player ' + currentPlayer + ' marked Y Position: 2, X Position: 0');
                    postPlayerMove(keyId, currentPlayer, "2", "0");
                    break;
                case 7:
                    //console.log('Player ' + currentPlayer + ' marked Y Position: 2, X Position: 1');
                    postPlayerMove(keyId, currentPlayer, "2", "1");
                    break;
                case 8:
                    //console.log('Player ' + currentPlayer + ' marked Y Position: 2, X Position: 2');
                    postPlayerMove(keyId, currentPlayer, "2", "2");
                    break;
                default:
                    console.log('Sorry, we are out of ' + index + '.');
            }
            updateBoard(index);
            save_move(keyId,playerId,currentPlayer,index);
            handleResultValidation();
            changePlayer();
        }
    }

    //METHOD FOR UPDATING THE WHOLE PAGE REAL-TIME FOR ALL PLAYERS AND SPECTATOR
    function loadBoard(){
    var loadBoardURL = "http://localhost:8080/tictactoe/tictactoeserver/board?key=" + keyId;
    var boardd;
    fetch(loadBoardURL)
    .then(response => response.text())
    .then((response) => {
        boardd = response;
        var checkBoardText = boardd.search(":");
        if (boardd !== "[GAME NOT YET STARTED]" && boardd !== ":::::::::") {
            var countTurnsX = boardd.split('X').length - 1;
            var countTurnsO = boardd.split('O').length - 1;
            if (countTurnsX > countTurnsO) {
                currentPlayer = 'O';
            } else {
                currentPlayer = 'X';
            }
            playerDisplay.innerText = currentPlayer;
            playerDisplay.classList.add(`player${currentPlayer}`);
        }
        if (checkBoardText != "-1") {
            var splitBoard = boardd.split(":");
            if (splitBoard.length !== null || boardd !== "[GAME NOT YET STARTED]") {
                for (let i = 0; i <= splitBoard.length; i++) {
                    if (splitBoard[i] !== null) {
                        var tile = document.getElementById(i);
                        tile.innerText = splitBoard[i];
                        tile.classList.add(`player${splitBoard[i]}`);
                        isValidAction(tile);
                        refreshBoard(i,splitBoard[i]);
                        handleResultValidation();
                    }
                }
                var countTurnsX = boardd.split('X').length - 1;
                var countTurnsO = boardd.split('O').length - 1;
                if (countTurnsX > countTurnsO) {
                    currentPlayer = 'O';
                } else {
                    currentPlayer = 'X';
                }
                playerDisplay.innerText = currentPlayer;
                playerDisplay.classList.add(`player${currentPlayer}`);     
            } 
            checkPlayerTurn();
        }
    })
    .catch(err => console.log(""))
    }

    //METHOD FOR INSERTING USER'S MOVE INTO THE GRID
    function refreshBoard(index, player){
        board[index] = player;
    }

    //METHOD FOR EXITING THE GAME (PLAYERS)
    function exitBoard() {
        var moveURL = "http://localhost:8080/tictactoe/tictactoeserver/reset?key=" + keyId;
        fetch(moveURL)
        .then(response => response.text())
        .then((response) => {
            alert("Exited the game successfully.");
            window.location.href="index.html";

        })
        .catch(err => console.log(err))
    }

    //METHOD FOR EXITING THE GAME (SPECTATORS)
    function exitSpectating() {
        alert("Exited the game successfully.");
        window.location.href="index.html";
    }

    function newGame() {
        //generate new unique game key
        var gameRandom = Math.random().toString(36).substr(2, 8);
        var player = "X";
        sessionStorage.setItem("player", player);
        sessionStorage.setItem("gamekey", gameRandom);
        createNewGame();
        window.location.href = "game.html"; 
    }
    
    function joinGame() {
        var playerO = sessionStorage.getItem("playerId");
        sessionStorage.setItem("playerId", playerO);
        window.location.href = "joingame.html"; 
    }
    //CREATE NEW GAME (PLAYER X)
    
    function createNewGame(){
        var url = "http://localhost:8080/tictactoe/tictactoeserver/createGame?key=" + sessionStorage.getItem("gamekey");
        fetch(url)
        .then( response => {
            console.log(response);
        } );
    }

    //METHOD FOR RESETTING THE WHOLE PAGE
    const resetBoard = () => {
        board = ['', '', '', '', '', '', '', '', ''];
        isGameActive = true;
        roundWon = false;
        announcer.classList.add('hide');

        if (currentPlayer === 'O') {
            changePlayer();
        }

        tiles.forEach(tile => {
            tile.innerText = '';
            tile.classList.remove('playerX');
            tile.classList.remove('playerO');
        });
        currentPlayer = 'X';
        resetAPI();
    }

    //TILES CLICK
    tiles.forEach( (tile, index) => {
        tile.addEventListener('click', () => userAction(tile, index));
    });

    //RESET BUTTON CLICK
    resetButton.addEventListener('click', resetBoard);

    //NEW GAME BUTTON CLICK
    btnNewGame.addEventListener('click', newGame);

    //JOIN GAME BUTTON CLICK
    btnJoinGame.addEventListener('click', joinGame);

    //EXIT GAME BUTTON CLICK
    btnExitThis.addEventListener('click', exitBoard);
});
