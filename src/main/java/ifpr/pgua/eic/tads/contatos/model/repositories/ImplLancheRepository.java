package ifpr.pgua.eic.tads.contatos.model.repositories;

import com.github.hugoperlin.results.Resultado;
import ifpr.pgua.eic.tads.contatos.model.daos.LancheDAO;
import ifpr.pgua.eic.tads.contatos.model.entities.Lanche;

import java.util.ArrayList;
import java.util.List;

public class ImplLancheRepository implements LancheRepository{

    private LancheDAO dao;
    private List<Lanche> lanches;

    public ImplLancheRepository(LancheDAO dao) {
        this.dao = dao;
        this.lanches = new ArrayList<>();
    }


    @Override
    public Resultado<Lanche> cadastrar(String nome, double valor) {
        Lanche lanche = new Lanche(nome,valor);
        return dao.cadastrarLanche(lanche);
    }

    @Override
    public Resultado<List<Lanche>> listarTodos(){
        var resultado = dao.listar();

        if(resultado.foiSucesso()){
            lanches.clear();
            lanches.addAll(resultado.comoSucesso().getObj());
        }
        return dao.listar();
    }


    @Override
    public Resultado<Lanche> getById(int id) {
        if(lanches.size() != 0){
            Lanche ret = lanches.stream().filter(lanche->lanche.getId()==id).findFirst().get();
            return Resultado.sucesso("Lanche encontrado", ret);
        }
        return Resultado.erro("Problema ao buscar lanche!");
    }


    @Override
    public Resultado<Lanche> excluir(int id) {
        var resultado = dao.listar();
        if(resultado.foiSucesso()){
            lanches.clear();
            lanches.addAll(resultado.comoSucesso().getObj());
        }
        if(lanches.size() != 0){
            Lanche ret = lanches.stream().filter(lanche->lanche.getId()==id).findFirst().get();
            lanches.remove(ret);
            return dao.deletar(ret);
        }
        return Resultado.erro("Problema ao excluir lanche!");
    }

}
