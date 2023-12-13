package ifpr.pgua.eic.tads.contatos.controllers;

import com.github.hugoperlin.results.Resultado;
import ifpr.pgua.eic.tads.contatos.model.entities.Bebida;
import ifpr.pgua.eic.tads.contatos.model.entities.Lanche;
import ifpr.pgua.eic.tads.contatos.model.entities.Pedido;
import ifpr.pgua.eic.tads.contatos.model.repositories.BebidaRepository;
import ifpr.pgua.eic.tads.contatos.model.repositories.LancheRepository;
import ifpr.pgua.eic.tads.contatos.model.repositories.PedidoRepository;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddPedidoController {

    private PedidoRepository pedidoRepository;
    private BebidaRepository bebidaRepository;
    private LancheRepository lancheRepository;

    public AddPedidoController(PedidoRepository pedidoRepository, BebidaRepository bebidaRepository, LancheRepository lancheRepository) {
        this.pedidoRepository = pedidoRepository;
        this.bebidaRepository = bebidaRepository;
        this.lancheRepository = lancheRepository;
    }

    public Handler get = (Context ctx) -> {
        Map<String, Object> model = new HashMap<>();

        Resultado<List<Bebida>> resultado1 = bebidaRepository.listarTodos();
        Resultado<List<Lanche>> resultado2 = lancheRepository.listarTodos();

        if (resultado1.foiSucesso() && resultado2.foiSucesso()) {
            model.put("bebidas", resultado1.comoSucesso().getObj());
            model.put("lanches", resultado2.comoSucesso().getObj());
        }
        ctx.render("templates/addPedido.peb", model);
    };

    public Handler post = (Context ctx) -> {
        String observacao = ctx.formParam("observacao");
        String idBebida = ctx.formParam("bebida");
        String idLanche = ctx.formParam("lanche");

        Resultado <Bebida> resultadoBebida = bebidaRepository.getById(Integer.valueOf(idBebida));
        Resultado <Lanche> resultadoLanche = lancheRepository.getById(Integer.valueOf(idLanche));

        Bebida bebida = resultadoBebida.comoSucesso().getObj();
        Lanche lanche = resultadoLanche.comoSucesso().getObj();
        Resultado<Pedido> resultado = pedidoRepository.cadastrar(bebida,lanche,observacao);

        Map<String,Object> model = new HashMap<>();
        model.put("resultado", resultado);
        if(resultado.foiErro()){
            model.put("observacao",observacao);

            model.put("idBebida", Integer.valueOf(idBebida));
            Resultado<List<Bebida>> ret = bebidaRepository.listarTodos();
            model.put("bebidas", ret.comoSucesso().getObj());
            
            model.put("idLanche",Integer.valueOf(idLanche));
            Resultado<List<Lanche>> ret2 = lancheRepository.listarTodos();
            model.put("lanches", ret2.comoSucesso().getObj());
            ctx.render("templates/addPedido.peb", model);
        }
        ctx.redirect("/addPedido");
        
    };
}
