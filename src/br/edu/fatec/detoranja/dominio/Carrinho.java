package br.edu.fatec.detoranja.dominio;

import java.util.List;

public class Carrinho implements IDominio {
	
	private int id,							// ID do Carrinho	
		id_cliente;							// ID do cliente que pertence o carrinho
	private double valor_total,				// Valor total dos itens no pedido
		quantidade;							// Quantidade Total de livros no Carrinho
	private List<Carrinho_Itens> itens; 	// ITENS QUE PERTENCEM AO CARRINHO
	
	

	public List<Carrinho_Itens> getItens() {
		return itens;
	}
	public void setItens(List<Carrinho_Itens> itens) {
		this.itens = itens;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_cliente() {
		return id_cliente;
	}
	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}
	public double getValor_total() {
		return valor_total;
	}
	public void setValor_total(double valor_total) {
		this.valor_total = valor_total;
	}
	public double getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}
}
