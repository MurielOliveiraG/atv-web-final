function addLanche(){
    const nome = document.getElementById('nome');
    const valor = document.getElementById('valor');
    var lanche = {
        nome:nome.value,
        valor:valor.valueAsNumber
    }

    var requisicao = {
      method: 'POST',
      'Content-Type': 'application/json',
      body: JSON.stringify(lanche),
      redirect: 'follow'
    };
    
    fetch("http://localhost:8080/api/lanche", requisicao)
      .then(response => response.text())
      .then(result => console.log(result))
      .catch(error => console.log('error', error));
      alert("Cadastrado com sucesso!")
      return false;
}

function getLanche() {
    var requisicao = {
        method: 'GET',
        redirect: 'follow',
        Origin: 'https://localhost:8080',
        'Content-Type': 'application/json'
    };

    fetch("http://localhost:8080/api/lanches", requisicao)
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
                    <button onclick="deleteLanche(${item.id})">Delete</button>`;

                container.appendChild(card);
            });
        })
        .catch(error => console.log('error', error));
}

function deleteLanche(itemId) {
    var requisicao = {
        method: 'DELETE',
        redirect: 'follow',
        Origin: 'https://localhost:8080',
        'Content-Type': 'application/json',
        body: JSON.stringify({ id: itemId })
    };

    fetch(`http://localhost:8080/api/lanche`, requisicao)
        .then(response => {
            if (response.ok) {
                getLanche();
            } else {
                console.log('Delete request failed.');
            }
        })
        .catch(error => console.log('error', error));
        alert("Deletado com sucesso!")
}
