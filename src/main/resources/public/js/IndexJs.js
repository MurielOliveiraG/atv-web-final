function addLanche(){
    const nome = document.getElementById('nome');
    const valor = document.getElementById('valor');
    var lanche = {
        nome:nome.value,
        valor:valor.valueAsNumber
    }

    var requisicao = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(lanche),
      redirect: 'follow'
    };
   
    fetch("http://localhost:8080/api/lanche", requisicao)
    .then(resposta => resposta.text())
    .then(resultado => {
        console.log(resultado);
        alert("Cadastrado com sucesso!");
    })
    .catch(erro => {
        console.log('erro', erro);
        alert("Erro ao cadastrar.");
    });

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
        .then(resposta => resposta.json())
        .then(data => {
            const container = document.getElementById('lanches');
            container.innerHTML = '';
            data.forEach(item => {
                const card = document.createElement('div');
                card.classList.add('card');


                card.innerHTML = `
                    <h3>${item.nome}</h3>
                    <p>R$ ${item.valor}</p>
                    <button onclick="deleteLanche(${item.id})">Delete</button>`;

                container.appendChild(card);
            });
        })
        .catch(erro => console.log('erro', erro));
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
        .then(resposta => {
            if (resposta.ok) {
                getLanche();
            } else {
                console.log('Erro ao deletar.');
            }
        })
        .catch(erro => console.log('erro', erro));
        alert("Deletado com sucesso!")
}
