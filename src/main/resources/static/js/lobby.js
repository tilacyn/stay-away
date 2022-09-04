function createGame() {
    const xhr = new XMLHttpRequest();
    xhr.open('POST', '/v1/create', false);
    xhr.setRequestHeader('Content-Type', 'application/json');
    const body = `{
        "name": "Game Created in UI"
    }`;
    xhr.send(body);

    if (xhr.status !== 201) {
        alert('failed to create game: ' + xhr.response);
        console.log('failed to create game: ' + xhr.response);
    } else {
        console.log('created game: ' + xhr.response);
        window.open(xhr.getResponseHeader('Location'));
    }
}

function openGame(e) {
    const gameId = e.children[0].textContent;
    const status = e.children[2].textContent;
    if (status === 'PREGAME') {
        window.open('/pregame/' + gameId);
    } else if (status === 'RUNNING') {
        window.open('/game/' + gameId);
    } else {
        console.log('status: ' + status);
        alert('not implemented yet');
    }
}

