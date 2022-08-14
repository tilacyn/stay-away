function getGameId() {
    let strings = document.location.href.split('/');
    return strings[strings.length - 1];
}

function startGame() {
    const xhr = new XMLHttpRequest();
    let gameId = getGameId();
    xhr.open('POST', '/v1/game/' + gameId + '/start', false);
    xhr.send();
    if (xhr.status !== 201) {
        alert('failed to start game: ' + xhr.response);
        console.log('failed to start game: ' + xhr.response);
    } else {
        console.log('started game: ' + xhr.response);
        window.open(xhr.getResponseHeader('Location'));
    }
}

