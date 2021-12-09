package br.edu.fatec.detoranja.vh;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import br.edu.fatec.detoranja.dominio.Cliente;
import br.edu.fatec.detoranja.dominio.Forma_Pagamento;
import br.edu.fatec.detoranja.dominio.IDominio;
import br.edu.fatec.detoranja.util.Resultado;

public class Forma_PagamentoVh implements IViewHelper {

	@Override
	public IDominio getDominio(HttpServletRequest req) {
		String operacao = req.getParameter("operacao");

		Forma_Pagamento forma = new Forma_Pagamento();

		if (operacao != null && operacao.equals("Salvar")) {

			try {
				JsonObject data = new Gson().fromJson(req.getReader(), JsonObject.class);

				forma.setId(Integer.parseInt(data.get("txtId").getAsString()));

				Cliente cliente = new Cliente();
				cliente.setId(Integer.parseInt(req.getSession().getAttribute("ClientUser").toString()));

				forma.setCliente(cliente);
				forma.setBandeira(data.get("txtBandeira").getAsString());
				forma.setNumero_cartao(Long.parseLong(data.get("txtNumeroCartao").getAsString().replaceAll(" ", "")));
				forma.setAno_validade(Integer.parseInt(data.get("txtAnoValidade").getAsString()));
				forma.setMes_validade(Integer.parseInt(data.get("txtMesValidade").getAsString()));
				forma.setCvv(Integer.parseInt(data.get("txtCvv").getAsString()));
				forma.setNome(data.get("txtNome").getAsString());
				forma.setPreferencal(Boolean.parseBoolean(data.get("txtPreferencia").getAsString()));
			} catch (JsonSyntaxException | JsonIOException | IOException e) {
				e.printStackTrace();
			}
		} else if (operacao != null && operacao.equals("Lista")) {
			Cliente cliente = new Cliente();
			cliente.setId(Integer.parseInt(req.getSession().getAttribute("ClientUser").toString()));

			forma.setCliente(cliente);
		} else if (operacao != null && operacao.equals("Buscar")) {
			forma.setId(Integer.parseInt(req.getParameter("id")));
		} else if (operacao != null && operacao.equals("Excluir")) {
			forma.setId(Integer.parseInt(req.getParameter("id")));
		}

		return forma;
	}

	@Override
	public void setDominio(HttpServletRequest req, HttpServletResponse resp, Resultado resultado) {
		String operacao = req.getParameter("operacao");

		if (operacao != null && operacao.equals("Salvar")) {
			String json = new Gson().toJson("Sucesso!");

			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");

			try {
				resp.getWriter().write(json);
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		} else if (operacao != null && operacao.equals("Lista")) {
			if (req.getParameter("s").equals("java")) {
				List<Forma_Pagamento> listaForma = new ArrayList<Forma_Pagamento>();
				for (IDominio d : resultado.getListDominio()) {
					listaForma.add((Forma_Pagamento) d);
				}

				try {
					req.setAttribute("listaFormasPagamento", listaForma);
					req.getRequestDispatcher("usuario_formas_pagamentos.jsp").forward(req, resp);
				} catch (ServletException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (req.getParameter("s").equals("json")) {
				String json = new Gson().toJson(resultado.getListDominio());

				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");

				try {
					resp.getWriter().write(json);
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		} else if (operacao != null && operacao.equals("Buscar")) {
			String json = new Gson().toJson(resultado.getDominio());

			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");

			try {
				resp.getWriter().write(json);
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		} else if (operacao != null && operacao.equals("Excluir")) {
			String json = new Gson().toJson("Exclusão realizada com sucesso !!");

			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");

			try {
				resp.getWriter().write(json);
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}

		}
	}

}
