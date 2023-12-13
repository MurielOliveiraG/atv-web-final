package ifpr.pgua.eic.tads.contatos.controllers;

import com.github.hugoperlin.results.Resultado;
import ifpr.pgua.eic.tads.contatos.model.entities.Lanche;
import ifpr.pgua.eic.tads.contatos.model.repositories.LancheRepository;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListLancheController {

    private LancheRepository lancheRepository;

    public ListLancheController(LancheRepository lancheRepository) {
        this.lancheRepository = lancheRepository;
    }

    public Handler get = (Context ctx) ->{
        Resultado<List<Lanche>> resultado = lancheRepository.listarTodos();

        Map<String, Object> model = new HashMap<>();

        model.put("resultado", resultado);
        if(resultado.foiSucesso()){
            model.put("lanches", resultado.comoSucesso().getObj());

            ctx.render("templates/listLanche.peb", model);
        }
    };

    public Handler getApi = (Context ctx) ->{

        // Create a data object to send to the template
        Resultado<List<Lanche>> resultado = lancheRepository.listarTodos();
        List<Lanche> lanches = resultado.comoSucesso().getObj();

        // Set the response content type to JSON
        ctx.contentType("application/json");

        // Return the data as JSON
        ctx.json(lanches);
    };

}
