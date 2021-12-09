package br.edu.fatec.detoranja.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.edu.fatec.detoranja.dominio.IDominio;
import br.edu.fatec.detoranja.dominio.Produto;
import br.edu.fatec.detoranja.dominio.Produto_Autor;
import br.edu.fatec.detoranja.dominio.Produto_Categoria;
import br.edu.fatec.detoranja.dominio.Produto_Dimensao;
import br.edu.fatec.detoranja.dominio.Produto_Editora;
import br.edu.fatec.detoranja.dominio.Produto_Idioma;
import br.edu.fatec.detoranja.dominio.Produto_Imagens;
import br.edu.fatec.detoranja.dominio.Produto_Precificacao;

public class ProdutoDAO implements IDAO {

	@Override
	public boolean salvar(IDominio obj) {
		return false;
	}

	@Override
	public boolean excluir(IDominio obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IDominio buscar(IDominio obj) {
		Produto produto = (Produto) obj;

		Connection conn = null;

		/*String sql = "SELECT *, (SELECT path_imagem FROM \"BD_PRODUTOS_IMAGENS\" WHERE id_produto = PRODUTOS.id LIMIT 1),"
				+ "(SELECT descricao FROM \"BD_PRODUTOS_CATEGORIA\" WHERE id = PRODUTOS.categoria LIMIT 1) AS desc_cat, "
				+ "(SELECT descricao FROM \"BD_PRODUTOS_AUTOR\" WHERE id = PRODUTOS.autor LIMIT 1) AS desc_autor, "
				+ "(SELECT lucro FROM \"BD_PRODUTOS_PRECIFICACAO\" WHERE id = PRODUTOS.precificacao LIMIT 1) AS lucro "
				+ "FROM \"BD_PRODUTOS\" AS PRODUTOS WHERE id=" + produto.getId() + ";";*/
		
		String sql = "SELECT PRODUTO.id AS id_prod, PRODUTO.titulo AS tit_prod, PRODUTO.valor AS val_prod, PRODUTO.data_alteracao AS alt_prod, "
				+ "PRODUTO.data_cadastro AS cad_prod, PRODUTO.data_lancamento AS lan_prod, PRODUTO.sinopse as sin_prod, PRODUTO.edicao as edi_prod, "
				+ "PRODUTO.\"ISBN\" AS isbn_prod, PRODUTO.numero_paginas AS num_prod, PRODUTO.codigo_barras as cod_prod, AUTOR.id as id_autor, "
				+ "AUTOR.descricao AS desc_autor, EDITORA.id AS id_edit, EDITORA.descricao AS desc_edit, CATEGORIA.id AS id_cat, CATEGORIA.descricao AS desc_cat, "
				+ "PRECIFICACAO.id AS id_pref, PRECIFICACAO.lucro AS luc_pref, PRECIFICACAO.descricao AS desc_pref, IDIOMA.id AS id_idi, IDIOMA.descricao as desc_idi, "
				+ "DIMENSAO.id_produto AS id_dim, DIMENSAO.altura AS alt_dim, DIMENSAO.largura AS lar_dim, DIMENSAO.peso AS pes_dim, DIMENSAO.profundidade AS pro_dim, ESTOQUE.estoque AS pro_est "
				+ "FROM \"BD_PRODUTOS\" AS PRODUTO "
				+ "JOIN \"BD_PRODUTOS_CATEGORIA\" AS CATEGORIA ON PRODUTO.categoria = CATEGORIA.id "
				+ "JOIN \"BD_PRODUTOS_AUTOR\" AS AUTOR ON PRODUTO.autor = AUTOR.id "
				+ "JOIN \"BD_PRODUTOS_DIMENSAO\" AS DIMENSAO ON DIMENSAO.id_produto = PRODUTO.id "
				+ "JOIN \"BD_PRODUTOS_EDITORA\" AS EDITORA ON EDITORA.id = PRODUTO.editora "
				+ "JOIN \"BD_PRODUTOS_IDIOMA\" AS IDIOMA ON IDIOMA.id = PRODUTO.idioma "
				+ "JOIN \"BD_PRODUTOS_PRECIFICACAO\" AS PRECIFICACAO ON PRECIFICACAO.id = PRODUTO.precificacao "
				+ "JOIN \"BD_ESTOQUE\" AS ESTOQUE ON PRODUTO.id = ESTOQUE.id_produto "
				+ "WHERE PRODUTO.id=" + produto.getId() + ";";

		try {
			conn = Conexao.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				produto = new Produto();

				produto.setId(rs.getInt("id_prod"));
				produto.setTitulo(rs.getString("tit_prod"));
				produto.setValor(rs.getDouble("val_prod"));
				produto.setData_alteracao(rs.getObject("alt_prod", LocalDateTime.class));
				produto.setData_cadastro(rs.getObject("cad_prod", LocalDateTime.class));
				produto.setData_lancamento(rs.getObject("lan_prod", LocalDate.class));
				produto.setSinopse(rs.getString("sin_prod"));
				produto.setEdicao(rs.getString("edi_prod"));
				produto.setISBN(rs.getString("isbn_prod"));
				produto.setNumero_paginas(rs.getInt("num_prod"));
				produto.setCodigo_barra(rs.getString("cod_prod"));
				produto.setQtde_estoque(rs.getInt("pro_est"));

				Produto_Autor autor = new Produto_Autor();
				autor.setId(rs.getInt("id_autor"));
				autor.setNome(rs.getString("desc_autor"));
				produto.setAutor(autor);

				Produto_Editora editora = new Produto_Editora();
				editora.setId(rs.getInt("id_edit"));
				editora.setDescricao(rs.getString("desc_edit"));
				produto.setEditora(editora);

				Produto_Categoria categoria = new Produto_Categoria();
				categoria.setId(rs.getInt("id_cat"));
				categoria.setDescricao(rs.getString("desc_cat"));
				produto.setCategoria(categoria);

				Produto_Precificacao precificacao = new Produto_Precificacao();
				precificacao.setId(rs.getInt("id_pref"));
				precificacao.setLucro(rs.getDouble("luc_pref"));
				precificacao.setSigla(rs.getString("desc_pref"));
				produto.setPrecificacao(precificacao);

				Produto_Idioma idioma = new Produto_Idioma();
				idioma.setId(rs.getInt("id_idi"));
				idioma.setDescricao(rs.getString("desc_idi"));
				produto.setIdioma(idioma);

				Produto_Dimensao dimensao = new Produto_Dimensao();
				dimensao.setId(rs.getInt("id_dim"));
				dimensao.setAltura(rs.getDouble("alt_dim"));
				dimensao.setLargura(rs.getDouble("lar_dim"));
				dimensao.setPeso(rs.getDouble("pes_dim"));
				dimensao.setProfundidade(rs.getDouble("pro_dim"));
				produto.setDimensao(dimensao);

				Produto_Imagens imagem = new Produto_Imagens();
				imagem.setId_produto(rs.getInt("id_prod"));
				List<Produto_Imagens> listaImagens = new ArrayList<Produto_Imagens>();
				for (IDominio d : new Produto_ImagemDAO().lista(imagem)) {
					listaImagens.add((Produto_Imagens) d);
				}
				produto.setImagens(listaImagens);

				return produto;
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
		Produto produto = (Produto) obj;

		Connection conn = null;

		String sql = "SELECT *, (SELECT path_imagem FROM \"BD_PRODUTOS_IMAGENS\" WHERE id_produto = PRODUTOS.id LIMIT 1),"
				+ "(SELECT descricao FROM \"BD_PRODUTOS_CATEGORIA\" WHERE id = PRODUTOS.categoria LIMIT 1) AS desc_cat, "
				+ "(SELECT lucro FROM \"BD_PRODUTOS_PRECIFICACAO\" WHERE id = PRODUTOS.precificacao LIMIT 1) AS lucro, "
				+ "(SELECT estoque FROM \"BD_ESTOQUE\" WHERE id_produto = PRODUTOS.id LIMIT 1) AS estoque "
				+ "FROM \"BD_PRODUTOS\" AS PRODUTOS ";
		
		if(produto.getIdioma() != null || produto.getCategoria() != null) {
			sql += "WHERE ";
			if(produto.getIdioma() != null)
				sql += "PRODUTOS.idioma = " + produto.getIdioma().getId();
			if(produto.getCategoria() != null)
				sql += "PRODUTOS.categoria = " + produto.getCategoria().getId();
		}		
		
		sql += ";";

		try {
			conn = Conexao.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();

			List<IDominio> listaProdutos = new ArrayList<IDominio>();
			while (rs.next()) {
				produto = new Produto();

				produto.setId(rs.getInt("id"));
				produto.setTitulo(rs.getString("titulo"));
				produto.setValor(rs.getDouble("valor"));
				produto.setData_alteracao(rs.getObject("data_alteracao", LocalDateTime.class));
				produto.setData_cadastro(rs.getObject("data_cadastro", LocalDateTime.class));
				produto.setData_lancamento(rs.getObject("data_lancamento", LocalDate.class));
				produto.setSinopse(rs.getString("sinopse"));
				produto.setEdicao(rs.getString("edicao"));
				produto.setISBN(rs.getString("ISBN"));
				produto.setNumero_paginas(rs.getInt("numero_paginas"));
				produto.setCodigo_barra(rs.getString("codigo_barras"));

				Produto_Categoria categoria = new Produto_Categoria();
				categoria.setId(rs.getInt("categoria"));
				categoria.setDescricao(rs.getString("desc_cat"));
				produto.setCategoria(categoria);

				Produto_Precificacao precificacao = new Produto_Precificacao();
				precificacao.setLucro(rs.getDouble("lucro"));
				produto.setPrecificacao(precificacao);

				Produto_Imagens imagem = new Produto_Imagens();
				imagem.setPath(rs.getString("path_imagem"));
				List<Produto_Imagens> listaImagens = new ArrayList<Produto_Imagens>();
				listaImagens.add(imagem);
				produto.setImagens(listaImagens);
				
				produto.setQtde_estoque(rs.getInt("estoque"));


				/*
				 * Produto_Autor autor = new Produto_Autor(); autor.setId(rs.getInt("autor"));
				 * produto.setAutor((Produto_Autor)new Produto_AutorDAO().buscar(autor));
				 */

				/*
				 * Produto_Editora editora = new Produto_Editora();
				 * editora.setId(rs.getInt("editora")); produto.setEditora((Produto_Editora)new
				 * Produto_EditoraDAO().buscar(editora));
				 */

				/*
				 * Produto_Categoria categoria = new Produto_Categoria();
				 * categoria.setId(rs.getInt("categoria"));
				 * produto.setCategoria((Produto_Categoria) new
				 * Produto_CategoriaDAO().buscar(categoria));
				 */

				/*
				 * Produto_Precificacao precificacao = new Produto_Precificacao();
				 * precificacao.setId(rs.getInt("precificacao"));
				 * produto.setPrecificacao((Produto_Precificacao) new
				 * Produto_PrecificacaoDAO().buscar(precificacao));
				 */

				/*
				 * Produto_Idioma idioma = new Produto_Idioma();
				 * idioma.setId(rs.getInt("idioma")); produto.setIdioma((Produto_Idioma) new
				 * Produto_IdiomaDAO().buscar(idioma));
				 */

				/*
				 * Produto_Dimensao dimensao = new Produto_Dimensao();
				 * dimensao.setId(rs.getInt("id")); produto.setDimensao((Produto_Dimensao) new
				 * Produto_DimensaoDAO().buscar(dimensao));
				 */

				/*
				 * Produto_Imagens imagem = new Produto_Imagens();
				 * imagem.setId_produto(rs.getInt("id")); List<Produto_Imagens> listaImagens =
				 * new ArrayList<Produto_Imagens>(); for(IDominio d : new
				 * Produto_ImagemDAO().lista(imagem)) { listaImagens.add((Produto_Imagens) d); }
				 * produto.setImagens(listaImagens);
				 */

				listaProdutos.add(produto);
			}

			return listaProdutos;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			Conexao.fechar(conn);
		}
		return null;
	}

}
