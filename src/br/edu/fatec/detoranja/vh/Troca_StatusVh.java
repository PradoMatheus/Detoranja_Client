package br.edu.fatec.detoranja.vh;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import br.edu.fatec.detoranja.dominio.IDominio;
import br.edu.fatec.detoranja.dominio.Troca;
import br.edu.fatec.detoranja.dominio.Troca_Status;
import br.edu.fatec.detoranja.util.Resultado;

public class Troca_StatusVh implements IViewHelper {

	@Override
	public IDominio getDominio(HttpServletRequest req) {
		String operacao = req.getParameter("operacao");

		Troca_Status status = new Troca_Status();

		if (operacao != null && operacao.equals("Salvar")) {
			status.setId(Integer.parseInt(req.getParameter("status")));
			
			Troca troca = new Troca();
			troca.setId(Integer.parseInt(req.getParameter("id")));
			status.setTroca(troca);
		}

		return status;
	}

	@Override
	public void setDominio(HttpServletRequest req, HttpServletResponse resp, Resultado resultado) {
		String operacao = req.getParameter("operacao");

		if (operacao != null && operacao.equals("Salvar ")) {
			String json = "";
			if (resultado.getMsg() == null) {
				json = new Gson().toJson(true);
			} else {
				json = new Gson().toJson(false);
			}

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
