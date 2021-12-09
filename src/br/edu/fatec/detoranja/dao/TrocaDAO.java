package br.edu.fatec.detoranja.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.edu.fatec.detoranja.dominio.Cupom;
import br.edu.fatec.detoranja.dominio.IDominio;
import br.edu.fatec.detoranja.dominio.Pedido;
import br.edu.fatec.detoranja.dominio.Troca;
import br.edu.fatec.detoranja.dominio.Troca_Itens;
import br.edu.fatec.detoranja.dominio.Troca_Log;
import br.edu.fatec.detoranja.dominio.Troca_Motivo;
import br.edu.fatec.detoranja.dominio.Troca_Status;

public class TrocaDAO implements IDAO {

	@Override
	public boolean salvar(IDominio obj) {
		Troca troca = (Troca) obj;

		String sql = "";

		if (troca.getId() == 0)
			sql = "INSERT INTO \"BD_TROCA\"(id_pedido, id_motivo, observacao, data, id_status) VALUES (?, ?, ?, current_timestamp, ?) RETURNING id;";

		ResultSet ultimoID = null;
		Connection conn = null;

		try {
			conn = Conexao.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, troca.getPedido().getId());
			pstm.setInt(2, troca.getMotivo().getId());
			pstm.setString(3, troca.getObservacao());
			pstm.setInt(4, troca.getStatus().getId());

			ultimoID = pstm.executeQuery();
			while (ultimoID.next())
				troca.setId(ultimoID.getInt(1));

			new Troca_ItensDAO().salvar(troca);

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
		Troca troca = (Troca) obj;

		Connection conn = null;

		String sql = "SELECT TROCA.id, TROCA.id_motivo, MOTIVO.descricao motivo, TROCA.id_pedido, TROCA.observacao, TROCA.\"data\" data, TROCA.id_status, STATUS.descricao status "
				+ "FROM \"BD_TROCA\" TROCA " 
				+ "JOIN \"BD_PEDIDO\" PEDIDO ON PEDIDO.id = TROCA.id_pedido "
				+ "JOIN \"BD_TROCA_MOTIVO\" MOTIVO ON MOTIVO.id = TROCA.id_motivo "
				+ "JOIN \"BD_TROCA_STATUS\" STATUS ON STATUS.id = TROCA.id_status " 
				+ "WHERE TROCA.id = " + troca.getId() + ";";

		try {
			conn = Conexao.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				troca = new Troca();

				troca.setId(rs.getInt("id"));
				troca.setObservacao(rs.getString("observacao"));
				troca.setData(rs.getObject("data", LocalDateTime.class));

				Troca_Motivo motivo = new Troca_Motivo();
				motivo.setDescricao(rs.getString("motivo"));
				motivo.setId(rs.getInt("id_motivo"));
				troca.setMotivo(motivo);

				Pedido pedido = new Pedido();
				pedido.setId(rs.getInt("id_pedido"));
				troca.setPedido(pedido);

				Troca_Status status = new Troca_Status();
				status.setDescricao(rs.getString("status"));
				status.setId(rs.getInt("id_status"));
				troca.setStatus(status);

				List<Troca_Itens> listItens = new ArrayList<Troca_Itens>();
				for (IDominio d : new Troca_ItensDAO().lista(troca))
					listItens.add((Troca_Itens) d);
				troca.setListTrocaItens(listItens);

				troca.setCupom((Cupom) new Troca_CupomDAO().buscar(troca));

				List<Troca_Log> listaLogs = new ArrayList<Troca_Log>();
				for (IDominio d : new Troca_LogDAO().lista(troca))
					listaLogs.add((Troca_Log) d);
				troca.setListLogs(listaLogs);

			}

			return troca;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			Conexao.fechar(conn);
		}
		return null;
	}

	@Override
	public List<IDominio> lista(IDominio obj) {
		Troca troca = (Troca) obj;

		Connection conn = null;

		String sql = "SELECT TROCA.id, TROCA.id_motivo, MOTIVO.descricao motivo, TROCA.id_pedido, TROCA.observacao, TROCA.\"data\" data, TROCA.id_status, STATUS.descricao status "
				+ "FROM \"BD_TROCA\" TROCA " + "JOIN \"BD_PEDIDO\" PEDIDO ON PEDIDO.id = TROCA.id_pedido "
				+ "JOIN \"BD_TROCA_MOTIVO\" MOTIVO ON MOTIVO.id = TROCA.id_motivo "
				+ "JOIN \"BD_TROCA_STATUS\" STATUS ON STATUS.id = TROCA.id_status " + "WHERE PEDIDO.id_cliente = "
				+ troca.getCliente().getId() + ";";

		try {
			conn = Conexao.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			List<IDominio> listTrocas = new ArrayList<IDominio>();

			while (rs.next()) {
				troca = new Troca();

				troca.setId(rs.getInt("id"));
				troca.setObservacao(rs.getString("observacao"));
				troca.setData(rs.getObject("data", LocalDateTime.class));

				Troca_Motivo motivo = new Troca_Motivo();
				motivo.setDescricao(rs.getString("motivo"));
				motivo.setId(rs.getInt("id_motivo"));
				troca.setMotivo(motivo);

				Pedido pedido = new Pedido();
				pedido.setId(rs.getInt("id_pedido"));
				troca.setPedido(pedido);

				Troca_Status status = new Troca_Status();
				status.setDescricao(rs.getString("status"));
				status.setId(rs.getInt("id_status"));
				troca.setStatus(status);

				listTrocas.add(troca);
			}

			return listTrocas;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			Conexao.fechar(conn);
		}
		return null;
	}

}
