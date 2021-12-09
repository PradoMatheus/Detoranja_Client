package br.edu.fatec.detoranja.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;

import br.edu.fatec.detoranja.dominio.Cliente;
import br.edu.fatec.detoranja.dominio.Cliente_Genero;
import br.edu.fatec.detoranja.dominio.IDominio;

public class ClienteDAO implements IDAO {

	@Override
	public boolean salvar(IDominio obj) {
		Cliente cliente = (Cliente) obj;

		String sql;

		if (cliente.getId() != 0)
			sql = "UPDATE \"BD_CLIENTES\" "
					+ "SET nome = ?, email = ?, senha = ?, cpf = ?, data_nascimento = ? , ativo = ?, id_genero = ?, telefone = ?"
					+ "WHERE id = " + cliente.getId() + ";";
		else
			sql = "INSERT INTO \"BD_CLIENTES\" " + "(nome, email, senha, cpf, data_nascimento, ativo, id_genero, telefone) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

		Connection conn = null;

		try {
			conn = Conexao.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, cliente.getNome());
			pstm.setString(2, cliente.getEmail());
			pstm.setString(3, cliente.getSenha());
			pstm.setString(4, cliente.getCpf());
			pstm.setDate(5, Date.valueOf(cliente.getData_nascimento()));
			pstm.setBoolean(6, true);
			pstm.setInt(7, cliente.getGenero().getId());
			pstm.setLong(8, cliente.getTelefone());
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IDominio buscar(IDominio obj) {
		Cliente cliente = (Cliente) obj;

		String sql;
		if (cliente.getId() != 0)
			sql = "SELECT *, (SELECT id FROM \"BD_CLIENTES_GENERO\" WHERE id = \"BD_CLIENTES\".id_genero) AS genero FROM \"BD_CLIENTES\" "
					+ "WHERE id = '" + cliente.getId() + "';";
		else
			sql = "SELECT *, (SELECT id FROM \"BD_CLIENTES_GENERO\" WHERE id = \"BD_CLIENTES\".id_genero) AS genero FROM \"BD_CLIENTES\" "
					+ "WHERE email = '" + cliente.getEmail() + "' AND senha = '" + cliente.getSenha() + "';";

		Connection conn = null;

		try {
			conn = Conexao.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				cliente.setId(rs.getInt("id"));
				cliente.setCpf(rs.getString("cpf"));
				cliente.setNome(rs.getString("nome"));
				cliente.setData_nascimento(rs.getObject("data_nascimento", LocalDate.class));
				cliente.setEmail(rs.getString("email"));
				cliente.setSenha(rs.getString("senha"));
				cliente.setAtivo(rs.getBoolean("ativo"));
				cliente.setTelefone(rs.getLong("telefone"));

				Cliente_Genero genero = new Cliente_Genero();
				genero.setId(rs.getInt("genero"));
				cliente.setGenero(genero);
			}

			return cliente;
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
