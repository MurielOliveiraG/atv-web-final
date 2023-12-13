package ifpr.pgua.eic.tads.contatos.model.daos;
import java.util.List;
import com.github.hugoperlin.results.Resultado;
import ifpr.pgua.eic.tads.contatos.model.entities.Lanche;
import ifpr.pgua.eic.tads.contatos.model.entities.Pedido;

public interface LancheDAO {
    Resultado <Lanche> cadastrarLanche (Lanche lanche);
    Resultado<Lanche> buscarLanches(Pedido pedido);
    Resultado <List<Lanche>> listar();
    Resultado <Lanche> deletar(Lanche lanche);
}
