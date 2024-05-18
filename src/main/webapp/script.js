// ==============================================================
// 		EVENTOS

// RESET
reset = function() {
    // Aqui você cria uma requisição AJAX POST a ControllerServlet
    // Você repassa, com a chave 'op' o parâmetro 'RESET'
    // Se a requisição for bem sucedida, você executa:
    // atualizaSessao() e window.location.href = "/prova1".
    // Se não for bem sucedida, decida o que fazer.
    
    
    
    //envia uma requisiçaão jaxa para reiniciar os dados no servidor, aparentemente?
    let request = new XMLHttpRequest(); //nova variavel
    request.open("POST", "ControllerServlet", true); //setar a var como post
    
    // Define o cabeçalho da requisição para "Content-Type: application/x-www-form-urlencoded",
	// indicando que os dados do formulário serão enviados no corpo da requisição HTTP
	// como pares de chave/valor codificados, semelhante ao formato de uma URL de query string.
    request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    
    
    request.onreadystatechange = () => { 
        if (request.readyState == 4 && request.status == 200) {
            atualizaSessao();
            window.location.href = "/prova1";
        } else if (request.readyState == 4) {
            // O QUE FAZER SE DEU ERRADO
            window.location.href = "/error.html";
        }
    }
    request.send("op=RESET");
}

// NOVA AULA
novaAula = function() {
    window.location.href = "nova";
}

// CANCELA NOVA AULA (OU EDIÇÃO)
cancelarNovaAula = function() {
    window.location.href = "/prova1";
}

// EDITA UMA AULA COM ID ESPECIFICADO
editarAula = function(id) {
    window.location.href = 'edit?id=' + id;
}

// ENVIA CONTEÚDO DA NOVA AULA
enviarNovaAula = function() {
    // obtém os valores a partir do formulário
    let data = document.getElementById('data-id').value;
    let horario = document.getElementById('hora-id').value;
    let duracao = document.getElementById('dur-id').value;
    let codDisciplina = document.getElementById('disc-id').value;
    let assunto = document.getElementById('ass-id').value;
    // verificando a validação
    if (!validaNovaAula(data, horario, duracao, codDisciplina, assunto)) {
        document.getElementById('msg-id').style.display = 'block';
        return;
    }
    // Aqui, você faz uma requisição AJAX POST a ControllerServlet e
    // envia a chave 'op' valendo 'CREATE'. Envie, do mesmo modo, os parâmetros
    // data, horario, duracao, codDisciplina e assunto.
    // Se a requisição for bem sucedida, execute atualizaSessao() e
    // window.location.href = "/prova1"
    // Se não for bem sucedida, decida o que fazer
    let request = new XMLHttpRequest();
    request.open("POST", "ControllerServlet", true);
    request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    request.onreadystatechange = () => {
        if (request.readyState == 4 && request.status == 200) {
            atualizaSessao();
            window.location.href = "/prova1";
        } else if (request.readyState == 4) {
            // O QUE FAZER SE DEU ERRADO
            window.location.href = "/error.html";
        }
    }
    request.send("op=CREATE&data=" + data + "&horario=" + horario + "&duracao=" + duracao + "&codDisciplina=" + codDisciplina + "&assunto=" + assunto);
}

// ENVIA CONTEÚDO EM EDIÇÃO
enviarEdit = function() {
    // obtém os valores a partir do formulário
    let id = document.getElementById('id').innerHTML;
    let data = document.getElementById('data-id').value;
    let horario = document.getElementById('hora-id').value;
    let duracao = document.getElementById('dur-id').value;
    let codDisciplina = document.getElementById('disc-id').value;
    let assunto = document.getElementById('ass-id').value;
    // Aqui, você faz uma requisição AJAX POST a ControllerServlet e
    // envia a chave 'op' valendo 'UPDATE'. Envie, do mesmo modo, os parâmetros
    // id, data, horario, duracao, codDisciplina e assunto.
    // Se a requisição for bem sucedida, execute atualizaSessao() e
    // window.location.href = "/prova1"
    // Se não for bem sucedida, decida o que fazer
    let request = new XMLHttpRequest();
    request.open("POST", "ControllerServlet", true);
    request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    request.onreadystatechange = () => {
        if (request.readyState == 4 && request.status == 200) {
            atualizaSessao();
            window.location.href = "/prova1";
        } else if (request.readyState == 4) {
            // O QUE FAZER SE DEU ERRADO
            window.location.href = "/error.html";
        }
    }
    request.send("op=UPDATE&id=" + id + "&data=" + data + "&horario=" + horario + "&duracao=" + duracao + "&codDisciplina=" + codDisciplina + "&assunto=" + assunto);
}

// DELETA UMA AULA
deleta = function(id) {
    // Aqui, você faz uma requisição AJAX POST a ControllerServlet e
    // envia a chave 'op' valendo 'DELETE'. Envie, do mesmo modo, o parâmetro id
    // Se a requisição for bem sucedida, execute atualizaSessao() e
    // window.location.href = "/prova1"
    // Se não for bem sucedida, decida o que fazer
    let request = new XMLHttpRequest();
    request.open("POST", "ControllerServlet", true);
    request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    request.onreadystatechange = () => {
        if (request.readyState == 4 && request.status == 200) {
            atualizaSessao();
            window.location.href = "/prova1";
        } else if (request.readyState == 4) {
            // O QUE FAZER SE DEU ERRADO
            window.location.href = "/error.html";
        }
    }
    request.send("op=DELETE&id=" + id);
}

const atualizaSessao = function() {
    let request = new XMLHttpRequest();
    request.open("POST", "ControllerServlet", true);
    request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    request.onreadystatechange = () => {
        if (request.readyState == 4 && request.status == 200) {
            // O QUE FAZER SE DEU CERTO
        } else if (request.readyState == 4) {
            // O QUE FAZER SE DEU ERRADO
            window.location.href = "/error.html";
        }
    }
    request.send("op=START_SESSION");
}

// ============================================================
// 			VALIDAÇÕES

validaNovaAula = function(data, horario, duracao, codDisciplina, assunto) {
    // Examine os valores dos parâmetros deste método e decida se estão
    // ou não validados. Este 'return true' provavelmente será alterado, não?
    // Exemplo de validação simples:
    if (data === "" || horario === "" || duracao === "" || codDisciplina === "" || assunto === "") {
        return false;
    }
    
    // Verifica se o horário está dentro do intervalo válido (0-24 horas)
    if (horario < 0 || horario > 24) {
        return false;
    }

    // Verifica se a duração é um número positivo
    if (duracao <= 0) {
        return false;
    }

    // Verifica se o ano da data está entre 2000 e 2099
    let dataObj = new Date(data);
    let ano = dataObj.getFullYear();
    if (ano < 2000 || ano > 2099) {
        return false;
    }
    return true;
}

// ===================================================================================
// 		INICIALIZA O PROCESSAMENTO

atualizaSessao();
