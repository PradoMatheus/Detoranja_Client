package br.edu.fatec.detoranja.dominio;

public class Troca_Status implements IDominio {

	private int id;					// Id do Status
	private String descricao;		// Descrição do status
	private Troca troca;			// Troca referente ao status
	
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
	public Troca getTroca() {
		return troca;
	}
	public void setTroca(Troca troca) {
		this.troca = troca;
	}
}
