package br.edu.fatec.detoranja.vh;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import br.edu.fatec.detoranja.dominio.Carrinho;
import br.edu.fatec.detoranja.dominio.Carrinho_Itens;
import br.edu.fatec.detoranja.dominio.IDominio;
import br.edu.fatec.detoranja.dominio.Produto;
import br.edu.fatec.detoranja.util.Resultado;

public class CarrinhoItensVh implements IViewHelper {

	@Override
	public IDominio getDominio(HttpServletRequest req) {
		String operacao = req.getParameter("operacao");

		Carrinho_Itens item = new Carrinho_Itens();

		if (operacao != null && operacao.equals("Salvar")) {

			try {
				JsonObject data = new Gson().fromJson(req.getReader(), JsonObject.class);

				if (data.get("id") != null)
					item.setId(data.get("id").getAsInt());
				else {
					Produto produto = new Produto();
					produto.setId(data.get("id_produto").getAsInt());
					item.setProduto(produto);
				}
				item.setQuantidade(data.get("quantidade").getAsInt());

				Carrinho carrinho = new Carrinho();
				carrinho.setId_cliente(Integer.parseInt(req.getSession().getAttribute("ClientUser").toString()));
				item.setCarrinho(carrinho);

			} catch (JsonSyntaxException | JsonIOException | IOException e) {
				e.printStackTrace();
			}
		} else if (operacao != null && operacao.equals("Excluir")) {

			try {
				JsonObject data = new Gson().fromJson(req.getReader(), JsonObject.class);

				item.setId(data.get("id").getAsInt());
			} catch (JsonSyntaxException | JsonIOException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return item;
	}

	@Override
	public void setDominio(HttpServletRequest req, HttpServletResponse resp, Resultado resultado) {
		String operacao = req.getParameter("operacao");

		if (operacao != null && operacao.equals("Salvar")) {

			String json = null;

			if (resultado.getMsg() != null)
				json = new Gson().toJson(false);
			else
				json = new Gson().toJson(true);

			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");

			try {
				resp.getWriter().write(json);
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		} else if (operacao != null && operacao.equals("Excluir")) {
			String json = null;

			if (resultado.getMsg() != null)
				json = new Gson().toJson(false);
			else
				json = new Gson().toJson(true);

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
