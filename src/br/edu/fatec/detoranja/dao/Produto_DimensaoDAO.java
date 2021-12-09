package br.edu.fatec.detoranja.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import br.edu.fatec.detoranja.dominio.IDominio;
import br.edu.fatec.detoranja.dominio.Produto_Dimensao;

public class Produto_DimensaoDAO implements IDAO {

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
		Produto_Dimensao produto_dimensao = (Produto_Dimensao) obj;

		Connection conn = null;

		String sql = "SELECT * FROM \"BD_PRODUTOS_DIMENSAO\" WHERE id_produto=" + produto_dimensao.getId() + ";";

		try {
			conn = Conexao.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				produto_dimensao = new Produto_Dimensao();

				produto_dimensao.setId(rs.getInt("id_produto"));
				produto_dimensao.setAltura(rs.getDouble("altura"));
				produto_dimensao.setLargura(rs.getDouble("largura"));
				produto_dimensao.setPeso(rs.getDouble("peso"));
				produto_dimensao.setProfundidade(rs.getDouble("profundidade"));

				return produto_dimensao;
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
