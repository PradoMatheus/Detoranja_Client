package br.edu.fatec.detoranja.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.edu.fatec.detoranja.dominio.Endereco;
import br.edu.fatec.detoranja.dominio.Endereco_Tipo_Logradouro;
import br.edu.fatec.detoranja.dominio.IDominio;

public class EnderecoDAO implements IDAO {

	@Override
	public boolean salvar(IDominio obj) {
		Endereco endereco = (Endereco) obj;

		String sql;

		if (endereco.getId() != 0)
			sql = "UPDATE \"BD_CLIENTES_ENDERECO\" " 
					+ "SET id_cliente=?, cep=?, estado=?, cidade=?, pais=?, id_tipo_logradouro=?, logradouro=?, numero=?, bairro=?, nome_endereco=?, complemento=?, referencia=?, preferencial=? "
					+ "WHERE id = " + endereco.getId() + ";";
		else
			sql = "INSERT INTO \"BD_CLIENTES_ENDERECO\" " 
					+ "(id_cliente, cep, estado, cidade, pais, id_tipo_logradouro, logradouro, numero, bairro, nome_endereco, complemento, referencia, preferencial) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

		Connection conn = null;

		try {
			conn = Conexao.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, endereco.getId_cliente());
			pstm.setString(2, endereco.getCep());
			pstm.setString(3, endereco.getEstado());
			pstm.setString(4, endereco.getCidade());
			pstm.setString(5, endereco.getPais());
			pstm.setInt(6, endereco.getTipo_logradouro().getId());
			pstm.setString(7, endereco.getLogradouro());
			pstm.setString(8, endereco.getNumero());
			pstm.setString(9, endereco.getBairro());
			pstm.setString(10, endereco.getDescricao());
			pstm.setString(11, endereco.getComplemento());
			pstm.setString(12, endereco.getReferencia());
			pstm.setBoolean(13, endereco.isPreferencial());
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
		Endereco endereco = (Endereco) obj;

		String sql = "DELETE FROM \"BD_CLIENTES_ENDERECO\" WHERE id=?;";

		Connection conn = null;

		try {
			conn = Conexao.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, endereco.getId());
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
		Endereco endereco = (Endereco) obj;

		Connection conn = null;

		String sql = "SELECT * FROM \"BD_CLIENTES_ENDERECO\" WHERE id=" + endereco.getId() + ";";

		try {
			conn = Conexao.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				endereco = new Endereco();
				
				endereco.setId(rs.getInt("id"));
				endereco.setId_cliente(rs.getInt("id_cliente"));
				endereco.setCep(rs.getString("cep"));
				endereco.setEstado(rs.getString("estado"));
				endereco.setCidade(rs.getString("cidade"));
				endereco.setPais(rs.getString("pais"));
				
				Endereco_Tipo_Logradouro endereco_Tipo_Logradouro = new Endereco_Tipo_Logradouro();
				endereco_Tipo_Logradouro.setId(rs.getInt("id_tipo_logradouro"));
				endereco.setTipo_logradouro(endereco_Tipo_Logradouro);
				
				endereco.setLogradouro(rs.getString("logradouro"));
				endereco.setNumero(rs.getString("numero"));
				endereco.setBairro(rs.getString("bairro"));
				endereco.setDescricao(rs.getString("nome_endereco"));
				endereco.setComplemento(rs.getString("complemento"));
				endereco.setReferencia(rs.getString("referencia"));
				endereco.setPreferencial(rs.getBoolean("preferencial"));
			}

			return endereco;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			Conexao.fechar(conn);
		}
		return null;
	}

	@Override
	public List<IDominio> lista(IDominio obj) {
		
		Endereco endereco = (Endereco) obj;

		Connection conn = null;

		String sql = "SELECT * FROM \"BD_CLIENTES_ENDERECO\" WHERE id_cliente=" + endereco.getId_cliente() + " ORDER BY id;";

		try {
			conn = Conexao.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();

			List<IDominio> listaEndereco = new ArrayList<IDominio>();
			while (rs.next()) {
				endereco = new Endereco();
				
				endereco.setId(rs.getInt("id"));
				endereco.setId_cliente(rs.getInt("id_cliente"));
				endereco.setCep(rs.getString("cep"));
				endereco.setEstado(rs.getString("estado"));
				endereco.setCidade(rs.getString("cidade"));
				endereco.setPais(rs.getString("pais"));
				
				Endereco_Tipo_Logradouro endereco_Tipo_Logradouro = new Endereco_Tipo_Logradouro();
				endereco_Tipo_Logradouro.setId(rs.getInt("id_tipo_logradouro"));
				endereco.setTipo_logradouro(endereco_Tipo_Logradouro);
				
				endereco.setLogradouro(rs.getString("logradouro"));
				endereco.setNumero(rs.getString("numero"));
				endereco.setBairro(rs.getString("bairro"));
				endereco.setDescricao(rs.getString("nome_endereco"));
				endereco.setComplemento(rs.getString("complemento"));
				endereco.setReferencia(rs.getString("referencia"));
				endereco.setPreferencial(rs.getBoolean("preferencial"));
				
				listaEndereco.add(endereco);
			}

			return listaEndereco;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			Conexao.fechar(conn);
		}
		return null;
	}

}
