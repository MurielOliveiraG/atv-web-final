package ifpr.pgua.eic.tads.contatos.model.daos;

import ifpr.pgua.eic.tads.contatos.model.entities.FabricaConexoes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import com.github.hugoperlin.results.Resultado;
import ifpr.pgua.eic.tads.contatos.model.entities.Bebida;
import ifpr.pgua.eic.tads.contatos.model.entities.Pedido;

public class JDBCBebidaDAO implements BebidaDAO {
    private FabricaConexoes fabricaConexao;

    public JDBCBebidaDAO(FabricaConexoes fabricaConexao) {
        this.fabricaConexao = fabricaConexao;
    }

    @Override
    public Resultado<Bebida> cadastrarBebida(Bebida bebida) {
        try {
            Connection connection = fabricaConexao.getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO oo_atvpedidos_bebidas(nome,valor) VALUES (?,?)");

            preparedStatement.setString(1, bebida.getNome());
            preparedStatement.setDouble(2, bebida.getValor());

            preparedStatement.executeUpdate();
            connection.close();

            return Resultado.sucesso("Bebida cadastrada", bebida);
        } catch (SQLException exception) {
            return Resultado.erro(exception.getMessage());
        }
    }

    @Override
    public Resultado<Bebida> buscarBebidas(Pedido pedido) {
        try (Connection connection = fabricaConexao.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM oo_atvpedidos_bebidas inner join oo_atvpedidos_pedidos ON oo_atvpedidos_bebidas.id=oo_atvpedidos_pedidos.idBebida WHERE oo_atvpedidos_pedidos.id=?");

            preparedStatement.setInt(1, pedido.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("oo_atvpedidos_bebidas.id");
                String nome = resultSet.getString("oo_atvpedidos_bebidas.nome");
                double valor = resultSet.getDouble("oo_atvpedidos_bebidas.valor");

                Bebida bebida = new Bebida(id, nome, valor);
                return Resultado.sucesso("Bebida carregada", bebida);
            }
            return Resultado.erro("Bebida não encontrada");
        } catch (SQLException exception) {
            return Resultado.erro("Problema em fazer a seleção!" + exception.getMessage());
        }
    }

    @Override
    public Resultado<List<Bebida>> listar() {
        ArrayList<Bebida> bebidas = new ArrayList<>();
        try {
            Connection connection = fabricaConexao.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM oo_atvpedidos_bebidas");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                double valor = resultSet.getDouble("valor");

                Bebida bebida = new Bebida(id, nome, valor);

                bebidas.add(bebida);
            }
            connection.close();
            return Resultado.sucesso("Bebidas carregadas", bebidas);
        } catch (SQLException exception) {
            return Resultado.erro(exception.getMessage());
        }
    }

    

}