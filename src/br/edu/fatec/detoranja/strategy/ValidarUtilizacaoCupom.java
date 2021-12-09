package br.edu.fatec.detoranja.strategy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.edu.fatec.detoranja.dao.Conexao;
import br.edu.fatec.detoranja.dominio.Cupom;
import br.edu.fatec.detoranja.dominio.IDominio;

public class ValidarUtilizacaoCupom implements IStrategy {

	@Override
	public String processar(IDominio dominio) {
		Cupom cupom = (Cupom) dominio;

		String sql = "SELECT * FROM \"BD_CUPONS\" CUPOM "
				+ "WHERE cod_cupom = '" + cupom.getDesc_cupom() + "' AND ativo = TRUE AND "
				+ "(tipo = 2 OR tipo = 1 AND cliente = " + cupom.getCliente().getId() + ") AND " 
		        + "NOT EXISTS (SELECT * FROM \"BD_CUPONS_UTILIZACAO\" WHERE id_cliente = " + cupom.getCliente().getId() + " AND id_cupom = CUPOM.id)";
		
		System.out.println(sql);

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

		return "Cupom já utilizado ou indisponivel!";
	}

}
