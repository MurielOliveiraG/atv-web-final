package ifpr.pgua.eic.tads.contatos.model.daos;

import com.github.hugoperlin.results.Resultado;
import ifpr.pgua.eic.tads.contatos.model.entities.FabricaConexoes;
import ifpr.pgua.eic.tads.contatos.model.entities.Pedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCPedidoDAO implements PedidoDAO {
    private FabricaConexoes fabricaConexoes;

    public JDBCPedidoDAO(FabricaConexoes fabricaConexoes) {
        this.fabricaConexoes = fabricaConexoes;
    }
    @Override
    public Resultado<Pedido> cadastrarPedido(Pedido pedido) {
        try(Connection connection = fabricaConexoes.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO oo_atvpedidos_pedidos(observacao,idBebida,idLanche) VALUE (?,?,?)");

            preparedStatement.setString(1, pedido.getObservacao());
            preparedStatement.setInt(2,pedido.getBebida().getId());
            preparedStatement.setInt(3,pedido.getLanche().getId());

            preparedStatement.executeUpdate();

            return Resultado.sucesso("Pedido cadastrado!", pedido);
        } catch (SQLException exception) {
            return Resultado.erro("Problema ao conectar" + exception.getMessage());
        }
    }

    @Override
    public Resultado<List<Pedido>> listar() {
        ArrayList<Pedido> pedidos = new ArrayList<>();

        try (Connection connection= fabricaConexoes.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM oo_atvpedidos_pedidos");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String observacao = resultSet.getString("observacao");

                Pedido pedido = new Pedido(id,observacao);

                pedidos.add(pedido);
            }
            return Resultado.sucesso("Pedidos carregados", pedidos);
        } catch (SQLException exception) {
            return Resultado.erro("Problema ao realizar seleção de pedidos"+exception.getMessage());
        }
    }
}