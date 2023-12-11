function getLanches() {
    var requestOptions = {
        method: 'GET',
        redirect: 'follow',
        Origin: 'https://localhost:8080',
        'Content-Type': 'application/json'
    };

    fetch("http://localhost:8080/api/lanches", requestOptions)
        .then(response => response.json())
        .then(data => {
            const container = document.getElementById('lanches');
            container.innerHTML = '';
            data.forEach(item => {
                const card = document.createElement('div');
                card.classList.add('card');

                
                card.innerHTML = `
                    <h3>${item.nome}</h3>
                    <p>${item.valor}</p>
                    <button onclick="deleteItem(${item.id})">Delete</button>`;

                container.appendChild(card);
            });
        })
        .catch(error => console.log('error', error));
}

function deleteItem(itemId) {
    var requestOptions = {
        method: 'DELETE',
        redirect: 'follow',
        Origin: 'https://localhost:8080',
        'Content-Type': 'application/json',
        body: JSON.stringify({ id: itemId })
    };

    fetch(`http://localhost:8080/api/lanche`, requestOptions)
        .then(response => {
            if (response.ok) {
                getLanches();
            } else {
                console.log('Delete request failed.');
            }
        })
        .catch(error => console.log('error', error));
}