package br.edu.fatec.detoranja.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import br.edu.fatec.detoranja.dominio.IDominio;
import br.edu.fatec.detoranja.dominio.Troca_Status;

public class Troca_StatusDAO implements IDAO {

	@Override
	public boolean salvar(IDominio obj) {
		Troca_Status status = (Troca_Status) obj;

		String sql = "UPDATE \"BD_TROCA\" SET id_status = ? WHERE id = " + status.getTroca().getId() + ";";

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
		return null;
	}

	@Override
	public List<IDominio> lista(IDominio obj) {
		return null;
	}

}
