refresh();

var stompClient = null;

connect()

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/game/' + getGameId(), function (greeting) {
            refresh();
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}


function drawCardFromDeck() {
    const xhr = new XMLHttpRequest();
    const gameId = getGameId();
    xhr.open('POST', '/v1/game/' + gameId + '/draw', false);
    xhr.send();

    if (xhr.status !== 200) {
        alert('failed to draw: ' + xhr.response);
        console.log('failed to draw: ' + xhr.response);
    }
}

function getGameId() {
    let strings = document.location.href.split('/');
    return strings[strings.length - 1];
}


function refresh() {
    const xhr = new XMLHttpRequest();
    const gameId = getGameId();
    xhr.responseType = 'json';
    xhr.open('GET', '/v1/game/' + gameId + '/board', true);
    xhr.onload = function() {
        if (xhr.status !== 200) {
            alert('failed to get board: ' + xhr.response);
            console.log('failed to get board: ' + xhr.response);
            return;
        }
        const board = xhr.response;
        redraw(board);
    }

    xhr.send(null);
}

function redraw(board) {
    const playerLogins = getPlayerLogins(board);
    redrawPlayers(playerLogins);
    const cards = getMainPlayerCards(board);
    redrawMainPlayerCards(cards);
}

function getPlayerLogins(board) {
    const players = board['players'];
    let playerLogins = [];
    for (i = 0; i < players.length; i++) {
        playerLogins.push(players[i]['login']);
    }
    return playerLogins;
}

function redrawMainPlayerCards(cards) {
    cardsTBodyElement = document.getElementsByClassName('mainPlayerHandTBody')[0];
    cardsTBodyElement.innerHTML = '';
    let trElement = document.createElement('tr');
    for (i = 0; i < cards.length; i++) {
        trElement.innerHTML = trElement.innerHTML + '<td class="mainPlayerCard"><img src="/none.png" alt="' + cards[i] + '" class="mainPlayerCard"></td>'
    }
    cardsTBodyElement.appendChild(trElement);
}

function getMainPlayerCards(board) {
    return board['cards'];
}

function redrawPlayers(players) {
    const tableRadius = 300;

    const pi = 3.14159265359

    let centerMargins = {}

    tableElement = document.getElementsByClassName('gameTable')[0];

    for (let i = 0; i < players.length; i++) {
        let alpha = 2 * pi * i / players.length;
        let centerTop = tableRadius * Math.cos(alpha);
        let centerLeft = tableRadius * Math.sin(pi - alpha);

        let userIcon = document.createElement('div');

        userIcon.innerHTML = '<img src="/user-icon.png" alt="N/A" style="width:100px;height:100px;">';
        userIcon.style.position = 'absolute';
        userIcon.style.left = centerLeft + tableRadius - 50 + 'px';
        userIcon.style.top = centerTop + tableRadius - 50 + 'px';

        let userLogin = document.createElement('div');

        userLogin.innerHTML = players[i];
        userLogin.style.color = 'white';
        userLogin.style['text-align'] = 'center';

        userIcon.appendChild(userLogin);

        tableElement.appendChild(userIcon);
    }

}