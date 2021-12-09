package br.edu.fatec.detoranja.vh;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import br.edu.fatec.detoranja.dominio.Cliente;
import br.edu.fatec.detoranja.dominio.Cupom;
import br.edu.fatec.detoranja.dominio.IDominio;
import br.edu.fatec.detoranja.util.Resultado;

public class CupomVh implements IViewHelper {

	@Override
	public IDominio getDominio(HttpServletRequest req) {
		String operacao = req.getParameter("operacao");

		Cupom cupom = new Cupom();

		if (operacao != null && operacao.equals("Buscar")) {
			cupom.setDesc_cupom(req.getParameter("txt"));

			Cliente cliente = new Cliente();
			cliente.setId(Integer.parseInt(req.getSession().getAttribute("ClientUser").toString()));
			cupom.setCliente(cliente);
		} else if (operacao != null && operacao.equals("Lista")) {

			Cliente cliente = new Cliente();
			cliente.setId(Integer.parseInt(req.getSession().getAttribute("ClientUser").toString()));
			cupom.setCliente(cliente);
		}

		return cupom;
	}

	@Override
	public void setDominio(HttpServletRequest req, HttpServletResponse resp, Resultado resultado) {
		String operacao = req.getParameter("operacao");

		if (operacao != null && operacao.equals("Buscar")) {
			String json = "";
			if (resultado.getMsg() == null)
				json = new Gson().toJson(resultado.getDominio());
			else
				json = new Gson().toJson(resultado.getMsg());

			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");

			try {
				resp.getWriter().write(json);
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		} else if (operacao != null && operacao.equals("Lista")) {
			List<Cupom> listaCupons = new ArrayList<Cupom>();
			for (IDominio d : resultado.getListDominio()) {
				listaCupons.add((Cupom) d);
			}

			try {
				req.setAttribute("listaCupons", listaCupons);
				req.getRequestDispatcher("usuario_cupons.jsp").forward(req, resp);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
