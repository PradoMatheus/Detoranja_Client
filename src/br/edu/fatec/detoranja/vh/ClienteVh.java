package br.edu.fatec.detoranja.vh;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import br.edu.fatec.detoranja.dominio.Cliente;
import br.edu.fatec.detoranja.dominio.Cliente_Genero;
import br.edu.fatec.detoranja.dominio.IDominio;
import br.edu.fatec.detoranja.util.Criptografia_Hash;
import br.edu.fatec.detoranja.util.Resultado;

public class ClienteVh implements IViewHelper {

	@Override
	public IDominio getDominio(HttpServletRequest req) {
		String operacao = req.getParameter("operacao");

		Cliente cliente = new Cliente();

		if (operacao != null && operacao.equals("Salvar")) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			try {
				JsonObject data = new Gson().fromJson(req.getReader(), JsonObject.class);

				System.out.println(data.get("txtTelefone").getAsString().replace(" ", ""));
				
				cliente.setNome(data.get("txtNome").getAsString());
				cliente.setTelefone(Long.parseLong(data.get("txtTelefone").getAsString().replace(" ", "").replace("(", "").replace(")", "").replace("-", "")));
				cliente.setCpf(data.get("txtCPF").getAsString());
				cliente.setEmail(data.get("txtEmail").getAsString());
				cliente.setData_nascimento(LocalDate.parse(data.get("txtData").getAsString(), formatter));
				
				Cliente_Genero genero = new Cliente_Genero();
				genero.setId(data.get("txtSexo").getAsInt());
				cliente.setGenero(genero);

				if (req.getSession().getAttribute("ClientUser") == null) {
					cliente.setSenha(Criptografia_Hash.criptografiaHash((data.get("txtPassword").getAsString())));
				} else {
					cliente.setId(Integer.parseInt(req.getSession().getAttribute("ClientUser").toString()));
					cliente.setSenha(data.get("txtPassword").getAsString());
				}
			} catch (JsonSyntaxException | JsonIOException | IOException e) {
				e.printStackTrace();
			}
		} else if (operacao != null && operacao.equals("Buscar")) {
			if (req.getSession().getAttribute("ClientUser") == null) {
				JsonObject data;
				try {
					data = new Gson().fromJson(req.getReader(), JsonObject.class);

					cliente.setEmail(data.get("txtEmail").getAsString());
					cliente.setSenha(Criptografia_Hash.criptografiaHash(data.get("txtPassword").getAsString()));
				} catch (JsonSyntaxException | JsonIOException | IOException e) {
					e.printStackTrace();
				}
			} else {
				cliente.setId(Integer.parseInt(req.getSession().getAttribute("ClientUser").toString()));
			}
		} else if (operacao != null && operacao.equals("Sair")) {
			req.getSession().removeAttribute("ClientUser");
		}

		return cliente;
	}

	@Override
	public void setDominio(HttpServletRequest req, HttpServletResponse resp, Resultado resultado) {
		String operacao = req.getParameter("operacao");

		if (operacao != null && operacao.equals("Salvar")) {
			if (req.getSession().getAttribute("ClientUser") == null) {
				if (resultado.getMsg() != null) {
					String json = new Gson().toJson(resultado.getMsg());

					resp.setContentType("application/json");
					resp.setCharacterEncoding("UTF-8");

					try {
						resp.getWriter().write(json);
					} catch (Exception e) {
						System.err.println(e.getMessage());
					}
				} else {
					try {
						req.getRequestDispatcher("index.jsp").forward(req, resp);
					} catch (ServletException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else {
				String json = new Gson().toJson("Sucesso!");

				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");

				try {
					resp.getWriter().write(json);
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		} else if (operacao != null && operacao.equals("Buscar")) {
			Cliente cliente = (Cliente) resultado.getDominio();
			try {
				if (cliente.getId() != 0) {
					if (req.getSession().getAttribute("ClientUser") == null) {
						req.getSession().setAttribute("ClientUser", cliente.getId());

						String json = new Gson().toJson(true);

						resp.setContentType("application/json");
						resp.setCharacterEncoding("UTF-8");

						try {
							resp.getWriter().write(json);
						} catch (Exception e) {
							System.err.println(e.getMessage());
						}
					} else {
						req.setAttribute("cliente", cliente);
						req.getRequestDispatcher("usuario_configuracao.jsp").forward(req, resp);
					}
				} else {
					String json = new Gson().toJson(false);

					resp.setContentType("application/json");
					resp.setCharacterEncoding("UTF-8");

					try {
						resp.getWriter().write(json);
					} catch (Exception e) {
						System.err.println(e.getMessage());
					}
				}
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (operacao != null && operacao.equals("Sair")) {
			try {
				req.getRequestDispatcher("index.jsp").forward(req, resp);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
