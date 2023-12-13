package ifpr.pgua.eic.tads.contatos.model.repositories;

import com.github.hugoperlin.results.Resultado;
import ifpr.pgua.eic.tads.contatos.model.daos.BebidaDAO;
import ifpr.pgua.eic.tads.contatos.model.daos.LancheDAO;
import ifpr.pgua.eic.tads.contatos.model.daos.PedidoDAO;
import ifpr.pgua.eic.tads.contatos.model.entities.Bebida;
import ifpr.pgua.eic.tads.contatos.model.entities.Lanche;
import ifpr.pgua.eic.tads.contatos.model.entities.Pedido;

import java.util.List;

public class ImplPedidoRepository implements PedidoRepository{

    private PedidoDAO dao;
    private LancheDAO lancheDao;
    private BebidaDAO bebidaDao;

    public ImplPedidoRepository(PedidoDAO dao, LancheDAO lancheDao, BebidaDAO bebidaDao) {
        this.dao = dao;
        this.lancheDao = lancheDao;
        this.bebidaDao = bebidaDao;
    }

    @Override
    public Resultado<Pedido> cadastrar(Bebida bebida, Lanche lanche, String observacao) {
        if (observacao.isBlank() || observacao.isEmpty()) {
            return Resultado.erro("Descrição inválida!");
        }

        

        Pedido pedido = new Pedido(bebida,lanche,observacao);

        return dao.cadastrarPedido(pedido);
    }

 @Override
public Resultado<List<Pedido>> listarTodos() {
    Resultado<List<Pedido>> resultado = dao.listar();

    if (resultado.foiSucesso()) {
        List<Pedido> pedidos = resultado.comoSucesso().getObj();
        for (Pedido pedido : pedidos) {
                Resultado<Bebida> resultado1 = bebidaDao.buscarBebidas(pedido);
                if (resultado1.foiErro()) {
                    return resultado1.comoErro();
                } else {
                    pedido.setBebida(resultado1.comoSucesso().getObj());
                }
                Resultado<Lanche> resultado2 = lancheDao.buscarLanches(pedido);
                if (resultado2.foiErro()) {
                    return resultado2.comoErro();
                } else {
                    pedido.setLanche(resultado2.comoSucesso().getObj());
                }
            }
        }
    return resultado;
    }
}

