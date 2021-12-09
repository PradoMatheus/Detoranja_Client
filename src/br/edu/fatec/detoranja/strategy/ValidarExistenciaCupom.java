package br.edu.fatec.detoranja.strategy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.edu.fatec.detoranja.dao.Conexao;
import br.edu.fatec.detoranja.dominio.Cupom;
import br.edu.fatec.detoranja.dominio.IDominio;

public class ValidarExistenciaCupom implements IStrategy {

	// Valida se o cupom esta disponivel para utilização do usuario
	@Override
	public String processar(IDominio dominio) {
		Cupom cupom = (Cupom) dominio;

		String sql = "SELECT * FROM \"BD_CUPONS\" " + "WHERE cod_cupom = '" + cupom.getDesc_cupom() + "';";

		Connection conn = null;

		try {
			conn = Conexao.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {

				return null;

			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			Conexao.fechar(conn);
		}

		return "Cupom não encontrado";
	}

}
