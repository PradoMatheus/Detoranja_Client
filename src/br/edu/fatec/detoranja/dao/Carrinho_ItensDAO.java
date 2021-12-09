package br.edu.fatec.detoranja.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.edu.fatec.detoranja.dominio.Carrinho;
import br.edu.fatec.detoranja.dominio.Carrinho_Itens;
import br.edu.fatec.detoranja.dominio.IDominio;
import br.edu.fatec.detoranja.dominio.Produto;

public class Carrinho_ItensDAO implements IDAO {

	@Override
	public boolean salvar(IDominio obj) {
		Carrinho_Itens item = (Carrinho_Itens) obj;

		String sql = "";

		if (item.getId() == 0)
			sql = "INSERT INTO \"BD_CARRINHO_PRODUTOS\"(id_carrinho, id_produto, quantidade) "
					+ "VALUES ((SELECT id FROM \"BD_CARRINHO\" WHERE id_cliente = ?), ?, ?);";
		else
			sql = "UPDATE \"BD_CARRINHO_PRODUTOS\" 	SET quantidade = ? WHERE id = ?;";

		Connection conn = null;

		try {
			conn = Conexao.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			if (item.getId() == 0) {
				pstm.setInt(1, item.getCarrinho().getId_cliente());
				pstm.setInt(2, item.getProduto().getId());
				pstm.setInt(3, item.getQuantidade());
			} else {
				pstm.setInt(1, item.getQuantidade());
				pstm.setInt(2, item.getId());
			}
			pstm.execute();

			return true;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			Conexao.fechar(conn);
		}

		return false;
	}

	@Override
	public boolean excluir(IDominio obj) {
		Carrinho_Itens item = (Carrinho_Itens) obj;

		String sql = "DELETE FROM \"BD_CARRINHO_PRODUTOS\" WHERE id=?;";

		Connection conn = null;

		try {
			conn = Conexao.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, item.getId());
			pstm.execute();

			return true;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			Conexao.fechar(conn);
		}

		return false;
	}

	@Override
	public IDominio buscar(IDominio obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IDominio> lista(IDominio obj) {
		Carrinho carrinho = (Carrinho) obj;

		Connection conn = null;

		String sql = "SELECT *, (SELECT estoque FROM \"BD_ESTOQUE\" WHERE id_produto = PRODUTO_CARRINHO.id_produto LIMIT 1) AS estoque "
				+ "FROM \"BD_CARRINHO_PRODUTOS\" PRODUTO_CARRINHO WHERE id_carrinho=" + carrinho.getId() + " ORDER BY id;";

		try {
			conn = Conexao.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();

			Carrinho_Itens item = new Carrinho_Itens();
			List<IDominio> listaItens = new ArrayList<IDominio>();
			while (rs.next()) {
				item = new Carrinho_Itens();

				item.setId(rs.getInt("id"));
				item.setCarrinho(carrinho);
				item.setQuantidade(rs.getInt("quantidade"));
				item.setEstoque(rs.getInt("estoque"));

				Produto produto = new Produto();
				produto.setId(rs.getInt("id_produto"));
				item.setProduto((Produto) new ProdutoDAO().buscar(produto));

				listaItens.add(item);
			}

			return listaItens;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			Conexao.fechar(conn);
		}
		return null;
	}

}
