package br.edu.fatec.detoranja.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.edu.fatec.detoranja.dominio.Cliente;
import br.edu.fatec.detoranja.dominio.Cupom;
import br.edu.fatec.detoranja.dominio.Cupom_Tipo;
import br.edu.fatec.detoranja.dominio.IDominio;

public class CupomDAO implements IDAO {

	@Override
	public boolean salvar(IDominio obj) {
		return false;
	}

	@Override
	public boolean excluir(IDominio obj) {
		return false;
	}

	@Override
	public IDominio buscar(IDominio obj) {
		Cupom cupom = (Cupom) obj;

		Connection conn = null;

		String sql = "SELECT * FROM \"BD_CUPONS\" WHERE cod_cupom = '" + cupom.getDesc_cupom() + "';";

		try {
			conn = Conexao.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				cupom = new Cupom();

				cupom.setId(rs.getInt("id"));
				cupom.setAtivo(rs.getBoolean("ativo"));
				cupom.setDesconto(rs.getDouble("desconto"));
				cupom.setValor_minimo(rs.getDouble("valor_minimo"));
				cupom.setValidade(rs.getObject("validade", LocalDate.class));
				cupom.setDesc_cupom(rs.getString("cod_cupom"));

				Cliente cliente = new Cliente();
				if (rs.getObject("cliente") != null || rs.getInt("cliente") != 0)
					cliente.setId(rs.getInt("cliente"));
				cupom.setCliente(cliente);

				Cupom_Tipo tipo = new Cupom_Tipo();
				if (rs.getObject("tipo") != null || rs.getInt("tipo") != 0)
					tipo.setId(rs.getInt("tipo"));
				cupom.setTipo(tipo);

				return cupom;
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
		Cupom cupom = (Cupom) obj;

		String sql = "SELECT CUPOM.id, CUPOM.ativo, CUPOM.desconto, CUPOM.valor_minimo, CUPOM.validade, CUPOM.cod_cupom, CUPOM.cliente, CUPOM.tipo, UTILIZACAO.id_pedido "
				+ "FROM \"BD_CUPONS\" CUPOM "
				+ "LEFT JOIN \"BD_CUPONS_UTILIZACAO\" UTILIZACAO ON CUPOM.id = UTILIZACAO.id_cupom "
				+ "LEFT JOIN \"BD_PEDIDO\" PEDIDO ON PEDIDO.id = UTILIZACAO.id_pedido "
				+ "WHERE (tipo = 2 OR (tipo = 1 AND cliente = " + cupom.getCliente().getId() + " )) AND (UTILIZACAO.id_pedido is null OR PEDIDO.id_cliente = " + cupom.getCliente().getId() + ")";
		
		Connection conn = null;

		try {
			conn = Conexao.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			
			List<IDominio> listaCupons = new ArrayList<IDominio>();
			while(rs.next()) {
				cupom = new Cupom();
				
				cupom.setId(rs.getInt("id"));
				cupom.setAtivo(rs.getBoolean("ativo"));
				cupom.setDesconto(rs.getDouble("desconto"));
				cupom.setValor_minimo(rs.getDouble("valor_minimo"));
				cupom.setValidade(rs.getObject("validade", LocalDate.class));
				cupom.setDesc_cupom(rs.getString("cod_cupom"));
				cupom.setDisponibilidade(String.valueOf(rs.getInt("id_pedido")));

				Cliente cliente = new Cliente();
				if (rs.getObject("cliente") != null || rs.getInt("cliente") != 0)
					cliente.setId(rs.getInt("cliente"));
				cupom.setCliente(cliente);

				Cupom_Tipo tipo = new Cupom_Tipo();
				if (rs.getObject("tipo") != null || rs.getInt("tipo") != 0)
					tipo.setId(rs.getInt("tipo"));
				cupom.setTipo(tipo);
				
				listaCupons.add(cupom);
			}

			return listaCupons;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			Conexao.fechar(conn);
		}

		return null;
	}

}
