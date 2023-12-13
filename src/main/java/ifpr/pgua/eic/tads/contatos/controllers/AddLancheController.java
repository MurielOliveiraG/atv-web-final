package ifpr.pgua.eic.tads.contatos.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hugoperlin.results.Resultado;
import ifpr.pgua.eic.tads.contatos.model.entities.Lanche;
import ifpr.pgua.eic.tads.contatos.model.repositories.LancheRepository;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.HashMap;
import java.util.Map;

public class AddLancheController {

    private LancheRepository lancheRepository;

    public AddLancheController(LancheRepository lancheRepository) {
        this.lancheRepository = lancheRepository;
    }

    public Handler get = (Context ctx) ->{
        ctx.render("templates/addLanche.peb");
    };

    public Handler post = (Context ctx) ->{
        String nome = ctx.formParam("nome");
        double valor = Double.parseDouble(ctx.formParam("valor"));

        Resultado<Lanche> resultado = lancheRepository.cadastrar(nome,valor);

        Map<String,Object> model = new HashMap<>();
        model.put("resultado", resultado);
        if (resultado.foiErro()){
            model.put("nome",nome);
            model.put("valor", valor);
        }

        ctx.render("templates/addLanche.peb",model);
    };

    public Handler postApi = (Context ctx) ->{
        // Get the request body as a String
        String requestBody = ctx.body();

        // Use Jackson ObjectMapper to parse the JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(requestBody);

        // Access individual attributes
        String nome = jsonNode.get("nome").asText();
        double valor = jsonNode.get("valor").asDouble();

        Resultado<Lanche> resultado = lancheRepository.cadastrar(nome,valor);

        Map<String,Object> model = new HashMap<>();
        model.put("resultado", resultado);
        if (resultado.foiErro()){
            model.put("nome",nome);
            model.put("valor", valor);
        }

        // Send a response
        ctx.json("Produto salvo com sucesso");
    };

    public Handler deleteApi = (Context ctx) ->{
        String requestBody = ctx.body();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(requestBody);

        int id = jsonNode.get("id").asInt();

        Resultado<Lanche> resultado = lancheRepository.excluir(id);

        Map<String,Object> model = new HashMap<>();
        model.put("resultado", resultado);
        if (resultado.foiErro()){
            model.put("id",id);
        }

        // Send a response
        ctx.json(model);
    };

}