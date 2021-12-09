package br.edu.fatec.detoranja.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.edu.fatec.detoranja.dominio.Endereco_Tipo_Logradouro;
import br.edu.fatec.detoranja.dominio.IDominio;

public class Endereco_Tipo_LogradouroDAO implements IDAO {

	@Override
	public boolean salvar(IDominio obj) {
		// TODO Auto-generated method stub
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
		Endereco_Tipo_Logradouro tipo_logradouro = (Endereco_Tipo_Logradouro) obj;

		Connection conn = null;

		String sql = "SELECT * FROM \"BD_TIPO_LOGRADOURO\" ORDER BY id;";

		try {
			conn = Conexao.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();

			List<IDominio> listaProdutosCategorias = new ArrayList<IDominio>();
			while (rs.next()) {
				tipo_logradouro = new Endereco_Tipo_Logradouro();

				tipo_logradouro.setId(rs.getInt("id"));
				tipo_logradouro.setDescricao(rs.getString("descricao"));

				listaProdutosCategorias.add(tipo_logradouro);
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
