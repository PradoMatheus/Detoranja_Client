package br.edu.fatec.detoranja.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import br.edu.fatec.detoranja.dominio.IDominio;
import br.edu.fatec.detoranja.dominio.Pedido_Status;

public class Pedido_StatusDAO implements IDAO {

	@Override
	public boolean salvar(IDominio obj) {
		Pedido_Status status = (Pedido_Status) obj;

		String sql = "UPDATE \"BD_PEDIDO\" SET id_status = ? WHERE id = " + status.getPedido().getId()
				+ ";";

		Connection conn = null;

		try {
			conn = Conexao.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, status.getId());
			pstm.execute();

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IDominio> lista(IDominio obj) {
		// TODO Auto-generated method stub
		return null;
	}

}
