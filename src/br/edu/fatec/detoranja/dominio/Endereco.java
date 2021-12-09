package br.edu.fatec.detoranja.dominio;

public class Endereco implements IDominio{
	
	int id,											// Id do Endere�o
		id_cliente;									// Id do cliente que � vinculado o endere�o	
	String descricao,				 				// Descri��o ou Apelido do Endere�o
			bairro,									// Bairro do Endere�o
			estado,									// Estado do Endereco
			cidade,									// Cidade do Endere�o
			pais,									// Pais do Endere�o
			cep,									// Cep do Endere�o
			logradouro,								// Rua do Endere�o
			numero,									// Numero do Endere�o
			complemento,							// Complemento do Endereco
			referencia;								// Referenca do Endere�o
	boolean preferencial;							// Indica se o endere�o � o preferencial do usuario
	
	public boolean isPreferencial() {
		return preferencial;
	}
	public void setPreferencial(boolean preferencial) {
		this.preferencial = preferencial;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	Endereco_Tipo_Logradouro tipo_logradouro;		// Tipo do Logradouro do Endere�o
	
	public int getId_cliente() {
		return id_cliente;
	}
	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public Endereco_Tipo_Logradouro getTipo_logradouro() {
		return tipo_logradouro;
	}
	public void setTipo_logradouro(Endereco_Tipo_Logradouro tipo_logradouro) {
		this.tipo_logradouro = tipo_logradouro;
	}
}
