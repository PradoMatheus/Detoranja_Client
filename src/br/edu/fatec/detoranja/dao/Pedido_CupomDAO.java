package br.edu.fatec.detoranja.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.edu.fatec.detoranja.dominio.Cupom;
import br.edu.fatec.detoranja.dominio.IDominio;
import br.edu.fatec.detoranja.dominio.Pedido;

public class Pedido_CupomDAO implements IDAO {

	@Override
	public boolean salvar(IDominio obj) {
		Pedido pedido = (Pedido) obj;

		// String sql = "INSERT INTO \"BD_PEDIDO_CUPOM\" (id_cupom, valor, id_pedido)
		// VALUES ((SELECT id FROM \"BD_CUPONS\" WHERE cod_cupom = ?), ?, ?)";
		String sql = "INSERT INTO \"BD_PEDIDO_CUPOM\" (id_cupom, valor, id_pedido) "
				+ "SELECT CUPONS.id, CUPONS.desconto, ? FROM \"BD_CUPONS\" CUPONS WHERE id = ?";

		Connection conn = null;

		try {
			conn = Conexao.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			for (Cupom cupom : pedido.getListcupoms()) {
				pstm.setInt(1, pedido.getId());
				pstm.setInt(2, cupom.getId());
				pstm.execute();
			}

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
		return false;
	}

	@Override
	public IDominio buscar(IDominio obj) {
		return null;
	}

	@Override
	public List<IDominio> lista(IDominio obj) {
		Pedido pedido = (Pedido) obj;
		Cupom cupom = new Cupom();

		Connection conn = null;

		String sql = "SELECT DISTINCT CUPOM.id, CUPOM.cod_cupom, PEDCUPOM.valor "
				+ "FROM \"BD_PEDIDO_CUPOM\" AS PEDCUPOM JOIN \"BD_CUPONS\" AS CUPOM ON CUPOM.id = PEDCUPOM.id_cupom "
				+ "WHERE PEDCUPOM.id_pedido = " + pedido.getId() + ";";

		try {
			conn = Conexao.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			List<IDominio> listCupom = new ArrayList<IDominio>();

			while (rs.next()) {
				cupom = new Cupom();

				cupom.setId(rs.getInt("id"));
				cupom.setDesconto(rs.getDouble("valor"));
				cupom.setDesc_cupom(rs.getString("cod_cupom"));
				listCupom.add(cupom);
			}

			return listCupom;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			Conexao.fechar(conn);
		}
		return null;
	}

}
