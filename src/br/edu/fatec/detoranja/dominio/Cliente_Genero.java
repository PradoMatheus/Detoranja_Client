package br.edu.fatec.detoranja.dominio;

public class Cliente_Genero implements IDominio{
	
	private int id;					// ID do Genero
	private String descricao;		// Descricao do Genero
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
