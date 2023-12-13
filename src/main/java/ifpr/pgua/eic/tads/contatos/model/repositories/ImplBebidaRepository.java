package ifpr.pgua.eic.tads.contatos.model.repositories;

import com.github.hugoperlin.results.Resultado;
import ifpr.pgua.eic.tads.contatos.model.daos.BebidaDAO;
import ifpr.pgua.eic.tads.contatos.model.entities.Bebida;

import java.util.ArrayList;
import java.util.List;

public class ImplBebidaRepository implements BebidaRepository {

    private BebidaDAO dao;
    private List<Bebida> bebidas;

    public ImplBebidaRepository(BebidaDAO dao) {
        this.dao = dao;
        this.bebidas = new ArrayList<>();
    }

    @Override
    public Resultado<Bebida> cadastrar(String nome, double valor) {
        Bebida bebida = new Bebida(nome,valor);

        return dao.cadastrarBebida(bebida);
    }

    @Override
    public Resultado<List<Bebida>> listarTodos() {
        var resultado = dao.listar();

        if(resultado.foiSucesso()){
            bebidas.clear();
            bebidas.addAll(resultado.comoSucesso().getObj());
        }
        return dao.listar();
    }

    @Override
    public Resultado<Bebida> getById(int id) {
        if(bebidas.size() != 0){
            Bebida ret = bebidas.stream().filter(bebida->bebida.getId()==id).findFirst().get();
            return Resultado.sucesso("Bebida encontrada", ret);
        }
        return Resultado.erro("Problema ao buscar categoria!");
    }
}
