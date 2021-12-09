package br.edu.fatec.detoranja.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.edu.fatec.detoranja.dominio.IDominio;
import br.edu.fatec.detoranja.dominio.Produto_Imagens;

public class Produto_ImagemDAO implements IDAO {

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
		return null;
	}

	@Override
	public List<IDominio> lista(IDominio obj) {
		Produto_Imagens produto_imagem = (Produto_Imagens) obj;

		Connection conn = null;

		String sql = "SELECT * FROM \"BD_PRODUTOS_IMAGENS\" WHERE id_produto=" + produto_imagem.getId_produto()
				+ " ORDER BY id;";

		try {
			conn = Conexao.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();

			List<IDominio> listaProdutosImagens = new ArrayList<IDominio>();
			while (rs.next()) {
				produto_imagem = new Produto_Imagens();

				produto_imagem.setId(rs.getInt("id"));
				produto_imagem.setId_produto(rs.getInt("id_produto"));
				produto_imagem.setPath(rs.getString("path_imagem"));

				listaProdutosImagens.add(produto_imagem);
			}

			return listaProdutosImagens;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			Conexao.fechar(conn);
		}
		return null;
	}
}
