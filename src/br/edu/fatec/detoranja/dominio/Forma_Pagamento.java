package br.edu.fatec.detoranja.dominio;

public class Forma_Pagamento implements IDominio{
	int id, 					// Id do cart�o
		ano_validade,			// Ano de Validade do Cart�o
		mes_validade,			// Mes de Validade do Cart�o
		cvv,					// CVV do cart�o
		qtde_parcela;			// Quantidade de Parcela no pedido
	Long numero_cartao;			// Numero do cart�o de Credito
	double valor_parcela;		// Valor da Parcela do Cart�o
	Cliente cliente;			// Cliente que pertence o cart�o
	String bandeira,			// bandeira do Cartao
		nome;					// Nome Vinculado ao cart�o de credito
	boolean preferencal;		// O cart�o preferido do cliente
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAno_validade() {
		return ano_validade;
	}
	public void setAno_validade(int ano_validade) {
		this.ano_validade = ano_validade;
	}
	public int getMes_validade() {
		return mes_validade;
	}
	public void setMes_validade(int mes_validade) {
		this.mes_validade = mes_validade;
	}
	public int getCvv() {
		return cvv;
	}
	public void setCvv(int cvv) {
		this.cvv = cvv;
	}
	public Long getNumero_cartao() {
		return numero_cartao;
	}
	public void setNumero_cartao(Long numero_cartao) {
		this.numero_cartao = numero_cartao;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public String getBandeira() {
		return bandeira;
	}
	public void setBandeira(String bandeira) {
		this.bandeira = bandeira;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public boolean isPreferencal() {
		return preferencal;
	}
	public void setPreferencal(boolean preferencal) {
		this.preferencal = preferencal;
	}
	public int getQtde_parcela() {
		return qtde_parcela;
	}
	public void setQtde_parcela(int qtde_parcela) {
		this.qtde_parcela = qtde_parcela;
	}
	public double getValor_parcela() {
		return valor_parcela;
	}
	public void setValor_parcela(double valor_parcela) {
		this.valor_parcela = valor_parcela;
	}
}
