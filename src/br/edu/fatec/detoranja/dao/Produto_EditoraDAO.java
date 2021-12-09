package br.edu.fatec.detoranja.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import br.edu.fatec.detoranja.dominio.IDominio;
import br.edu.fatec.detoranja.dominio.Produto_Editora;

public class Produto_EditoraDAO implements IDAO {

	@Override
	public boolean salvar(IDominio obj) {
		return false;
	}

	@Override
	public boolean excluir(IDominio obj) {
		return false;
	}

	@Override
	public IDominio buscar(IDominio obj) {
		Produto_Editora produto_editora = (Produto_Editora) obj;

		Connection conn = null;

		String sql = "SELECT * FROM \"BD_PRODUTOS_EDITORA\" WHERE id=" + produto_editora.getId() + ";";

		try {
			conn = Conexao.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				produto_editora = new Produto_Editora();

				produto_editora.setId(rs.getInt("id"));
				produto_editora.setDescricao(rs.getString("descricao"));

				return produto_editora;
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			Conexao.fechar(conn);
		}
		return null;
	}

	@Override
	public List<IDominio> lista(IDominio obj) {
		return null;
	}
}
