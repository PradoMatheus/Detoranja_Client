package br.edu.fatec.detoranja.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.edu.fatec.detoranja.dominio.IDominio;
import br.edu.fatec.detoranja.dominio.Produto_Categoria;

public class Produto_CategoriaDAO implements IDAO {

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
		Produto_Categoria produto_categoria = (Produto_Categoria) obj;

		Connection conn = null;

		String sql = "SELECT * FROM \"BD_PRODUTOS_CATEGORIA\" WHERE id=" + produto_categoria.getId() + ";";

		try {
			conn = Conexao.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				produto_categoria = new Produto_Categoria();

				produto_categoria.setId(rs.getInt("id"));
				produto_categoria.setDescricao(rs.getString("descricao"));

				return produto_categoria;
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

		Produto_Categoria produto_categoria = (Produto_Categoria) obj;

		Connection conn = null;

		String sql = "SELECT * FROM \"BD_PRODUTOS_CATEGORIA\" ORDER BY id;";

		try {
			conn = Conexao.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();

			List<IDominio> listaProdutosCategorias = new ArrayList<IDominio>();
			while (rs.next()) {
				produto_categoria = new Produto_Categoria();

				produto_categoria.setId(rs.getInt("id"));
				produto_categoria.setDescricao(rs.getString("descricao"));

				listaProdutosCategorias.add(produto_categoria);
			}

			return listaProdutosCategorias;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			Conexao.fechar(conn);
		}
		return null;
	}
}
