package br.edu.fatec.detoranja.vh;

import java.io.IOException;
import java.time.LocalDateTime;
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
import br.edu.fatec.detoranja.dominio.Cupom;
import br.edu.fatec.detoranja.dominio.Endereco;
import br.edu.fatec.detoranja.dominio.Endereco_Tipo_Logradouro;
import br.edu.fatec.detoranja.dominio.Forma_Pagamento;
import br.edu.fatec.detoranja.dominio.IDominio;
import br.edu.fatec.detoranja.dominio.Pedido;
import br.edu.fatec.detoranja.dominio.Pedido_Frete;
import br.edu.fatec.detoranja.dominio.Pedido_Itens;
import br.edu.fatec.detoranja.dominio.Pedido_Status;
import br.edu.fatec.detoranja.dominio.Produto;
import br.edu.fatec.detoranja.util.Resultado;

public class PedidoVh implements IViewHelper {

	@Override
	public IDominio getDominio(HttpServletRequest req) {
		String operacao = req.getParameter("operacao");

		Pedido pedido = new Pedido();

		if (operacao != null && operacao.equals("Salvar")) {

			try {
				JsonObject data = new Gson().fromJson(req.getReader(), JsonObject.class);

				// Valor Total do Pedido
				pedido.setValorTotal(data.get("ValorTotal").getAsDouble());

				// Quantidade Total de Itens do Pedido
				pedido.setQuantidade(data.get("quantidade").getAsInt());

				// Status inicial do pedido
				Pedido_Status status = new Pedido_Status();
				status.setId(1);
				pedido.setStatus(status);

				// Horario da Compra
				pedido.setData_pedido(LocalDateTime.now());

				// Cliente que está efetuando a compra
				Cliente cliente = new Cliente();
				cliente.setId(Integer.parseInt(req.getSession().getAttribute("ClientUser").toString()));
				pedido.setCliente(cliente);

				// Busca e adiciona o frete ao objeto
				Pedido_Frete frete = new Pedido_Frete();
				frete.setValor(data.get("Frete").getAsDouble());
				pedido.setFrete(frete);

				// Busca e adiciona os cupons ao objeto
				List<Cupom> listCupoms = new ArrayList<Cupom>();
				Cupom cupom;
				JsonArray jCupons = data.get("Cupons").getAsJsonArray();
				for (int i = 0; i < jCupons.size(); i++) {
					cupom = new Cupom();
					JsonObject obj = jCupons.get(i).getAsJsonObject();

					cupom.setId(obj.get("cupom_id").getAsInt());

					listCupoms.add(cupom);
				}
				pedido.setListcupoms(listCupoms);

				
				if(pedido.getValorTotal() > 0) {
					// Busca e adiciona os Cartoes ao objeto
					List<Forma_Pagamento> listCartoes = new ArrayList<Forma_Pagamento>();
					Forma_Pagamento cartao;
					JsonArray jCartao = data.get("Cartoes").getAsJsonArray();
	
					for (int i = 0; i < jCartao.size(); i++) {
						cartao = new Forma_Pagamento();
						JsonObject obj = jCartao.get(i).getAsJsonObject();
	
						if (obj.has("id")) {
	
							cartao.setId(obj.get("id").getAsInt());
	
							cliente = new Cliente();
							cliente.setId(Integer.parseInt(req.getSession().getAttribute("ClientUser").toString()));
							cartao.setCliente(cliente);
	
							cartao.setBandeira(obj.get("bandeira").getAsString());
							cartao.setNumero_cartao(
									Long.parseLong(obj.get("numero_cartao").getAsString().replaceAll(" ", "")));
							cartao.setAno_validade(obj.get("ano_validade").getAsInt());
							cartao.setMes_validade(obj.get("mes_validade").getAsInt());
							cartao.setCvv(obj.get("cvv").getAsInt());
							cartao.setNome(obj.get("nome").getAsString());
							cartao.setPreferencal(false);
							cartao.setValor_parcela(
									Double.parseDouble(obj.get("valorParcela").getAsString().replaceAll(",", ".")));
							cartao.setQtde_parcela(obj.get("QtdeParcelas").getAsInt());
						} else {
							cartao.setId(0);
	
							cliente = new Cliente();
							cliente.setId(Integer.parseInt(req.getSession().getAttribute("ClientUser").toString()));
							cartao.setCliente(cliente);
	
							cartao.setBandeira(obj.get("txtBandeira").getAsString());
							cartao.setNumero_cartao(
									Long.parseLong(obj.get("txtNumeroCartao").getAsString().replaceAll(" ", "")));
							cartao.setAno_validade(obj.get("txtAnoValidade").getAsInt());
							cartao.setMes_validade(obj.get("txtMesValidade").getAsInt());
							cartao.setCvv(obj.get("txtCvv").getAsInt());
							cartao.setNome(obj.get("txtNome").getAsString());
							cartao.setPreferencal(obj.get("txtSalvar").getAsBoolean());
							cartao.setValor_parcela(
									Double.parseDouble(obj.get("valorParcela").getAsString().replaceAll(",", ".")));
							cartao.setQtde_parcela(obj.get("QtdeParcelas").getAsInt());
						}
	
						listCartoes.add(cartao);
					}
					pedido.setListforma_Pagamentos(listCartoes);
				}
				
				// Busca e adiciona o endereço ao objeto
				Endereco endereco = new Endereco();
				JsonArray jEnd = data.get("Endereco").getAsJsonArray();
				JsonObject oEnd = jEnd.get(0).getAsJsonObject();
				if (oEnd.has("id")) {
					endereco.setId(Integer.parseInt(oEnd.get("id").getAsString()));
					endereco.setId_cliente(Integer.parseInt(req.getSession().getAttribute("ClientUser").toString()));
					endereco.setDescricao(oEnd.get("descricao").getAsString());
					endereco.setCep(oEnd.get("cep").getAsString());
					endereco.setBairro(oEnd.get("bairro").getAsString());
					endereco.setCidade(oEnd.get("cidade").getAsString());
					endereco.setPais(oEnd.get("pais").getAsString());
					endereco.setEstado(oEnd.get("estado").getAsString());

					Endereco_Tipo_Logradouro tipo_logradouro = new Endereco_Tipo_Logradouro();
					JsonObject tipoLog = oEnd.get("tipo_logradouro").getAsJsonObject();
					tipo_logradouro.setId(tipoLog.get("id").getAsInt());
					endereco.setTipo_logradouro(tipo_logradouro);

					endereco.setLogradouro(oEnd.get("logradouro").getAsString());
					endereco.setNumero(oEnd.get("numero").getAsString());
					endereco.setComplemento(oEnd.get("complemento").getAsString());
					endereco.setReferencia(oEnd.get("referencia").getAsString());
					endereco.setPreferencial(false);
				} else {
					endereco.setId(0);
					endereco.setId_cliente(Integer.parseInt(req.getSession().getAttribute("ClientUser").toString()));
					endereco.setDescricao(oEnd.get("txtDescricao").getAsString());
					endereco.setCep(oEnd.get("txtCEP").getAsString());
					endereco.setBairro(oEnd.get("txtBairro").getAsString());
					endereco.setCidade(oEnd.get("txtCidade").getAsString());
					endereco.setPais(oEnd.get("txtPais").getAsString());
					endereco.setEstado(oEnd.get("txtEstado").getAsString());

					Endereco_Tipo_Logradouro tipo_logradouro = new Endereco_Tipo_Logradouro();
					tipo_logradouro.setId(Integer.parseInt(oEnd.get("txtTipoLogradouro").getAsString()));
					endereco.setTipo_logradouro(tipo_logradouro);

					endereco.setLogradouro(oEnd.get("txtLogradouro").getAsString());
					endereco.setNumero(oEnd.get("txtNumero").getAsString());
					endereco.setComplemento(oEnd.get("txtComplemento").getAsString());
					endereco.setReferencia(oEnd.get("txtReferencia").getAsString());
					endereco.setPreferencial(oEnd.get("txtSalvar").getAsBoolean());
				}
				pedido.setEndereco(endereco);

				// Busca e adiciona o endereço ao objeto
				Pedido_Itens item;
				List<Pedido_Itens> listItens = new ArrayList<Pedido_Itens>();
				JsonArray jItens = data.get("Itens").getAsJsonArray();
				for (int i = 0; i < jItens.size(); i++) {
					item = new Pedido_Itens();
					JsonObject obj = jItens.get(i).getAsJsonObject();

					Pedido p = new Pedido();
					p.setId(pedido.getId());
					item.setPedido(p);

					Produto produto = new Produto();
					produto.setId(obj.get("id_produto").getAsInt());
					item.setProduto(produto);

					item.setQuantidade(obj.get("quantidade").getAsInt());
					item.setValor(obj.get("valor").getAsDouble());

					listItens.add(item);
				}
				pedido.setListprodutos(listItens);

				System.out.println(data);

			} catch (JsonSyntaxException | JsonIOException | IOException e) {
				e.printStackTrace();
			}

		} else if (operacao != null && operacao.equals("Lista")) {

			Cliente cliente = new Cliente();
			cliente.setId(Integer.parseInt(req.getSession().getAttribute("ClientUser").toString()));
			pedido.setCliente(cliente);
		} else if (operacao != null && operacao.equals("Buscar")) {

			pedido.setId(Integer.parseInt(req.getParameter("id")));
		}

		return pedido;
	}

	@Override
	public void setDominio(HttpServletRequest req, HttpServletResponse resp, Resultado resultado) {
		String operacao = req.getParameter("operacao");

		if (operacao != null && operacao.equals("Salvar")) {
			Pedido pedido = (Pedido) resultado.getDominio();
			String json = new Gson().toJson(pedido);

			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");

			try {
				resp.getWriter().write(json);
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		} else if (operacao != null && operacao.equals("Lista")) {
			List<Pedido> listaPedido = new ArrayList<Pedido>();
			for (IDominio d : resultado.getListDominio()) {
				listaPedido.add((Pedido) d);
			}
			req.setAttribute("listaPedido", listaPedido);

			try {
				req.getRequestDispatcher("usuario_meus_pedidos.jsp").forward(req, resp);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (operacao != null && operacao.equals("Buscar")) {
			Pedido pedido = (Pedido) resultado.getDominio();
			try {
				req.setAttribute("pedido", pedido);
				req.getRequestDispatcher("usuario_pedido.jsp").forward(req, resp);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
