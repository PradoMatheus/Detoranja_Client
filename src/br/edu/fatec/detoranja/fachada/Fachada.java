package br.edu.fatec.detoranja.fachada;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.fatec.detoranja.dao.CarrinhoDAO;
import br.edu.fatec.detoranja.dao.Carrinho_ItensDAO;
import br.edu.fatec.detoranja.dao.ClienteDAO;
import br.edu.fatec.detoranja.dao.Cliente_GeneroDAO;
import br.edu.fatec.detoranja.dao.CupomDAO;
import br.edu.fatec.detoranja.dao.EnderecoDAO;
import br.edu.fatec.detoranja.dao.Endereco_Tipo_LogradouroDAO;
import br.edu.fatec.detoranja.dao.Forma_PagamentoDAO;
import br.edu.fatec.detoranja.dao.IDAO;
import br.edu.fatec.detoranja.dao.PedidoDAO;
import br.edu.fatec.detoranja.dao.Pedido_StatusDAO;
import br.edu.fatec.detoranja.dao.ProdutoDAO;
import br.edu.fatec.detoranja.dao.TrocaDAO;
import br.edu.fatec.detoranja.dao.Troca_MotivoDAO;
import br.edu.fatec.detoranja.dao.Troca_StatusDAO;
import br.edu.fatec.detoranja.dominio.Carrinho;
import br.edu.fatec.detoranja.dominio.Carrinho_Itens;
import br.edu.fatec.detoranja.dominio.Cliente;
import br.edu.fatec.detoranja.dominio.Cliente_Genero;
import br.edu.fatec.detoranja.dominio.Cupom;
import br.edu.fatec.detoranja.dominio.Endereco;
import br.edu.fatec.detoranja.dominio.Endereco_Tipo_Logradouro;
import br.edu.fatec.detoranja.dominio.Forma_Pagamento;
import br.edu.fatec.detoranja.dominio.IDominio;
import br.edu.fatec.detoranja.dominio.Pedido;
import br.edu.fatec.detoranja.dominio.Pedido_Status;
import br.edu.fatec.detoranja.dominio.Produto;
import br.edu.fatec.detoranja.dominio.Troca;
import br.edu.fatec.detoranja.dominio.Troca_Motivo;
import br.edu.fatec.detoranja.dominio.Troca_Status;
import br.edu.fatec.detoranja.strategy.IStrategy;
import br.edu.fatec.detoranja.strategy.ValidacaoEmail;
import br.edu.fatec.detoranja.strategy.ValidarAdicaoItemExistente;
import br.edu.fatec.detoranja.strategy.ValidarCPF;
import br.edu.fatec.detoranja.strategy.ValidarExistenciaCupom;
import br.edu.fatec.detoranja.strategy.ValidarUtilizacaoCupom;
import br.edu.fatec.detoranja.util.Resultado;

public class Fachada implements IFachada {

	private Map<String, IDAO> daos;

	private Map<String, Map<String, List<IStrategy>>> rns;

	private Resultado resultado;

	public Fachada() {
		daos = new HashMap<String, IDAO>();
		rns = new HashMap<String, Map<String, List<IStrategy>>>();
		resultado = new Resultado();

		ProdutoDAO produtoDAO = new ProdutoDAO();
		ClienteDAO clienteDAO = new ClienteDAO();
		Cliente_GeneroDAO cliente_GeneroDAO = new Cliente_GeneroDAO();
		EnderecoDAO enderecoDAO = new EnderecoDAO();
		Endereco_Tipo_LogradouroDAO endereco_Tipo_LogradouroDAO = new Endereco_Tipo_LogradouroDAO();
		Forma_PagamentoDAO forma_pagamentoDAO = new Forma_PagamentoDAO();
		CarrinhoDAO carrinhoDAO = new CarrinhoDAO();
		Carrinho_ItensDAO carrinho_ItensDAO = new Carrinho_ItensDAO();
		CupomDAO cupomDAO = new CupomDAO();
		PedidoDAO pedidoDAO = new PedidoDAO();
		Pedido_StatusDAO pedido_StatusDAO = new Pedido_StatusDAO();
		TrocaDAO trocaDAO = new TrocaDAO();
		Troca_MotivoDAO troca_motivoDAO = new Troca_MotivoDAO();
		Troca_StatusDAO troca_statusDAO = new Troca_StatusDAO();

		// VINCULANDO AS DAOS AS SUAS CLASSES
		daos.put(Produto.class.getName(), produtoDAO);
		daos.put(Cliente.class.getName(), clienteDAO);
		daos.put(Cliente_Genero.class.getName(), cliente_GeneroDAO);
		daos.put(Endereco.class.getName(), enderecoDAO);
		daos.put(Endereco_Tipo_Logradouro.class.getName(), endereco_Tipo_LogradouroDAO);
		daos.put(Forma_Pagamento.class.getName(), forma_pagamentoDAO);
		daos.put(Carrinho.class.getName(), carrinhoDAO);
		daos.put(Carrinho_Itens.class.getName(), carrinho_ItensDAO);
		daos.put(Cupom.class.getName(), cupomDAO);
		daos.put(Pedido.class.getName(), pedidoDAO);
		daos.put(Pedido_Status.class.getName(), pedido_StatusDAO);
		daos.put(Troca.class.getName(), trocaDAO);
		daos.put(Troca_Motivo.class.getName(), troca_motivoDAO);
		daos.put(Troca_Status.class.getName(), troca_statusDAO);

		// REGRA DE NEGOCIO
		ValidacaoEmail validacaoEmail = new ValidacaoEmail();
		ValidarCPF validarCPF = new ValidarCPF();
		ValidarAdicaoItemExistente validarAdicaoItemExistente = new ValidarAdicaoItemExistente();
		ValidarExistenciaCupom validarExistenciaCupom = new ValidarExistenciaCupom();
		ValidarUtilizacaoCupom validarUtilizacaoCupom = new ValidarUtilizacaoCupom();

		// LISTA DE REGRA DE NEGOCIO
		// // PRODUTO
		List<IStrategy> rnSalvarProduto = new ArrayList<IStrategy>();
		List<IStrategy> rnBuscarProduto = new ArrayList<IStrategy>();
		List<IStrategy> rnListaProduto = new ArrayList<IStrategy>();
		List<IStrategy> rnExcluirProduto = new ArrayList<IStrategy>();
		// // CLIENTE
		List<IStrategy> rnSalvarCliente = new ArrayList<IStrategy>();
		List<IStrategy> rnBuscarCliente = new ArrayList<IStrategy>();
		List<IStrategy> rnListaCliente = new ArrayList<IStrategy>();
		List<IStrategy> rnExcluirCliente = new ArrayList<IStrategy>();
		// // CLIENTE GENERO
		List<IStrategy> rnSalvarClienteGenero = new ArrayList<IStrategy>();
		List<IStrategy> rnBuscarClienteGenero = new ArrayList<IStrategy>();
		List<IStrategy> rnListaClienteGenero = new ArrayList<IStrategy>();
		List<IStrategy> rnExcluirClienteGenero = new ArrayList<IStrategy>();
		// // ENDERECO
		List<IStrategy> rnSalvarEndereco = new ArrayList<IStrategy>();
		List<IStrategy> rnBuscarEndereco = new ArrayList<IStrategy>();
		List<IStrategy> rnListaEndereco = new ArrayList<IStrategy>();
		List<IStrategy> rnExcluirEndereco = new ArrayList<IStrategy>();
		// // FORMA
		List<IStrategy> rnSalvarForma = new ArrayList<IStrategy>();
		List<IStrategy> rnBuscarForma = new ArrayList<IStrategy>();
		List<IStrategy> rnListaForma = new ArrayList<IStrategy>();
		List<IStrategy> rnExcluirForma = new ArrayList<IStrategy>();
		// // CARRINHO
		List<IStrategy> rnSalvarCarrinho = new ArrayList<IStrategy>();
		List<IStrategy> rnBuscarCarrinho = new ArrayList<IStrategy>();
		List<IStrategy> rnListaCarrinho = new ArrayList<IStrategy>();
		List<IStrategy> rnExcluirCarrinho = new ArrayList<IStrategy>();
		// // ITENS CARRINHO
		List<IStrategy> rnSalvarItensCarrinho = new ArrayList<IStrategy>();
		List<IStrategy> rnBuscarItensCarrinho = new ArrayList<IStrategy>();
		List<IStrategy> rnListaItensCarrinho = new ArrayList<IStrategy>();
		List<IStrategy> rnExcluirItensCarrinho = new ArrayList<IStrategy>();
		// // CUPOM
		List<IStrategy> rnSalvarCupom = new ArrayList<IStrategy>();
		List<IStrategy> rnBuscarCupom = new ArrayList<IStrategy>();
		List<IStrategy> rnListaCupom = new ArrayList<IStrategy>();
		List<IStrategy> rnExcluirCupom = new ArrayList<IStrategy>();
		// // PEDIDO
		List<IStrategy> rnSalvarPedido = new ArrayList<IStrategy>();
		List<IStrategy> rnBuscarPedido = new ArrayList<IStrategy>();
		List<IStrategy> rnListaPedido = new ArrayList<IStrategy>();
		List<IStrategy> rnExcluirPedido = new ArrayList<IStrategy>();
		// // PEDIDO STATUS
		List<IStrategy> rnSalvarPedidoStatus = new ArrayList<IStrategy>();
		List<IStrategy> rnBuscarPedidoStatus = new ArrayList<IStrategy>();
		List<IStrategy> rnListaPedidoStatus = new ArrayList<IStrategy>();
		List<IStrategy> rnExcluirPedidoStatus = new ArrayList<IStrategy>();
		// // TROCA
		List<IStrategy> rnSalvarTroca = new ArrayList<IStrategy>();
		List<IStrategy> rnBuscarTroca = new ArrayList<IStrategy>();
		List<IStrategy> rnListaTroca = new ArrayList<IStrategy>();
		List<IStrategy> rnExcluirTroca = new ArrayList<IStrategy>();
		// // TROCA MOTIVO
		List<IStrategy> rnSalvarTrocaMotivo = new ArrayList<IStrategy>();
		List<IStrategy> rnBuscarTrocaMotivo = new ArrayList<IStrategy>();
		List<IStrategy> rnListaTrocaMotivo = new ArrayList<IStrategy>();
		List<IStrategy> rnExcluirTrocaMotivo = new ArrayList<IStrategy>();
		// // TROCA STATUS
		List<IStrategy> rnSalvarTrocaStatus = new ArrayList<IStrategy>();
		List<IStrategy> rnBuscarTrocaStatus = new ArrayList<IStrategy>();
		List<IStrategy> rnListaTrocaStatus = new ArrayList<IStrategy>();
		List<IStrategy> rnExcluirTrocaStatus = new ArrayList<IStrategy>();

		// VINCULAR AS REGRAS AO VIEW HELPER
		// // CLIENTE - SALVAR
		rnSalvarCliente.add(validacaoEmail);
		rnSalvarCliente.add(validarCPF);
		// // ITEM CARRINHO
		rnSalvarItensCarrinho.add(validarAdicaoItemExistente);
		// // CUPOM
		rnBuscarCupom.add(validarExistenciaCupom);
		rnBuscarCupom.add(validarUtilizacaoCupom);

		// LISTA REGRA DE NEGOCIO DE CADA CLASSE
		// // PRODUTO
		Map<String, List<IStrategy>> rnsproduto = new HashMap<String, List<IStrategy>>();
		rnsproduto.put("Salvar", rnSalvarProduto);
		rnsproduto.put("Buscar", rnBuscarProduto);
		rnsproduto.put("Lista", rnListaProduto);
		rnsproduto.put("Excluir", rnExcluirProduto);
		// // CLIENTE
		Map<String, List<IStrategy>> rnscliente = new HashMap<String, List<IStrategy>>();
		rnscliente.put("Salvar", rnSalvarCliente);
		rnscliente.put("Buscar", rnBuscarCliente);
		rnscliente.put("Lista", rnListaCliente);
		rnscliente.put("Excluir", rnExcluirCliente);
		// // CLIENTE
		Map<String, List<IStrategy>> rnsclientegenero = new HashMap<String, List<IStrategy>>();
		rnsclientegenero.put("Salvar", rnSalvarClienteGenero);
		rnsclientegenero.put("Buscar", rnBuscarClienteGenero);
		rnsclientegenero.put("Lista", rnListaClienteGenero);
		rnsclientegenero.put("Excluir", rnExcluirClienteGenero);
		// // ENDEREÇO
		Map<String, List<IStrategy>> rnsendereco = new HashMap<String, List<IStrategy>>();
		rnsendereco.put("Salvar", rnSalvarEndereco);
		rnsendereco.put("Buscar", rnBuscarEndereco);
		rnsendereco.put("Lista", rnListaEndereco);
		rnsendereco.put("Excluir", rnExcluirEndereco);
		// // FORMA
		Map<String, List<IStrategy>> rnsforma = new HashMap<String, List<IStrategy>>();
		rnsforma.put("Salvar", rnSalvarForma);
		rnsforma.put("Buscar", rnBuscarForma);
		rnsforma.put("Lista", rnListaForma);
		rnsforma.put("Excluir", rnExcluirForma);
		// // CARRINHO
		Map<String, List<IStrategy>> rnscarrinho = new HashMap<String, List<IStrategy>>();
		rnscarrinho.put("Salvar", rnSalvarCarrinho);
		rnscarrinho.put("Buscar", rnBuscarCarrinho);
		rnscarrinho.put("Lista", rnListaCarrinho);
		rnscarrinho.put("Excluir", rnExcluirCarrinho);
		// // ITENS CARRINHO
		Map<String, List<IStrategy>> rnsitenscarrinho = new HashMap<String, List<IStrategy>>();
		rnsitenscarrinho.put("Salvar", rnSalvarItensCarrinho);
		rnsitenscarrinho.put("Buscar", rnBuscarItensCarrinho);
		rnsitenscarrinho.put("Lista", rnListaItensCarrinho);
		rnsitenscarrinho.put("Excluir", rnExcluirItensCarrinho);
		// // ITENS CARRINHO
		Map<String, List<IStrategy>> rnsitenscupom = new HashMap<String, List<IStrategy>>();
		rnsitenscupom.put("Salvar", rnSalvarCupom);
		rnsitenscupom.put("Buscar", rnBuscarCupom);
		rnsitenscupom.put("Lista", rnListaCupom);
		rnsitenscupom.put("Excluir", rnExcluirCupom);
		// // ITENS CARRINHO
		Map<String, List<IStrategy>> rnspedido = new HashMap<String, List<IStrategy>>();
		rnspedido.put("Salvar", rnSalvarPedido);
		rnspedido.put("Buscar", rnBuscarPedido);
		rnspedido.put("Lista", rnListaPedido);
		rnspedido.put("Excluir", rnExcluirPedido);
		// // PEDIDO STATUS
		Map<String, List<IStrategy>> rnspedidostatus = new HashMap<String, List<IStrategy>>();
		rnspedidostatus.put("Salvar", rnSalvarPedidoStatus);
		rnspedidostatus.put("Buscar", rnBuscarPedidoStatus);
		rnspedidostatus.put("Lista", rnListaPedidoStatus);
		rnspedidostatus.put("Excluir", rnExcluirPedidoStatus);
		// // TROCA
		Map<String, List<IStrategy>> rnstroca = new HashMap<String, List<IStrategy>>();
		rnstroca.put("Salvar", rnSalvarTroca);
		rnstroca.put("Buscar", rnBuscarTroca);
		rnstroca.put("Lista", rnListaTroca);
		rnstroca.put("Excluir", rnExcluirTroca);
		// // TROCA MOTIVO
		Map<String, List<IStrategy>> rnstrocamotivo = new HashMap<String, List<IStrategy>>();
		rnstrocamotivo.put("Salvar", rnSalvarTrocaMotivo);
		rnstrocamotivo.put("Buscar", rnBuscarTrocaMotivo);
		rnstrocamotivo.put("Lista", rnListaTrocaMotivo);
		rnstrocamotivo.put("Excluir", rnExcluirTrocaMotivo);
		// // TROCA MOTIVO
		Map<String, List<IStrategy>> rnstrocastatus = new HashMap<String, List<IStrategy>>();
		rnstrocastatus.put("Salvar", rnSalvarTrocaStatus);
		rnstrocastatus.put("Buscar", rnBuscarTrocaStatus);
		rnstrocastatus.put("Lista", rnListaTrocaStatus);
		rnstrocastatus.put("Excluir", rnExcluirTrocaStatus);

		// ADICINADO AS LISTA DE REGRAS DE NEGOCIOS
		rns.put(Cliente.class.getName(), rnscliente);
		rns.put(Cliente_Genero.class.getName(), rnsclientegenero);
		rns.put(Endereco.class.getName(), rnsendereco);
		rns.put(Forma_Pagamento.class.getName(), rnsforma);
		rns.put(Carrinho.class.getName(), rnscarrinho);
		rns.put(Carrinho_Itens.class.getName(), rnsitenscarrinho);
		rns.put(Cupom.class.getName(), rnsitenscupom);
		rns.put(Pedido.class.getName(), rnspedido);
		rns.put(Produto.class.getName(), rnsproduto);
		rns.put(Pedido_Status.class.getName(), rnspedidostatus);
		rns.put(Troca.class.getName(), rnstroca);
		rns.put(Troca_Motivo.class.getName(), rnstrocamotivo);
		rns.put(Troca_Status.class.getName(), rnstrocastatus);
	}

	@Override
	public Resultado salvar(IDominio dominio) {
		resultado = new Resultado();
		IDAO idao = daos.get(dominio.getClass().getName());

		executarStrategys(dominio, rns.get(dominio.getClass().getName()).get("Salvar"));
		if (resultado.getMsg() == null) {
			idao.salvar(dominio);
		}
		resultado.setDominio(dominio);

		return resultado;
	}

	public Resultado buscar(IDominio dominio) {
		resultado = new Resultado();
		IDAO idao = daos.get(dominio.getClass().getName());

		executarStrategys(dominio, rns.get(dominio.getClass().getName()).get("Buscar"));
		System.out.println(resultado.getMsg());
		if (resultado.getMsg() == null) {
			resultado.setDominio(idao.buscar(dominio));
		}

		return resultado;
	}

	@Override
	public Resultado excluir(IDominio dominio) {
		// resultado = new Resultado();
		IDAO idao = daos.get(dominio.getClass().getName());

		if (resultado.getMsg() == null) {
			idao.excluir(dominio);
		}

		return resultado;
	}

	@Override
	public Resultado lista(IDominio dominio) {
		// resultado = new Resultado();
		IDAO idao = daos.get(dominio.getClass().getName());

		resultado.setListDominio(idao.lista(dominio));

		return resultado;
	}

	private void executarStrategys(IDominio entidade, List<IStrategy> strategys) {
		for (IStrategy str : strategys) {
			String mensagem = str.processar(entidade);
			if (mensagem != null) {
				resultado.setMsg(mensagem);
				return;
			}
		}
		return;
	}

	@Override
	public Resultado sair(IDominio dominio) {
		return resultado;
	}

}
