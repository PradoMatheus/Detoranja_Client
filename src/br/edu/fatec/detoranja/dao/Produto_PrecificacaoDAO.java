package br.edu.fatec.detoranja.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import br.edu.fatec.detoranja.dominio.IDominio;
import br.edu.fatec.detoranja.dominio.Produto_Precificacao;

public class Produto_PrecificacaoDAO implements IDAO {

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
		Produto_Precificacao produto_precificacao = (Produto_Precificacao) obj;

		Connection conn = null;

		String sql = "SELECT * FROM \"BD_PRODUTOS_PRECIFICACAO\" WHERE id=" + produto_precificacao.getId() + ";";

		try {
			conn = Conexao.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				produto_precificacao = new Produto_Precificacao();

				produto_precificacao.setId(rs.getInt("id"));
				produto_precificacao.setSigla(rs.getString("descricao"));
				produto_precificacao.setLucro(rs.getDouble("lucro"));

				return produto_precificacao;
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
