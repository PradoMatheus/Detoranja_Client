package br.edu.fatec.detoranja.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.edu.fatec.detoranja.dominio.Carrinho;
import br.edu.fatec.detoranja.dominio.Carrinho_Itens;
import br.edu.fatec.detoranja.dominio.IDominio;

public class CarrinhoDAO implements IDAO {

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
		Carrinho carrinho = (Carrinho) obj;
		Connection conn = null;

		String sql = null;

		if (carrinho.getId_cliente() != 0)
			sql = "SELECT * FROM \"BD_CARRINHO\" WHERE id_cliente =" + carrinho.getId_cliente() + ";";
		else
			return carrinho;

		try {
			conn = Conexao.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				carrinho = new Carrinho();

				carrinho.setId(rs.getInt("id"));
				carrinho.setId_cliente(rs.getInt("id_cliente"));
				carrinho.setQuantidade(rs.getInt("quantidade"));
				carrinho.setValor_total(rs.getDouble("valor_total"));

				List<Carrinho_Itens> listaItens = new ArrayList<Carrinho_Itens>();
				for (IDominio d : new Carrinho_ItensDAO().lista(carrinho)) {
					listaItens.add((Carrinho_Itens) d);
				}
				carrinho.setItens(listaItens);

				return carrinho;
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
		// TODO Auto-generated method stub
		return null;
	}

}
