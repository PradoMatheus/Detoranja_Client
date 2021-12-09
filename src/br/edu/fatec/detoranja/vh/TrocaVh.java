package br.edu.fatec.detoranja.vh;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import br.edu.fatec.detoranja.dominio.Cliente;
import br.edu.fatec.detoranja.dominio.IDominio;
import br.edu.fatec.detoranja.dominio.Pedido;
import br.edu.fatec.detoranja.dominio.Produto;
import br.edu.fatec.detoranja.dominio.Troca;
import br.edu.fatec.detoranja.dominio.Troca_Itens;
import br.edu.fatec.detoranja.dominio.Troca_Motivo;
import br.edu.fatec.detoranja.dominio.Troca_Status;
import br.edu.fatec.detoranja.util.Resultado;

public class TrocaVh implements IViewHelper {

	@Override
	public IDominio getDominio(HttpServletRequest req) {
		String operacao = req.getParameter("operacao");

		Troca troca = new Troca();

		if (operacao != null && operacao.equals("Salvar")) {

			try {
				JsonObject data = new Gson().fromJson(req.getReader(), JsonObject.class);

				Troca_Status status = new Troca_Status();
				status.setId(1);
				troca.setStatus(status);

				Troca_Motivo motivo = new Troca_Motivo();
				motivo.setId(data.get("motivo").getAsInt());
				troca.setMotivo(motivo);

				Pedido pedido = new Pedido();
				pedido.setId(data.get("pedido").getAsInt());
				troca.setPedido(pedido);

				troca.setObservacao(data.get("observacao").getAsString());

				// Busca e adiciona os itens que foram solicitados a troca
				List<Troca_Itens> listItens = new ArrayList<Troca_Itens>();
				Troca_Itens item;
				JsonArray jItens = data.get("itens").getAsJsonArray();
				for (int i = 0; i < jItens.size(); i++) {
					item = new Troca_Itens();
					JsonObject obj = jItens.get(i).getAsJsonObject();

					Produto produto = new Produto();
					produto.setId(obj.get("produto").getAsInt());

					item.setProduto(produto);
					item.setQuantidade(obj.get("quantidade").getAsInt());
					item.setValor(obj.get("valor").getAsDouble());

					listItens.add(item);
				}
				troca.setListTrocaItens(listItens);

			} catch (JsonSyntaxException | JsonIOException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (operacao != null && operacao.equals("Lista")) {

			Cliente cliente = new Cliente();
			cliente.setId(Integer.parseInt(req.getSession().getAttribute("ClientUser").toString()));
			troca.setCliente(cliente);
		} else if (operacao != null && operacao.equals("Buscar")) {
			troca.setId(Integer.parseInt(req.getParameter("id")));
		}

		return troca;
	}

	@Override
	public void setDominio(HttpServletRequest req, HttpServletResponse resp, Resultado resultado) {
		String operacao = req.getParameter("operacao");

		if (operacao != null && operacao.equals("Salvar")) {
			
		} else if (operacao != null && operacao.equals("Lista")) {
			List<Troca> listaTroca = new ArrayList<Troca>();
			for (IDominio d : resultado.getListDominio()) {
				listaTroca.add((Troca) d);
			}
			req.setAttribute("listaTrocas", listaTroca);

			try {
				req.getRequestDispatcher("usuario_minhas_trocas.jsp").forward(req, resp);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (operacao != null && operacao.equals("Buscar")) {
			Troca troca = (Troca) resultado.getDominio();
			try {
				req.setAttribute("troca", troca);
				req.getRequestDispatcher("usuario_troca.jsp").forward(req, resp);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
