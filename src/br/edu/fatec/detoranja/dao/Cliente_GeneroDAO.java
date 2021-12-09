package br.edu.fatec.detoranja.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.edu.fatec.detoranja.dominio.Cliente_Genero;
import br.edu.fatec.detoranja.dominio.IDominio;

public class Cliente_GeneroDAO implements IDAO {

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
		Cliente_Genero genero = (Cliente_Genero) obj;

		Connection conn = null;

		String sql = "SELECT * FROM \"BD_CLIENTES_GENERO\" ORDER BY id;";

		try {
			conn = Conexao.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();

			List<IDominio> listaGeneros = new ArrayList<IDominio>();
			while (rs.next()) {
				genero = new Cliente_Genero();

				genero.setId(rs.getInt("id"));
				genero.setDescricao(rs.getString("descricao"));

				listaGeneros.add(genero);
			}

			return listaGeneros;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			Conexao.fechar(conn);
		}
		return null;
	}

}
