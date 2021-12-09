package br.edu.fatec.detoranja.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.edu.fatec.detoranja.dominio.IDominio;
import br.edu.fatec.detoranja.dominio.Pedido;
import br.edu.fatec.detoranja.dominio.Pedido_Log;
import br.edu.fatec.detoranja.dominio.Pedido_Status;

public class Pedido_LogDAO implements IDAO {

	@Override
	public boolean salvar(IDominio obj) {
		return false;
	}

	@Override
	public boolean excluir(IDominio obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IDominio buscar(IDominio obj) {
		return null;
	}

	@Override
	public List<IDominio> lista(IDominio obj) {
		Pedido pedido = (Pedido) obj;
		Pedido_Log log = new Pedido_Log();

		Connection conn = null;

		String sql = "SELECT LOG.id, LOG.id_status, STATUS.descricao, LOG.data FROM \"BD_PEDIDO_LOG\" LOG "
				+ "JOIN \"BD_PEDIDO_STATUS\" STATUS ON STATUS.id = LOG.id_status " + "WHERE id_pedido = "
				+ pedido.getId() + ";";

		try {
			conn = Conexao.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();

			List<IDominio> logs = new ArrayList<IDominio>();
			while (rs.next()) {
				log = new Pedido_Log();

				log.setId(rs.getInt("id"));
				log.setData(rs.getObject("data", LocalDateTime.class));

				Pedido_Status status = new Pedido_Status();
				status.setId(rs.getInt("id_status"));
				status.setDescricao(rs.getString("descricao"));
				log.setStatus(status);

				logs.add(log);
			}
			return logs;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			Conexao.fechar(conn);
		}
		return null;
	}

}