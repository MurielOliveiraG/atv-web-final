package ifpr.pgua.eic.tads.contatos.model.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.github.hugoperlin.results.Resultado;
import ifpr.pgua.eic.tads.contatos.model.entities.FabricaConexoes;
import ifpr.pgua.eic.tads.contatos.model.entities.Lanche;
import ifpr.pgua.eic.tads.contatos.model.entities.Pedido;

public class JDBCLancheDAO implements LancheDAO {
    private FabricaConexoes fabricaConexao;

    public JDBCLancheDAO(FabricaConexoes fabricaConexao) {
        this.fabricaConexao = fabricaConexao;
    }

    @Override
    public Resultado<Lanche> cadastrarLanche(Lanche lanche) {
        try {
            Connection connection = fabricaConexao.getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO oo_atvpedidos_lanches(nome,valor) VALUES (?,?)");

            preparedStatement.setString(1, lanche.getNome());
            preparedStatement.setDouble(2, lanche.getValor());

            preparedStatement.executeUpdate();
            connection.close();

            return Resultado.sucesso("Lanche cadastrado", lanche);
        } catch (SQLException exception) {
            return Resultado.erro(exception.getMessage());
        }
    }

    @Override
    public Resultado<Lanche> buscarLanches(Pedido pedido) {
        try (Connection connection = fabricaConexao.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM oo_atvpedidos_lanches inner join oo_atvpedidos_pedidos ON oo_atvpedidos_lanches.id=oo_atvpedidos_pedidos.idLanche WHERE oo_atvpedidos_pedidos.id=?");

            preparedStatement.setInt(1, pedido.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("oo_atvpedidos_lanches.id");
                String nome = resultSet.getString("oo_atvpedidos_lanches.nome");
                double valor = resultSet.getDouble("oo_atvpedidos_lanches.valor");

                Lanche lanche = new Lanche(id, nome, valor);
                return Resultado.sucesso("Lanche carregado", lanche);
            }
            return Resultado.erro("Lanche não encontrado");
        } catch (SQLException exception) {
            return Resultado.erro("Problema em fazer a seleção!" + exception.getMessage());
        }
    }

    @Override
    public Resultado<List<Lanche>> listar() {
        ArrayList<Lanche> lanches = new ArrayList<>();
        try {
            Connection connection = fabricaConexao.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM oo_atvpedidos_lanches");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                double valor = resultSet.getDouble("valor");

                Lanche lanche = new Lanche(id, nome, valor);

                lanches.add(lanche);
            }
            connection.close();
            return Resultado.sucesso("Lanches carregados", lanches);
        } catch (SQLException exception) {
            return Resultado.erro(exception.getMessage());
        }
    }

    @Override
    public Resultado<Lanche> deletar(Lanche lanche) {
        try {
            Connection connection = fabricaConexao.getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement( "DELETE FROM oo_atvpedidos_lanches WHERE oo_atvpedidos_lanches.id=?");

            preparedStatement.setInt(1, lanche.getId());

            preparedStatement.execute();
            connection.close();

            return Resultado.sucesso("Lanche deletado", lanche);
        } catch (SQLException exception) {
            return Resultado.erro(exception.getMessage());
        }
    }

}
