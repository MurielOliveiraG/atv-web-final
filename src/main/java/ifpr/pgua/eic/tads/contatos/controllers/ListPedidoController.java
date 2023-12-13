package ifpr.pgua.eic.tads.contatos.controllers;

import com.github.hugoperlin.results.Resultado;
import ifpr.pgua.eic.tads.contatos.model.entities.Pedido;
import ifpr.pgua.eic.tads.contatos.model.repositories.PedidoRepository;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListPedidoController {

    private PedidoRepository pedidoRepository;

    public ListPedidoController(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Handler get = (Context ctx) -> {
        Resultado<List<Pedido>> resultado = pedidoRepository.listarTodos();

        Map<String,Object> model = new HashMap<>();

        model.put("resultado", resultado);
        if (resultado.foiSucesso()) {
            model.put("pedidos", resultado.comoSucesso().getObj());
        }

        ctx.render("templates/listPedido.peb", model);
    };

    public Handler getApi = (Context ctx) ->{

        // Create a data object to send to the template
        Resultado<List<Pedido>> resultado = pedidoRepository.listarTodos();
        List<Pedido> pedidos = resultado.comoSucesso().getObj();

        // Set the response content type to JSON
        ctx.contentType("application/json");

        // Return the data as JSON
        ctx.json(pedidos);
    };

}
