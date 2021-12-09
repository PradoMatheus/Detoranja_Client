package br.edu.fatec.detoranja.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import br.edu.fatec.detoranja.dominio.IDominio;
import br.edu.fatec.detoranja.dominio.Produto_Idioma;

public class Produto_IdiomaDAO implements IDAO {

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
		Produto_Idioma produto_idioma = (Produto_Idioma) obj;

		Connection conn = null;

		String sql = "SELECT * FROM \"BD_PRODUTOS_IDIOMA\" WHERE id=" + produto_idioma.getId() + ";";

		try {
			conn = Conexao.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				produto_idioma = new Produto_Idioma();

				produto_idioma.setId(rs.getInt("id"));
				produto_idioma.setDescricao(rs.getString("descricao"));

				return produto_idioma;
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
