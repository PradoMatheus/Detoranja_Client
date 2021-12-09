package br.edu.fatec.detoranja.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.edu.fatec.detoranja.command.BuscarCommand;
import br.edu.fatec.detoranja.command.ExcluirCommand;
import br.edu.fatec.detoranja.command.ICommand;
import br.edu.fatec.detoranja.command.ListaCommand;
import br.edu.fatec.detoranja.command.SairCommand;
import br.edu.fatec.detoranja.command.SalvarCommand;
import br.edu.fatec.detoranja.dominio.IDominio;
import br.edu.fatec.detoranja.util.Resultado;
import br.edu.fatec.detoranja.vh.CarrinhoItensVh;
import br.edu.fatec.detoranja.vh.CarrinhoVh;
import br.edu.fatec.detoranja.vh.ClienteVh;
import br.edu.fatec.detoranja.vh.Cliente_GeneroVh;
import br.edu.fatec.detoranja.vh.CupomVh;
import br.edu.fatec.detoranja.vh.EnderecoVh;
import br.edu.fatec.detoranja.vh.Endereco_Tipo_LogradouroVh;
import br.edu.fatec.detoranja.vh.Forma_PagamentoVh;
import br.edu.fatec.detoranja.vh.IViewHelper;
import br.edu.fatec.detoranja.vh.PedidoVh;
import br.edu.fatec.detoranja.vh.Pedido_StatusVh;
import br.edu.fatec.detoranja.vh.ProdutoVh;
import br.edu.fatec.detoranja.vh.TrocaVh;
import br.edu.fatec.detoranja.vh.Troca_MotivoVh;
import br.edu.fatec.detoranja.vh.Troca_StatusVh;

@WebServlet(urlPatterns = { "/cliente", "/cliente_genero", "/produto", "/endereco", "/tipo_logradouro",
		"/forma_pagamento", "/carrinho", "/carrinho_itens", "/cupom", "/pedido", "/pedido_status", "/troca",
		"/troca_motivo", "/troca_status" })
public class Servlet extends HttpServlet {
	private Map<String, IViewHelper> mapavh;
	private Map<String, ICommand> commands;

	public Servlet() {

		mapavh = new HashMap<String, IViewHelper>();
		mapavh.put("/Detoranja_Client/cliente", new ClienteVh());
		mapavh.put("/Detoranja_Client/cliente_genero", new Cliente_GeneroVh());
		mapavh.put("/Detoranja_Client/produto", new ProdutoVh());
		mapavh.put("/Detoranja_Client/endereco", new EnderecoVh());
		mapavh.put("/Detoranja_Client/tipo_logradouro", new Endereco_Tipo_LogradouroVh());
		mapavh.put("/Detoranja_Client/forma_pagamento", new Forma_PagamentoVh());
		mapavh.put("/Detoranja_Client/carrinho", new CarrinhoVh());
		mapavh.put("/Detoranja_Client/carrinho_itens", new CarrinhoItensVh());
		mapavh.put("/Detoranja_Client/cupom", new CupomVh());
		mapavh.put("/Detoranja_Client/pedido", new PedidoVh());
		mapavh.put("/Detoranja_Client/pedido_status", new Pedido_StatusVh());
		mapavh.put("/Detoranja_Client/troca", new TrocaVh());
		mapavh.put("/Detoranja_Client/troca_motivo", new Troca_MotivoVh());
		mapavh.put("/Detoranja_Client/troca_status", new Troca_StatusVh());

		commands = new HashMap<String, ICommand>();
		commands.put("Salvar", new SalvarCommand());
		commands.put("Buscar", new BuscarCommand());
		commands.put("Lista", new ListaCommand());
		commands.put("Excluir", new ExcluirCommand());
		commands.put("Sair", new SairCommand());
	}

	private void executar(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		IViewHelper iViewHelper = mapavh.get(req.getRequestURI());
		IDominio dominio = iViewHelper.getDominio(req);
		String operacao = req.getParameter("operacao");
		ICommand command = commands.get(operacao);
		Resultado resultado = command.execute(dominio);
		iViewHelper.setDominio(req, resp, resultado);
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		executar(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		executar(req, resp);
	}

}
