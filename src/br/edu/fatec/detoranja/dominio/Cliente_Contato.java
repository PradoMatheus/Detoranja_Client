package br.edu.fatec.detoranja.dominio;

public class Cliente_Contato implements IDominio {

	private int id, 		// ID do contato
		telefone;			// Telefone do Contato
	private String nome;	// Nome do Contato
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTelefone() {
		return telefone;
	}
	public void setTelefone(int telefone) {
		this.telefone = telefone;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}	
}
