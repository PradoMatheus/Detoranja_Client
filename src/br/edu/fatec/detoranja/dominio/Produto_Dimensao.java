package br.edu.fatec.detoranja.dominio;

public class Produto_Dimensao implements IDominio{
	
	int id_produto;					// ID DO PRODUTO
	Double altura,					// ALTURA DO LIVRO
		largura,					// LARGURA DO LIVRO
		peso,						// PESO DO LIVRO
		profundidade;				// PROFUNDIDADE DO LIVRO
	
	public int getId() {
		return id_produto;
	}
	public void setId(int id_produto) {
		this.id_produto = id_produto;
	}
	public Double getAltura() {
		return altura;
	}
	public void setAltura(Double altura) {
		this.altura = altura;
	}
	public Double getLargura() {
		return largura;
	}
	public void setLargura(Double largura) {
		this.largura = largura;
	}
	public Double getPeso() {
		return peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	public Double getProfundidade() {
		return profundidade;
	}
	public void setProfundidade(Double profundidade) {
		this.profundidade = profundidade;
	}
}
