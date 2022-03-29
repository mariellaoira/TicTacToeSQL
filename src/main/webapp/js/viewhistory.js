
function getCurrentTime(){
    var today = new Date();
    var date = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate();
    var time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
    var dateTime = date+' '+time;
    return dateTime;
}

//CALL THIS WHEN click on the grid
function save_move(gameId, playerId, symbolL, locationN) {

    // create a json
    var json = {
        gameid:gameId,
        playerid:playerId,
        symbol:symbolL,
        location:locationN,
    };
    
    // send to server
    fetch('http://localhost:8145/save_service/rest/test/save', {
        method: 'post',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(json)  
    })
    .then(function (res) {
        return res.json()
    })
    .then(function (result) {
        fetchHistory(gameid)
    })
    .catch (function (error) {
        // console.log('Request failed', error);
    });
}

function fetchHistory(gameId){
    fetch('http://localhost:8145/save_service/rest/test/getgame/' + gameId, {
        method: 'get',
        mode:'cors'
    })
    .then(function (res) {
        // console.log(res) 
        return res.json()
    })
    .then(function (res) {

        let data = res.list.sort((a,b)=>{
            return a.datesaved > b.datesaved
        })
        console.log(data)
    })
    .catch (function (error) {
        console.log('Request failed', error);
    });
}

// click show history button
function showHistory(){
    playerId = document.getElementById('txtPlayerId').value

    infoContainer = document.getElementById('infoContainer')

    infoContainer.classList.add("hide")

    console.log("show histories for " + playerId)

    fetch('http://localhost:8145/save_service/rest/test/listgames/' + playerId, {
        method: 'get',
        headers: {'Content-Type': 'application/json'},
        mode:'cors'
    })
    .then(function (res) {
        // console.log(res) 
        return res.json()
    })
    .then(function (res) {

        console.log(res)
        let data = res.list.filter((value, index, self) =>
        index === self.findIndex((t) => (
          t.id === value.id))
        )
        console.log(data)

        historyContainer = document.getElementById('historyContainer')
        gameListContainer = document.getElementById('gameListContainer')
        historyContainer.classList.remove('hide')

        gameListContainer.innerHTML  = `<h2> Games  of ${playerId} </h2>`

        data.forEach(e=>{
            gameListContainer.innerHTML +=  `<div onclick="loadGame('${e.id}')"> ${e.id} </div>`;
        })

    })
    .catch (function (error) {
        console.log('Request failed', error);
    });
}

// click on game id button
function loadGame(gameId){
    fetch('http://localhost:8145/save_service/rest/test/getgame/' + gameId, {
        method: 'get',
        mode:'cors'
    })
    .then(function (res) {
        // console.log(res) 
        return res.json()
    })
    .then(function (res) {

        let data = res.list.sort((a,b)=>{
            return a.datesaved > b.datesaved
        })
        console.log(data)
        logs = document.getElementById('logs')
        logs.classList.remove('hide')
        logs.innerHTML = ``

        time = 2000

        //reset
        for(let i = 0; i < 9;i++){
            document.getElementById(i).innerHTML = ''
        }

        data.forEach(e=>{

            setTimeout(function(){
                if(e.symbol == 'X'){
                    document.getElementById(e.location).innerHTML = `<span style="color:#765">${e.symbol} </span`
                }else{
                    document.getElementById(e.location).innerHTML = `<span style="color:#465">${e.symbol} </span`
                }
            },time)
            time += 2000
            logs.innerHTML +=  `<div> ${ JSON.stringify(e)} </div>`

        })  
    })
    .catch (function (error) {
        console.log('Request failed', error);
    });
}