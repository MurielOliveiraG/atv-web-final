package ifpr.pgua.eic.tads.contatos.controllers;

import com.github.hugoperlin.results.Resultado;
import ifpr.pgua.eic.tads.contatos.model.entities.Bebida;
import ifpr.pgua.eic.tads.contatos.model.repositories.BebidaRepository;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListBebidaController {

    private BebidaRepository bebidaRepository;

    public ListBebidaController(BebidaRepository bebidaRepository) {
        this.bebidaRepository = bebidaRepository;
    }

    public Handler get =(Context ctx) ->{
        Resultado< List<Bebida>> resultado = bebidaRepository.listarTodos();

        Map<String, Object> model = new HashMap<>();

        model.put("resultado", resultado);
        if(resultado.foiSucesso()){
            model.put("bebidas", resultado.comoSucesso().getObj());

            ctx.render("templates/listBebida.peb", model);
        }
    };
}
