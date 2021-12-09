package br.edu.fatec.detoranja.dominio;

import java.time.LocalDateTime;
import java.util.List;

public class Troca implements IDominio {

	private int id;								// ID da Troca
	private Pedido pedido;						// pedido vinculado a troca
	private Troca_Motivo motivo; 				// Motivo da Troca
	private String observacao;					// Observaocao da Troca
	private LocalDateTime data;					// Data da solicitação da troca
	private Troca_Status status;				// Status da Troca
	private List<Troca_Itens> listTrocaItens;	// Lista de Itens da Troca
	private Cliente cliente;					// Cliente vinculado a troca
	private Cupom cupom;						// Cupom gerado no processo de troca
	private List<Troca_Log> listLogs;			// Lista de Logs da Troca
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Pedido getPedido() {
		return pedido;
	}
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	public Troca_Motivo getMotivo() {
		return motivo;
	}
	public void setMotivo(Troca_Motivo motivo) {
		this.motivo = motivo;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public LocalDateTime getData() {
		return data;
	}
	public void setData(LocalDateTime data) {
		this.data = data;
	}
	public Troca_Status getStatus() {
		return status;
	}
	public void setStatus(Troca_Status status) {
		this.status = status;
	}
	public List<Troca_Itens> getListTrocaItens() {
		return listTrocaItens;
	}
	public void setListTrocaItens(List<Troca_Itens> listTrocaItens) {
		this.listTrocaItens = listTrocaItens;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Cupom getCupom() {
		return cupom;
	}
	public void setCupom(Cupom cupom) {
		this.cupom = cupom;
	}
	public List<Troca_Log> getListLogs() {
		return listLogs;
	}
	public void setListLogs(List<Troca_Log> listLogs) {
		this.listLogs = listLogs;
	}
	
}
