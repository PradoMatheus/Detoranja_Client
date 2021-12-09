package br.edu.fatec.detoranja.dominio;

public class Carrinho_Itens implements IDominio{
	
	private int id,					// ID UNICO DOS ITENS DO PEDIDO
		quantidade;			// QUANTIDADE DE ITENS NO CARRINHO
	private Produto produto;		// PRODUTO NO CARRINHO
	private Carrinho carrinho;		// CARRINHO DO ITEM
	private int estoque;			// ESTOQUE DO ITEM NO CARRINHO
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	public Carrinho getCarrinho() {
		return carrinho;
	}
	public void setCarrinho(Carrinho carrinho) {
		this.carrinho = carrinho;
	}
	public int getEstoque() {
		return estoque;
	}
	public void setEstoque(int estoque) {
		this.estoque = estoque;
	}
}
