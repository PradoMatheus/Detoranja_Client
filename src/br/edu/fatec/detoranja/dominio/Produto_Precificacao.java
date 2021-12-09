package br.edu.fatec.detoranja.dominio;

public class Produto_Precificacao implements IDominio {

	int id;					// ID DA PREFICICAÇÃO
	double lucro;			// PORCENTAGEM DE LUCRO EM CIMA DO VALOR DE COMPRA
	String sigla;			// SIGLA DA PRECIFICAÇÃO
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getLucro() {
		return lucro;
	}
	public void setLucro(double lucro) {
		this.lucro = lucro;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
}
