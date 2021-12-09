package br.edu.fatec.detoranja.dominio;

public class Produto_Autor implements IDominio {

	int id;
	String nome;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
