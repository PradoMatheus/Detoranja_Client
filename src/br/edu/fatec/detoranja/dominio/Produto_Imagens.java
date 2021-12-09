package br.edu.fatec.detoranja.dominio;

public class Produto_Imagens implements IDominio{
	int id,						// ID DA IMAGEM
		id_produto;				// ID DO PRODUTO REPRESENTADO NA IMAGEM
	String path;				// PATH DO CAMINHO DA IMAGEM
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_produto() {
		return id_produto;
	}
	public void setId_produto(int id_produto) {
		this.id_produto = id_produto;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}
