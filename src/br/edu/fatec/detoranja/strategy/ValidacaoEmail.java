package br.edu.fatec.detoranja.strategy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.edu.fatec.detoranja.dao.Conexao;
import br.edu.fatec.detoranja.dominio.Cliente;
import br.edu.fatec.detoranja.dominio.IDominio;

public class ValidacaoEmail implements IStrategy {

	@Override
	public String processar(IDominio dominio) {
		Cliente cliente = (Cliente) dominio;

		if (cliente.getId() == 0) {

			String sql = "SELECT * FROM \"BD_CLIENTES\" WHERE email = '" + cliente.getEmail() + "';";

			Connection conn = null;

			try {
				conn = Conexao.getConnection();
				PreparedStatement pstm = conn.prepareStatement(sql);
				ResultSet rs = pstm.executeQuery();

				while (rs.next()) {
					cliente.setId(rs.getInt("id"));

					return "E-mail já cadastrado !!";
				}
			} catch (Exception e) {
				System.err.println(e.getMessage());
			} finally {
				Conexao.fechar(conn);
			}
		}
		
		return null;
	}

}
