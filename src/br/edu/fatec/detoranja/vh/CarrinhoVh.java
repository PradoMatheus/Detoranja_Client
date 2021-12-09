package br.edu.fatec.detoranja.vh;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import br.edu.fatec.detoranja.dominio.Carrinho;
import br.edu.fatec.detoranja.dominio.Carrinho_Itens;
import br.edu.fatec.detoranja.dominio.IDominio;
import br.edu.fatec.detoranja.util.Resultado;

public class CarrinhoVh implements IViewHelper {

	@Override
	public IDominio getDominio(HttpServletRequest req) {
		String operacao = req.getParameter("operacao");

		Carrinho carrinho = new Carrinho();

		if (operacao != null && operacao.equals("Buscar")) {
			if (req.getSession().getAttribute("ClientUser") != null)
				carrinho.setId_cliente(Integer.parseInt(req.getSession().getAttribute("ClientUser").toString()));
		}

		return carrinho;
	}

	@Override
	public void setDominio(HttpServletRequest req, HttpServletResponse resp, Resultado resultado) {
		String operacao = req.getParameter("operacao");

		if (operacao != null && operacao.equals("Buscar")) {
			if (req.getSession().getAttribute("ClientUser") != null) {
				if (req.getParameter("s").equals("cart"))
					try {
						req.setAttribute("Carrinho", (Carrinho) resultado.getDominio());
						req.getRequestDispatcher("cart.jsp").forward(req, resp);
					} catch (ServletException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				else if (req.getParameter("s").equals("checkout")) {
					try {
						req.setAttribute("Carrinho", (Carrinho) resultado.getDominio());
						req.getRequestDispatcher("checkout.jsp").forward(req, resp);
					} catch (ServletException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else if (req.getParameter("s").equals("size")) {
					int qtde = 0;
					if (req.getSession().getAttribute("ClientUser") != null) {
						for (Carrinho_Itens item : ((Carrinho) resultado.getDominio()).getItens()) {
							qtde += item.getQuantidade();
						}
					}

					String json = new Gson().toJson(qtde);

					resp.setContentType("application/json");
					resp.setCharacterEncoding("UTF-8");

					try {
						resp.getWriter().write(json);
					} catch (Exception e) {
						System.err.println(e.getMessage());
					}
				}
			} else {
				try {
					req.getRequestDispatcher("cart_empty.jsp").forward(req, resp);
				} catch (ServletException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
