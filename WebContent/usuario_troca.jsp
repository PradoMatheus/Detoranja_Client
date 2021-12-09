<%@page import="br.edu.fatec.detoranja.dominio.Troca_Log"%>
<%@page import="br.edu.fatec.detoranja.dominio.Troca_Itens"%>
<%@page import="br.edu.fatec.detoranja.dominio.Troca"%>
<%@page import="br.edu.fatec.detoranja.dominio.Cupom"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="br.edu.fatec.detoranja.dominio.Pedido_Log"%>
<%@page import="br.edu.fatec.detoranja.dominio.Forma_Pagamento"%>
<%@page import="br.edu.fatec.detoranja.dominio.Pedido_Itens"%>
<%@page import="java.text.DecimalFormat"%>
<html>
<head>
<title>Troca</title>
<meta charset="UTF-8">
<%@ include file="complements/complements_css.jsp"%>
<link type="text/css" rel="stylesheet" href="css/pedido.css">
</head>
<body>

	<%@ include file="complements/navbar.jsp"%>

	<%
		Troca troca = (Troca) request.getAttribute("troca");
		DecimalFormat df = new DecimalFormat();
		df.applyPattern("#,##0.00");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	%>

	<div class="container">
		<div class="container px-1 px-md-4 py-5 mx-auto">
			<div class="card">
				<div class="row d-flex justify-content-between px-3 top">
					<div class="d-flex">
						<h5>
							TROCA <span class="text-primary font-weight-bold">#<%
						out.print(troca.getId());
						%></span> <a class="login" href="#" data-toggle="dropdown" aria-hidden="true"><i class="fa fa-info-circle"
								aria-hidden="true" style="margin-right: 1px"></i></a>
						<div class="dropdown-menu">
							<a class="dropdown-item" id="btnCancelar" href="#" <%if (troca.getStatus().getId() == 5) { %>style=" pointer-events: none;color:gray"<% }%> data-abc="true">Cancelar</a>
						</div>
						</h5>
					</div>
					<div class="d-flex flex-column text-sm-right">
						<p class="mb-0">
							Previsão de Entrega: <span>13/09/2021</span>
						</p>
						<p>
							Código de Rastreio: <span class="font-weight-bold"><a
								href="#">FV5465465AA</a></span>
						</p>
					</div>
				</div>
				<!-- Add class 'active' to progress -->
				<div class="row d-flex justify-content-center">
					<div class="col-12">
						<ul id="progressbar" class="text-center">
							<%
							Map<Integer, Troca_Log> mapLogs = new HashMap<Integer, Troca_Log>();
							for (Troca_Log log : troca.getListLogs()) {
								mapLogs.put(log.getStatus().getId(), log);
							}
							%>
							<li class="<%
							if (mapLogs.containsKey(1)){%>active<%}%> step0"
								style="margin-left: 100px">EM TROCA<br> <span
								class="text-primary font-weight-bold"> <%
							  if (mapLogs.containsKey(1)) {
							  	out.print(mapLogs.get(1).getData().format(formatter));
							  }
							 %>
							</span>
							</li>
							<li
								class="<%
								if (mapLogs.containsKey(2)){%>active<%}%> step0">TROCA AUTORIZADA<br>
								<span class="text-primary font-weight-bold"> <%
								  if (mapLogs.containsKey(2)) {
								  	out.print(mapLogs.get(2).getData().format(formatter));
								  }
							 %>
							</span></li>
							<li class="<%
									if (mapLogs.containsKey(3)){%>active<%}%> step0">TROCA EM TRANSITO
							<br> <span class="text-primary font-weight-bold">
									<%
									if (mapLogs.containsKey(3)) {
										out.print(mapLogs.get(3).getData().format(formatter));
									}
									%>
							</span>
							</li>
							<li class="<%
								if (mapLogs.containsKey(4)){%>active<%}%> step0">TROCA FINALIZADA<br>
							<span class="text-primary font-weight-bold"> 
							<%
							  if (mapLogs.containsKey(4)) {
							  	out.print(mapLogs.get(4).getData().format(formatter));
							  }
							 %>
							</span></li>
						</ul>
					</div>
				</div>
				<%if (troca.getStatus().getId() == 5) { %>
				<div class="row d-flex justify-content-center">
					<div class="col-12">
						<ul class="text-center">
								CANCELADO<br>
								<span class="text-primary font-weight-bold"> <i class="fa fa-ban fa-2x" style="color: #FF0000;"></i>
							</span><br>
							<% if (mapLogs.containsKey(5)) {
								out.print(mapLogs.get(5).getData().format(formatter));
							}%>
							</ul>
					</div>
				</div>
				<% } %>
				<hr />
				<div class="row d-flex justify-content-between top">
					<div class="d-flex">
						<h5>
							<strong>CUPONS</strong>
						</h5>
					</div>
				</div>
				<div class="row d-flex justify-content-between px-3 top">
					<div class="d-flex">
						<%
						if(troca.getCupom() != null) {
						%>
						<table class="table">
							<thead>
								<tr>
									<th scope="col">Descricão Cupom</th>
									<th scope="col"
										style='vertical-align: middle; text-align: center'>Desconto</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td><span class="text-primary font-weight-bold"> 
									<%
 									out.print(troca.getCupom().getDesc_cupom());
 									%>
									</span></td>
									<td style='vertical-align: middle; text-align: center'>R$<%
									out.print(df.format(troca.getCupom().getDesconto()));
									%></td>
								</tr>
							</tbody>
						</table>
						<%
						
						} else {
						%>
							<i class="fa fa-exclamation-triangle" style="margin-right: 5px;"></i> Nenhum cupom gerado !!
						<%
						}
						%>
					</div>
				</div>
				<hr />
				<div class="px-3 top">
					<div class="d-flex">
						<h5>
							<strong>LISTA DE PRODUTOS</strong>
						</h5>
					</div>
					<div class="table-responsive-sm">
						<table class="table table-striped">
							<thead>
								<tr>
									<th style="width: 5%; text-align: center;">#</th>
									<th style="width: 5%; text-align: center;">Livro</th>
									<th style="width: 45%; text-align: left;">Titulo</th>
									<th style="width: 10%; text-align: center;">Valor</th>
									<th style="width: 10%; text-align: center;">Qtde</th>
									<th style="width: 15%; text-align: center;">Total</th>
								</tr>
							</thead>
							<tbody>
								<%
								int i = 0;
								double total = 0;
								for (Troca_Itens item : troca.getListTrocaItens()) {
									total += (item.getValor() * item.getQuantidade());
								%>
								<tr>
									<td style="width: 5%; text-align: center;">
										<%
										out.print(++i);
										%>
									</td>
									<td style="width: 5%; text-align: center;" style='vertical-align: middle; text-align: center'>
										<%
										out.print(item.getProduto().getId());
										%>
									</td>
									<td style="width: 45%; text-align: left;">
										<%
										out.print(item.getProduto().getTitulo());
										%>
									</td>
									<td style="width: 10%; text-align: center;">R$<%
									out.print(df.format(item.getValor()));
									%></td>
									<td style="width: 10%; text-align: center;">
										<%
										out.print(item.getQuantidade());
										%>
									</td>
									<td style="width: 15%; text-align: center;">R$ <%
									out.print(df.format(item.getValor() * item.getQuantidade()));
									%></td>
								</tr>
								<%
								}
								%>
							</tbody>
						</table>
					</div>
					<div class="col-lg-4 col-sm-5 ml-auto">
						<table class="table table-clear">
							<tbody>
								<tr>
									<td class="left"><strong class="text-dark">Valor Troca</strong></td>
									<td class="right">R$ <%
									out.print(df.format(total));
									%></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- MODAL PARA SOLICITAR CANCELAMENTO -->
  	<div class="modal fade" id="modalCancelamento" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
	    <div class="modal-content">
	      <div class="modal-header" style=" margin-left: auto;margin-right: auto;">
	        <h5 class="modal-title"><strong>Cancelar Troca <% out.print(troca.getId()); %></strong></h5>
	      </div>
	      <div class="modal-body">
	      	<label class="col-form-label">
	      		Confirmar Cancelamento da Troca <% out.print(troca.getId()); %> ?
	      	</label>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">Fechar</button>
	        <button type="button" class="btn btn-primary" id="btnCancelarPedido">Solicitar</button>
	      </div>
	    </div>
	  </div>
	</div>

	<%@ include file="complements/complements_js.jsp"%>
</body>
	<script type="text/javascript">
			$("#btnCancelar").click(function(){
				$('#modalCancelamento').modal('show');
				
			})
			$("#btnCancelarPedido").click(function(){
				$.ajax({
				    type: "POST",
				    url: "troca_status?operacao=Salvar&id=<%out.print(troca.getId());%>&status=5",
				    contentType: "application/json", // NOT dataType!
				    success: function(retorno) {
				    	if(retorno == true){
					    	toastr.success('Cancelamento realizado com sucesso!!')
					    	setTimeout(function() {
					    		window.location.href = "index.jsp";
					    	}, 1000);
				    	} else {
				    		toastr.success('Erro ao realizar Cancelamento!!')
				    	}
				    }
				});
			});
	</script>

<%@ include file="complements/footer.jsp"%>
</html>
