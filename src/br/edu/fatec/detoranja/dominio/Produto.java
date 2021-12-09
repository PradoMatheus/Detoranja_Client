package br.edu.fatec.detoranja.dominio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Produto implements IDominio {

	private int id, // PK DO LIVRO
			numero_paginas; // NUMERO DE PAGINAS DO LIVRO
	private String titulo, // TITULO DO LIVRO
			sinopse, // SINOPSE DO LIVRO
			ISBN, // ISBN DO LIVRO
			edicao, // EDIÇÃO DO LIVRO
			codigo_barra; // CODIGO DE BARRA DO LIVRO
	private double valor; // VALOR DE VENDA DO LIVRO
	private LocalDate data_lancamento; // DATA DE LANÇAMENTO DO LIVRO
	private LocalDateTime data_cadastro, // DATA DE CADASTRO DO LIVRO
			data_alteracao; // DATA DE ALTERAÇÃO DO LIVRO
	private Produto_Dimensao dimensao; // DIMENSÃO DO LIVRO
	private Produto_Categoria categoria; // CATEGORIA DO LIVRO
	private Produto_Idioma idioma; // IDIOMA DO LIVRO
	private Produto_Autor autor; // AUTOR DO LIVRO
	private Produto_Precificacao precificacao; // PRECIFICAÇÃO DO LIVRO
	private Produto_Editora editora; // EDITORA DO LIVRO
	private List<Produto_Imagens> imagens; // IMAGENS DO PRODUTO
	private int qtde_estoque;

	public List<Produto_Imagens> getImagens() {
		return imagens;
	}

	public void setImagens(List<Produto_Imagens> imagens) {
		this.imagens = imagens;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumero_paginas() {
		return numero_paginas;
	}

	public void setNumero_paginas(int numero_paginas) {
		this.numero_paginas = numero_paginas;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getSinopse() {
		return sinopse;
	}

	public void setSinopse(String sinopse) {
		this.sinopse = sinopse;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public String getEdicao() {
		return edicao;
	}

	public void setEdicao(String edicao) {
		this.edicao = edicao;
	}

	public String getCodigo_barra() {
		return codigo_barra;
	}

	public void setCodigo_barra(String codigo_barra) {
		this.codigo_barra = codigo_barra;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public LocalDate getData_lancamento() {
		return data_lancamento;
	}

	public void setData_lancamento(LocalDate data_lancamento) {
		this.data_lancamento = data_lancamento;
	}

	public LocalDateTime getData_cadastro() {
		return data_cadastro;
	}

	public void setData_cadastro(LocalDateTime data_cadastro) {
		this.data_cadastro = data_cadastro;
	}

	public LocalDateTime getData_alteracao() {
		return data_alteracao;
	}

	public void setData_alteracao(LocalDateTime data_alteracao) {
		this.data_alteracao = data_alteracao;
	}

	public Produto_Dimensao getDimensao() {
		return dimensao;
	}

	public void setDimensao(Produto_Dimensao dimensao) {
		this.dimensao = dimensao;
	}

	public Produto_Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Produto_Categoria categoria) {
		this.categoria = categoria;
	}

	public Produto_Idioma getIdioma() {
		return idioma;
	}

	public void setIdioma(Produto_Idioma idioma) {
		this.idioma = idioma;
	}

	public Produto_Autor getAutor() {
		return autor;
	}

	public void setAutor(Produto_Autor autor) {
		this.autor = autor;
	}

	public Produto_Precificacao getPrecificacao() {
		return precificacao;
	}

	public void setPrecificacao(Produto_Precificacao precificacao) {
		this.precificacao = precificacao;
	}

	public Produto_Editora getEditora() {
		return editora;
	}

	public void setEditora(Produto_Editora editora) {
		this.editora = editora;
	}

	public int getQtde_estoque() {
		return qtde_estoque;
	}

	public void setQtde_estoque(int qtde_estoque) {
		this.qtde_estoque = qtde_estoque;
	}
}
