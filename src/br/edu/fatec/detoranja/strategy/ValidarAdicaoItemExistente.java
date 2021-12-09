package br.edu.fatec.detoranja.strategy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.edu.fatec.detoranja.dao.Conexao;
import br.edu.fatec.detoranja.dominio.Carrinho_Itens;
import br.edu.fatec.detoranja.dominio.IDominio;

public class ValidarAdicaoItemExistente implements IStrategy {

	@Override
	public String processar(IDominio dominio) {
		Carrinho_Itens item = (Carrinho_Itens) dominio;
		
		if (item.getId() != 0) 
			return null;

		String sql = "SELECT * FROM \"BD_CARRINHO_PRODUTOS\" "
				+ "WHERE id_carrinho = (SELECT id FROM \"BD_CARRINHO\" WHERE id_cliente = "
				+ item.getCarrinho().getId_cliente() + ") AND id_produto = " + item.getProduto().getId() + ";";

		Connection conn = null;

		try {
			conn = Conexao.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {

				return "Item já Adicionado ao carrinho !!";
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			Conexao.fechar(conn);
		}

		return null;
	}

}
