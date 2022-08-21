const players = ['tilacyn', 'lera', 'gaidds', 'Kowalski1337', 'hey_boris', 'gbarto', 'kongosha']

const tableRadius = 300;

const pi = 3.14159265359

let centerMargins = {}

tableElement = document.getElementsByClassName('gameTable')[0];

for (let i = 0; i < players.length; i++) {
    let alpha = 2 * pi * i / players.length;
    let centerTop = tableRadius * Math.cos(alpha);
    let centerLeft = tableRadius * Math.sin(pi - alpha);

    let userIcon = document.createElement('div');

    userIcon.innerHTML = '<img src="user-icon.png" alt="N/A" style="width:100px;height:100px;">';
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
