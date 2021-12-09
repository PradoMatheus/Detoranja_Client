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

import br.edu.fatec.detoranja.dominio.Endereco;
import br.edu.fatec.detoranja.dominio.Endereco_Tipo_Logradouro;
import br.edu.fatec.detoranja.dominio.IDominio;
import br.edu.fatec.detoranja.util.Resultado;

public class EnderecoVh implements IViewHelper {

	@Override
	public IDominio getDominio(HttpServletRequest req) {
		String operacao = req.getParameter("operacao");

		Endereco endereco = new Endereco();

		if (operacao != null && operacao.equals("Salvar")) {

			try {
				JsonObject data = new Gson().fromJson(req.getReader(), JsonObject.class);

				endereco.setId(Integer.parseInt(data.get("txtId").getAsString()));
				endereco.setId_cliente(Integer.parseInt(req.getSession().getAttribute("ClientUser").toString()));
				endereco.setDescricao(data.get("txtDescricao").getAsString());
				endereco.setCep(data.get("txtCEP").getAsString());
				endereco.setBairro(data.get("txtBairro").getAsString());
				endereco.setCidade(data.get("txtCidade").getAsString());
				endereco.setPais(data.get("txtPais").getAsString());
				endereco.setEstado(data.get("txtEstado").getAsString());

				Endereco_Tipo_Logradouro tipo_logradouro = new Endereco_Tipo_Logradouro();
				tipo_logradouro.setId(Integer.parseInt(data.get("txtTipoLogradouro").getAsString()));
				endereco.setTipo_logradouro(tipo_logradouro);

				endereco.setLogradouro(data.get("txtLogradouro").getAsString());
				endereco.setNumero(data.get("txtNumero").getAsString());
				endereco.setComplemento(data.get("txtComplemento").getAsString());
				endereco.setReferencia(data.get("txtReferencia").getAsString());
				endereco.setPreferencial(Boolean.parseBoolean(data.get("txtPreferencial").getAsString()));

			} catch (JsonSyntaxException | JsonIOException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (operacao != null && operacao.equals("Lista")) {
			endereco.setId_cliente(Integer.parseInt(req.getSession().getAttribute("ClientUser").toString()));
		} else if (operacao != null && operacao.equals("Buscar")) {
			endereco.setId(Integer.parseInt(req.getParameter("id")));
		} else if (operacao != null && operacao.equals("Excluir")) {
			endereco.setId(Integer.parseInt(req.getParameter("id")));
		}

		return endereco;
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
				List<Endereco> listaEndereco = new ArrayList<Endereco>();
				for (IDominio d : resultado.getListDominio()) {
					listaEndereco.add((Endereco) d);
				}

				try {
					req.setAttribute("listaEndereco", listaEndereco);
					req.getRequestDispatcher("usuario_enderecos.jsp").forward(req, resp);
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
