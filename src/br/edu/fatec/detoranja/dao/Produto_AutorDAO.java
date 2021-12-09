package br.edu.fatec.detoranja.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import br.edu.fatec.detoranja.dominio.IDominio;
import br.edu.fatec.detoranja.dominio.Produto_Autor;

public class Produto_AutorDAO implements IDAO {

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
		Produto_Autor produto_autor = (Produto_Autor) obj;

		Connection conn = null;

		String sql = "SELECT * FROM \"BD_PRODUTOS_CATEGORIA\" WHERE id=" + produto_autor.getId() + ";";

		try {
			conn = Conexao.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				produto_autor = new Produto_Autor();

				produto_autor.setId(rs.getInt("id"));
				produto_autor.setNome(rs.getString("descricao"));

				return produto_autor;
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
