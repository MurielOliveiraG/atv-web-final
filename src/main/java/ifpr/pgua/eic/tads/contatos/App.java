package ifpr.pgua.eic.tads.contatos;

import ifpr.pgua.eic.tads.contatos.controllers.*;
import ifpr.pgua.eic.tads.contatos.model.daos.*;
import ifpr.pgua.eic.tads.contatos.model.entities.FabricaConexoes;
import ifpr.pgua.eic.tads.contatos.model.repositories.*;
import ifpr.pgua.eic.tads.contatos.utils.JavalinUtils;
import io.javalin.Javalin;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Javalin app = JavalinUtils.makeApp(8080);

        BebidaDAO bebidaDao = new JDBCBebidaDAO(FabricaConexoes.getInstance());
        BebidaRepository bebidaRepository = new ImplBebidaRepository(bebidaDao);

        LancheDAO lancheDao = new JDBCLancheDAO(FabricaConexoes.getInstance());
        LancheRepository lancheRepository = new ImplLancheRepository(lancheDao);

        PedidoDAO pedidoDao = new JDBCPedidoDAO(FabricaConexoes.getInstance());
        PedidoRepository pedidoRepository = new ImplPedidoRepository(pedidoDao,lancheDao,bebidaDao);

        IndexController indexController = new IndexController();

        AddBebidaController addBebidaController = new AddBebidaController(bebidaRepository);
        ListBebidaController listBebidaController = new ListBebidaController(bebidaRepository);

        AddLancheController addLancheController = new AddLancheController(lancheRepository);
        ListLancheController listLancheController = new ListLancheController(lancheRepository);

        AddPedidoController addPedidoController = new AddPedidoController(pedidoRepository,bebidaRepository,lancheRepository);
        ListPedidoController listPedidoController = new ListPedidoController(pedidoRepository);

        //app.get("/", indexController.get);

        app.get("/", indexController.getBiteBlitz);
        app.get("/src/main/resources/public/html/addLanche.html", indexController.getFormularioBiteBlitz);

        app.get("/addBebida",addBebidaController.get);
        app.post("/addBebida",addBebidaController.post);
        app.get("/listBebida",listBebidaController.get);

        app.get("/addLanche", addLancheController.get);
        app.post("/addLanche", addLancheController.post);
        app.post("/api/lanche", addLancheController.postApi);
        app.get("/listLanche",listLancheController.get);

        app.delete("/api/lanche", addLancheController.deleteApi);

        app.get("/addPedido",addPedidoController.get);
        app.post("/addPedido",addPedidoController.post);
        app.get("/listPedido",listPedidoController.get);
        app.get("/api/pedidos",listPedidoController.getApi);
        app.get("/api/lanches",listLancheController.getApi);
    }
}
