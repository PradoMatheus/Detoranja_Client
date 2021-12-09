package br.edu.fatec.detoranja.vh;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import br.edu.fatec.detoranja.dominio.Endereco_Tipo_Logradouro;
import br.edu.fatec.detoranja.dominio.IDominio;
import br.edu.fatec.detoranja.util.Resultado;

public class Endereco_Tipo_LogradouroVh implements IViewHelper {

	@Override
	public IDominio getDominio(HttpServletRequest req) {
		String operacao = req.getParameter("operacao");

		Endereco_Tipo_Logradouro endereco_Tipo_LogradouroDAO = new Endereco_Tipo_Logradouro();

		if (operacao != null && operacao.equals("Lista")) {

		}

		return endereco_Tipo_LogradouroDAO;
	}

	@Override
	public void setDominio(HttpServletRequest req, HttpServletResponse resp, Resultado resultado) {
		String operacao = req.getParameter("operacao");
		
		if (operacao != null && operacao.equals("Lista")) {
			String json = new Gson().toJson(resultado.getListDominio());

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
