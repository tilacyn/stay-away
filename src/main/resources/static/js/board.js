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
    const cards = getMainPlayersCards(board);
    redrawMainPlayersCards(cards);
}

function getPlayerLogins(board) {
    const players = board['players'];
    let playerLogins = [];
    for (let i = 0; i < players.length; i++) {
        playerLogins.push(players[i]['login']);
    }
    return playerLogins;
}

function redrawMainPlayersCards(cards) {
    let cardsTBodyElement = document.querySelector('.main-player-hand');
    cardsTBodyElement.innerHTML = '';
    for (let i = 0; i < cards.length; i++) {
        const cardEl = document.createElement('div');
        cardEl.classList.add('main-player-card');

        const cardName = document.createElement('div');
        cardName.classList.add('main-player-card_title');
        cardName.innerHTML = cards[i];

        cardEl.appendChild(cardName);
        cardsTBodyElement.appendChild(cardEl);
    }
}

function getMainPlayersCards(board) {
    return board['cards'];
}

function redrawPlayers(players) {
    const tableRadius = 250;

    const pi = 3.14159265359;

    const cardsInHandCount = 4;

    let centerMargins = {}

    let tableElement = document.querySelector('.gameTable');

    for (let i = 0; i < players.length; i++) {
        let alpha = 2 * pi * i / players.length;
        let centerTop = tableRadius * Math.cos(alpha);
        let centerLeft = tableRadius * Math.sin(pi - alpha);

        let playerEl = document.createElement('div');
        playerEl.classList.add('player');
        playerEl.style.left = centerLeft + tableRadius - 50 + 'px';
        playerEl.style.top = centerTop + tableRadius - 50 + 'px';

        let playerCard = document.createElement('div');
        playerCard.classList.add('player-card');

        playerCard.innerHTML = '<img src="/user-icon.png" alt="N/A" class="player-card_img">';

        let userName = document.createElement('div');

        userName.classList.add('player-card_name');
        userName.innerHTML = players[i];

        playerCard.appendChild(userName);
        playerEl.appendChild(playerCard);

        let playerHand = document.createElement('div');
        playerHand.classList.add('player-card_hand');
        for (let j = 0; j < cardsInHandCount; j++) {
            let card = document.createElement('div');
            card.classList.add('player-card_hand-i');
            playerHand.appendChild(card);
        }

        playerEl.appendChild(playerHand);
        tableElement.appendChild(playerEl);
    }
}