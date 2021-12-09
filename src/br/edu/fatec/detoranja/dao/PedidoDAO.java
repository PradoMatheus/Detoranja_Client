package br.edu.fatec.detoranja.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.edu.fatec.detoranja.dominio.Cliente;
import br.edu.fatec.detoranja.dominio.Cupom;
import br.edu.fatec.detoranja.dominio.Endereco;
import br.edu.fatec.detoranja.dominio.Forma_Pagamento;
import br.edu.fatec.detoranja.dominio.IDominio;
import br.edu.fatec.detoranja.dominio.Pedido;
import br.edu.fatec.detoranja.dominio.Pedido_Frete;
import br.edu.fatec.detoranja.dominio.Pedido_Itens;
import br.edu.fatec.detoranja.dominio.Pedido_Log;
import br.edu.fatec.detoranja.dominio.Pedido_Status;

public class PedidoDAO implements IDAO {

	@Override
	public boolean salvar(IDominio obj) {
		Pedido pedido = (Pedido) obj;

		String sql = "";

		if (pedido.getId() == 0)
			sql += "INSERT INTO \"BD_PEDIDO\" (id_cliente, valor, quantidade, data_pedido, id_status) VALUES (?, ?, ?, ?, ?) RETURNING id;";

		ResultSet ultimoID = null;
		Connection conn = null;

		try {
			conn = Conexao.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, pedido.getCliente().getId());
			pstm.setDouble(2, pedido.getValorTotal());
			pstm.setInt(3, pedido.getQuantidade());
			pstm.setDate(4, Date.valueOf(pedido.getData_pedido().toLocalDate()));
			pstm.setInt(5, pedido.getStatus().getId());

			ultimoID = pstm.executeQuery();
			while (ultimoID.next())
				pedido.setId(ultimoID.getInt(1));

			new Pedido_EnderecoDAO().salvar(pedido);
			if (pedido.getValorTotal() > 0)
				new Pedido_Forma_PagamentoDAO().salvar(pedido);
			new Pedido_CupomDAO().salvar(pedido);
			new Pedido_FreteDAO().salvar(pedido);
			new Pedido_ProdutoDAO().salvar(pedido);

			return true;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			Conexao.fechar(conn);
		}

		return false;
	}

	@Override
	public boolean excluir(IDominio obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IDominio buscar(IDominio obj) {
		Pedido pedido = (Pedido) obj;

		Connection conn = null;

		String sql = "SELECT PEDIDO.id, PEDIDO.id_cliente, PEDIDO.valor, PEDIDO.quantidade, PEDIDO.data_pedido, PEDIDO.id_status, STATUS.descricao FROM \"BD_PEDIDO\" PEDIDO "
				+ "JOIN \"BD_PEDIDO_STATUS\" STATUS ON STATUS.id = PEDIDO.id_status  " + "WHERE PEDIDO.id = "
				+ pedido.getId() + ";";

		try {
			conn = Conexao.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				pedido.setId(rs.getInt("id"));

				Cliente cliente = new Cliente();
				cliente.setId(rs.getInt("id_cliente"));
				pedido.setCliente(cliente);

				pedido.setValorTotal(rs.getDouble("valor"));
				pedido.setQuantidade(rs.getInt("quantidade"));
				pedido.setData_pedido(rs.getObject("data_pedido", LocalDateTime.class));

				Pedido_Status status = new Pedido_Status();
				status.setId(rs.getInt("id_status"));
				status.setDescricao(rs.getString("descricao"));
				pedido.setStatus(status);

				pedido.setEndereco((Endereco) new Pedido_EnderecoDAO().buscar(pedido));

				List<Pedido_Itens> listItens = new ArrayList<Pedido_Itens>();
				for (IDominio d : new Pedido_ProdutoDAO().lista(pedido))
					listItens.add((Pedido_Itens) d);
				pedido.setListprodutos(listItens);
				
				List<Forma_Pagamento> listFormas = new ArrayList<Forma_Pagamento>();
				for (IDominio d : new Pedido_Forma_PagamentoDAO().lista(pedido))
					listFormas.add((Forma_Pagamento) d);
				pedido.setListforma_Pagamentos(listFormas);

				pedido.setFrete((Pedido_Frete) new Pedido_FreteDAO().buscar(pedido));
				
				List<Pedido_Log> listaLogs = new ArrayList<Pedido_Log>();
				for (IDominio d : new Pedido_LogDAO().lista(pedido))
					listaLogs.add((Pedido_Log) d);
				pedido.setListlogs(listaLogs);
				
				List<Cupom> listCupons = new ArrayList<Cupom>();
				for (IDominio d : new Pedido_CupomDAO().lista(pedido))
					listCupons.add((Cupom) d);
				pedido.setListcupoms(listCupons);
			}

			return pedido;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			Conexao.fechar(conn);
		}
		return null;
	}

	@Override
	public List<IDominio> lista(IDominio obj) {
		Pedido pedido = (Pedido) obj;

		String sql = "SELECT PEDIDO.id, PEDIDO.id_cliente, PEDIDO.valor, PEDIDO.quantidade, PEDIDO.data_pedido, PEDIDO.id_status, STATUS.descricao FROM \"BD_PEDIDO\" PEDIDO "
				+ "JOIN \"BD_PEDIDO_STATUS\" STATUS ON STATUS.id = PEDIDO.id_status  " + "WHERE PEDIDO.id_cliente = "
				+ pedido.getCliente().getId() + ";";

		Connection conn = null;

		try {
			conn = Conexao.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();

			List<IDominio> listaPedido = new ArrayList<IDominio>();
			while (rs.next()) {
				pedido = new Pedido();

				pedido.setId(rs.getInt("id"));

				Cliente cliente = new Cliente();
				cliente.setId(rs.getInt("id_cliente"));
				pedido.setCliente(cliente);

				pedido.setValorTotal(rs.getDouble("valor"));
				pedido.setQuantidade(rs.getInt("quantidade"));
				pedido.setData_pedido(rs.getObject("data_pedido", LocalDateTime.class));

				Pedido_Status status = new Pedido_Status();
				status.setId(rs.getInt("id_status"));
				status.setDescricao(rs.getString("descricao"));
				pedido.setStatus(status);

				listaPedido.add(pedido);
			}

			return listaPedido;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			Conexao.fechar(conn);
		}

		return null;
	}

}
