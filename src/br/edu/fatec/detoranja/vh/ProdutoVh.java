package br.edu.fatec.detoranja.vh;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.edu.fatec.detoranja.dominio.IDominio;
import br.edu.fatec.detoranja.dominio.Produto;
import br.edu.fatec.detoranja.dominio.Produto_Categoria;
import br.edu.fatec.detoranja.dominio.Produto_Idioma;
import br.edu.fatec.detoranja.util.Resultado;

public class ProdutoVh implements IViewHelper {

	@Override
	public IDominio getDominio(HttpServletRequest req) {
		String operacao = req.getParameter("operacao");

		Produto produto = new Produto();

		if (operacao != null && operacao.equals("Buscar")) {
			
			produto.setId(Integer.parseInt(req.getParameter("id")));

		} else if (operacao != null && operacao.equals("Lista")) {
			if(req.getParameter("i") != null) {
				Produto_Idioma idioma = new Produto_Idioma();
				idioma.setId(Integer.parseInt(req.getParameter("i")));
				produto.setIdioma(idioma);
			}
				
			if(req.getParameter("c") != null) {
				Produto_Categoria categoria = new Produto_Categoria();
				categoria.setId(Integer.parseInt(req.getParameter("c")));
				produto.setCategoria(categoria);
			}
				
		}


		return produto;
	}

	@Override
	public void setDominio(HttpServletRequest req, HttpServletResponse resp, Resultado resultado) {
		String operacao = req.getParameter("operacao");

		if (operacao != null && operacao.equals("Buscar")) {
			req.setAttribute("produto", (Produto) resultado.getDominio());

			try {
				req.getRequestDispatcher("product_details.jsp").forward(req, resp);
			} catch (ServletException e) {
				System.err.print(e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (operacao != null && operacao.equals("Lista")) {
			List<Produto> listaProdutos = new ArrayList<Produto>();
			for (IDominio d : resultado.getListDominio()) {
				listaProdutos.add((Produto) d);
			}
			req.setAttribute("listaProdutos", listaProdutos);

			try {
				req.getRequestDispatcher("products.jsp").forward(req, resp);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
