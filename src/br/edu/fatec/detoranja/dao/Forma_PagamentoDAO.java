package br.edu.fatec.detoranja.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.edu.fatec.detoranja.dominio.Cliente;
import br.edu.fatec.detoranja.dominio.Forma_Pagamento;
import br.edu.fatec.detoranja.dominio.IDominio;

public class Forma_PagamentoDAO implements IDAO {

	@Override
	public boolean salvar(IDominio obj) {

		Forma_Pagamento forma = (Forma_Pagamento) obj;

		String sql = "";
		
		if(forma.isPreferencal())
			sql = "UPDATE \"BD_CLIENTES_CARTAO\" SET preferencial=false WHERE id_cliente = " + forma.getId() + ";";

		if (forma.getId() != 0)
			sql += "UPDATE \"BD_CLIENTES_CARTAO\" "
					+ "SET numero_cartao=?, bandeira=?, cvv=?, id_cliente=?, nome=?, ano_validade=?, mes_validade=?, preferencial=? "
					+ "WHERE id = " + forma.getId() + ";";
		else
			sql += "INSERT INTO \"BD_CLIENTES_CARTAO\" "
					+ "(numero_cartao, bandeira, cvv, id_cliente, nome, ano_validade, mes_validade, preferencial) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

		Connection conn = null;

		try {
			conn = Conexao.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setLong(1, forma.getNumero_cartao());
			pstm.setString(2, forma.getBandeira());
			pstm.setInt(3, forma.getCvv());
			pstm.setInt(4, forma.getCliente().getId());
			pstm.setString(5, forma.getNome());
			pstm.setInt(6, forma.getAno_validade());
			pstm.setInt(7, forma.getMes_validade());
			pstm.setBoolean(8, forma.isPreferencal());
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
		Forma_Pagamento forma = (Forma_Pagamento) obj;

		String sql = "DELETE FROM \"BD_CLIENTES_CARTAO\" WHERE id=?;";

		Connection conn = null;

		try {
			conn = Conexao.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, forma.getId());
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
		Forma_Pagamento forma = (Forma_Pagamento) obj;

		Connection conn = null;

		String sql = "SELECT * FROM \"BD_CLIENTES_CARTAO\" WHERE id=" + forma.getId() + ";";

		try {
			conn = Conexao.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				forma = new Forma_Pagamento();

				forma.setId(rs.getInt("id"));
				forma.setBandeira(rs.getString("bandeira"));

				Cliente cliente = new Cliente();
				cliente.setId(rs.getInt("id_cliente"));
				forma.setCliente(cliente);

				forma.setNumero_cartao(rs.getLong("numero_cartao"));
				forma.setCvv(rs.getInt("cvv"));
				forma.setAno_validade(rs.getInt("ano_validade"));
				forma.setMes_validade(rs.getInt("mes_validade"));
				forma.setNome(rs.getString("nome"));
				forma.setPreferencal(rs.getBoolean("preferencial"));
			}

			return forma;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			Conexao.fechar(conn);
		}
		return null;
	}

	@Override
	public List<IDominio> lista(IDominio obj) {
		Forma_Pagamento forma = (Forma_Pagamento) obj;

		Connection conn = null;

		String sql = "SELECT * FROM \"BD_CLIENTES_CARTAO\" WHERE id_cliente=" + forma.getCliente().getId()
				+ " ORDER BY id;";

		try {
			conn = Conexao.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();

			List<IDominio> listaForma = new ArrayList<IDominio>();
			while (rs.next()) {
				forma = new Forma_Pagamento();

				forma.setId(rs.getInt("id"));
				forma.setBandeira(rs.getString("bandeira"));

				Cliente cliente = new Cliente();
				cliente.setId(rs.getInt("id_cliente"));
				forma.setCliente(cliente);

				forma.setNumero_cartao(rs.getLong("numero_cartao"));
				forma.setCvv(rs.getInt("cvv"));
				forma.setAno_validade(rs.getInt("ano_validade"));
				forma.setMes_validade(rs.getInt("mes_validade"));
				forma.setNome(rs.getString("nome"));
				forma.setPreferencal(rs.getBoolean("preferencial"));

				listaForma.add(forma);
			}

			return listaForma;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			Conexao.fechar(conn);
		}
		return null;
	}

}
