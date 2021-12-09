<%@page import="br.edu.fatec.detoranja.dominio.Troca"%>
<%@page import="br.edu.fatec.detoranja.dominio.Cupom"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="br.edu.fatec.detoranja.dominio.Pedido_Log"%>
<%@page import="br.edu.fatec.detoranja.dominio.Forma_Pagamento"%>
<%@page import="br.edu.fatec.detoranja.dominio.Pedido_Itens"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="br.edu.fatec.detoranja.dominio.Pedido"%>
<html>
<head>
<title>Pedido</title>
<meta charset="UTF-8">
<%@ include file="complements/complements_css.jsp"%>
<link type="text/css" rel="stylesheet" href="css/pedido.css">
</head>
<body>

	<%@ include file="complements/navbar.jsp"%>

	<%
	Pedido pedido = (Pedido) request.getAttribute("pedido");
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
							PEDIDO <span class="text-primary font-weight-bold">#<%
						out.print(pedido.getId());
						%></span> <a class="login" href="#" data-toggle="dropdown" aria-hidden="true"><i class="fa fa-info-circle"
								aria-hidden="true" style="margin-right: 1px"></i></a>
						<div class="dropdown-menu">
							<a class="dropdown-item" id="btnCancelar" href="#" data-abc="true" <%if(pedido.getStatus().getId() > 2){ %>style=" pointer-events: none;color:gray"<% } %>>Cancelar</a>
							<div class="dropdown-divider"></div>
							<a class="dropdown-item" id="btnTroca" href="#" data-abc="true" <%if(pedido.getStatus().getId() != 5 || pedido.getStatus().getId() > 6){ %>style=" pointer-events: none;color:gray"<% } %>>Trocar</a>
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
							Map<Integer, Pedido_Log> mapLogs = new HashMap<Integer, Pedido_Log>();
							for (Pedido_Log log : pedido.getListlogs()) {
								mapLogs.put(log.getStatus().getId(), log);
							}
							%>
							<li class="<%if (mapLogs.containsKey(1)) {%>active<%}%> step0"
								style="margin-left: 100px">EM PROCESSAMENTO<br> <span
								class="text-primary font-weight-bold"> <%
							 if (mapLogs.containsKey(1)) {
							 	out.print(mapLogs.get(1).getData().format(formatter));
							 }
							 %>
							</span>
							</li>
							<li
								class="<%if (mapLogs.containsKey(2)) {%>active<%} else if ( mapLogs.containsKey(3)){%>teste<%}%> step0">APROVADO/REPROVADO<br>
								<span class="text-primary font-weight-bold"> <%
								 if (mapLogs.containsKey(2)) {
								 	out.print(mapLogs.get(2).getData().format(formatter));
								 } else if(mapLogs.containsKey(3)) {
									out.print(mapLogs.get(3).getData().format(formatter));
								 }
								 %>
							</span></li>
							<li class="<%if (mapLogs.containsKey(4)) {%>active<%}%> step0">EM
								TRANSPORTE<br> <span class="text-primary font-weight-bold">
									<%
									if (mapLogs.containsKey(4)) {
										out.print(mapLogs.get(4).getData().format(formatter));
									}
									%>
							</span>
							</li>
							<li class="<%if (mapLogs.containsKey(5)) {%>active<%}%> step0">ENTREGUE<br>
								<span class="text-primary font-weight-bold"> <%
								 if (mapLogs.containsKey(5)) {
								 	out.print(mapLogs.get(5).getData().format(formatter));
								 }
								 %>
							</span></li>
						</ul>
					</div>
				</div>
				<%if (pedido.getStatus().getId() == 10) { %>
				<div class="row d-flex justify-content-center">
					<div class="col-12">
						<ul class="text-center">
								CANCELADO<br>
								<span class="text-primary font-weight-bold"> <i class="fa fa-ban fa-2x" style="color: #FF0000;"></i>
							</span><br>
							<% if (mapLogs.containsKey(10)) {
								out.print(mapLogs.get(10).getData().format(formatter));
							}%>
							</ul>
					</div>
				</div>
				<% } %>
				<hr />
				<div class="row d-flex justify-content-between top">
					<div class="d-flex">
						<h5>
							<strong>ENDEREÇO DE ENTREGA</strong>
						</h5>
					</div>
				</div>
				<div class="row d-flex justify-content-between px-3 top">
					<div class="d-flex">
						<table class="table">
							<thead>
								<tr>
									<th scope="col">Descrição</th>
									<th scope="col">Rua</th>
									<th scope="col">Nª</th>
									<th scope="col">Bairro</th>
									<th scope="col">Cidade</th>
									<th scope="col">Estado</th>
									<!-- <th scope="col">CEP</th>  -->
								</tr>
							</thead>
							<tbody>
								<tr>
									<td><span class="text-primary font-weight-bold">
									<%
									out.print(pedido.getEndereco().getDescricao());
									%>
									</span></td>
									<td style='vertical-align: middle; text-align: center'><%
									out.print(pedido.getEndereco().getLogradouro());
									%></td>
									<td style='vertical-align: middle; text-align: center'><%
									out.print(pedido.getEndereco().getNumero());
									%></td>
									<td style='vertical-align: middle; text-align: center'><%
									out.print(pedido.getEndereco().getBairro());
									%></td>
									<td style='vertical-align: middle; text-align: center'><%
									out.print(pedido.getEndereco().getCidade());
									%></td>
									<td style='vertical-align: middle; text-align: center'><%
									out.print(pedido.getEndereco().getEstado());
									%></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<hr />
				<div class="row d-flex justify-content-between top">
					<div class="d-flex">
						<h5>
							<strong>FORMA DE PAGAMENTO</strong>
						</h5>
					</div>
				</div>
				<div class="row d-flex justify-content-between px-3 top">
					<div class="d-flex">
						<%if(pedido.getListforma_Pagamentos().size() != 0) {%>
						<table class="table">
							<thead>
								<tr>
									<th scope="col">Cartão de Credito</th>
									<th scope="col"
										style='vertical-align: middle; text-align: center'>Valor</th>
									<th scope="col">Parcela(s)</th>
								</tr>
							</thead>
							<tbody>
								<%
								for (Forma_Pagamento forma : pedido.getListforma_Pagamentos()) {
								%>
								<tr>
									<td><span class="text-primary font-weight-bold">
											**** **** **** <%
									//out.print(forma.getNumero_cartao().toString().substring(12, 16));
									out.print(Long.toString(forma.getNumero_cartao()).substring(
										       Long.toString(forma.getNumero_cartao()).length() - 4));
									%>
									</span></td>
									<td style='vertical-align: middle; text-align: center'>R$<%
									out.print(df.format(forma.getValor_parcela()));
									%></td>
									<td style='vertical-align: middle; text-align: center'>
										<%
										out.print(forma.getQtde_parcela());
										%> X
									</td>
								</tr>
								<%
								}
								%>
							</tbody>
						</table>
						<%} else{ %>
							<i class="fa fa-exclamation-triangle" style="margin-right: 5px;"></i> Não utilizou cartões para o Pagamento !!
						<%} %>
					</div>
				</div>
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
						<%if(pedido.getListcupoms().size() != 0) {%>
						<table class="table">
							<thead>
								<tr>
									<th scope="col">Descricão Cupom</th>
									<th scope="col"
										style='vertical-align: middle; text-align: center'>Desconto</th>
								</tr>
							</thead>
							<tbody>
								<%
								for (Cupom cupom : pedido.getListcupoms()) {
								%>
								<tr>
									<td><span class="text-primary font-weight-bold"> 
									<%
									out.print(cupom.getDesc_cupom());
									%>
									</span></td>
									<td style='vertical-align: middle; text-align: center'>R$<%
									out.print(df.format(cupom.getDesconto()));
									%></td>
								</tr>
								<%
								}
								%>
							</tbody>
						</table>
						<%} else{ %>
							<i class="fa fa-exclamation-triangle" style="margin-right: 5px;"></i> Nenhum cupom utilizado !!
						<%} %>
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
									<th style="width: 10%; text-align: center;">Troca</th>
									<th style="width: 15%; text-align: center;">Total</th>
								</tr>
							</thead>
							<tbody>
								<%
								int i = 0;
								for (Pedido_Itens item : pedido.getListprodutos()) {
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
									<td style="width: 10%; text-align: center;">
										<%
										out.print(item.getDisponivel_troca());
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
					<% 
					double valorDesc = 0;
					for(Cupom cupom : pedido.getListcupoms()){
						valorDesc += cupom.getDesconto();
					} %>
						<table class="table table-clear">
							<tbody>
								<tr>
									<td class="left"><strong class="text-dark">Valor
											Livros</strong></td>
									<td class="right">R$ <%
									out.print(df.format(pedido.getValorTotal() - pedido.getFrete().getValor() + valorDesc));
									%></td>
								</tr>
								<tr>
									<td class="left"><strong class="text-dark">Frete</strong>
									</td>
									<td class="right">+ R$ <%
									out.print(df.format(pedido.getFrete().getValor()));
									%></td>
								</tr>
								<tr>
									<td class="left"><strong class="text-dark">Desconto</strong>
									</td>
									<td class="right">- R$ <% out.print(df.format(valorDesc)); %></td>
								</tr>
								<tr>
									<td class="left"><strong class="text-dark">Valor
											Total</strong></td>
									<td class="right">R$ <%
									out.print(df.format(pedido.getValorTotal()));
									%></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- MODAL PARA SOLICITAR A TROCA -->
  	<div class="modal fade" id="modalCancelamentoTroca" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
	    <div class="modal-content">
	      <div class="modal-header" style=" margin-left: auto;margin-right: auto;">
	        <h5 class="modal-title"><strong id="labelTitulo"></strong></h5>
	      </div>
	      <div class="modal-body">
	      	<label id="labelConfirmacao" class="col-form-label"></label>
	      	<table id="tabela_itens_troca" class="table table-striped">
	      		<thead id="troca_head">
	      		</thead>
	      		<tbody id="corpo_head">
	      		</tbody>
	      	</table>
	      	<label class="col-form-label">Motivo da Troca</label>
	      	<select id="select_motivos" class="form-control">
	      	  <option value=0 disabled selected="selected">Selecione</option>
			</select>
			<label class="col-form-label">Observação</label>
			<textarea class="form-control" id="txtObservacao" rows="3"></textarea>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">Fechar</button>
	        <button type="button" class="btn btn-primary" id="btnSolicitar">Solicitar</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<!-- MODAL PARA SOLICITAR CANCELAMENTO -->
  	<div class="modal fade" id="modalCancelamento" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
	    <div class="modal-content">
	      <div class="modal-header" style=" margin-left: auto;margin-right: auto;">
	        <h5 class="modal-title"><strong>Cancelar Pedido <% out.print(pedido.getId()); %></strong></h5>
	      </div>
	      <div class="modal-body">
	      	<label class="col-form-label">
	      		Confirmar Cancelamento do Pedido <% out.print(pedido.getId()); %> ?
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
	<script type="text/javascript">
		$("#btnCancelar").click(function(){
			$('#modalCancelamento').modal('show');
		})
		
		$("#btnTroca").click(function(){
			$('#troca_head th').remove();
			$('#corpo_head td').remove();
			$('#labelTitulo').text('Solicitação de troca do PEDIDO ' + <%out.print(pedido.getId());%>)
			$('#labelConfirmacao').text('Itens disponiveis para realização da troca :')
			
			$('#troca_head').append(
					'<th style="width: 5%; text-align: center;">#</th>' + 
					'<th style="width: 5%";>Cod</th>' + 
					'<th style="width: 35%";>Livro</th>' + 
					'<th style="width: 5%; text-align: center;">Valor</th>' + 
					'<th style="width: 15%; text-align: center;">Qtd Disp</th> ' + 
					'<th style="width: 15%; text-align: center;">Qtd Troca</th> ' + 
					'<th style="width: 15%; text-align: center;">Selecionado</th>')
			<%
				int j = 0;
				for (Pedido_Itens item : pedido.getListprodutos()) {
			%>
			$('#corpo_head').append(
					'<tr>' +
					'<td style="width: 5%; text-align: center; "class="id_seq"><%out.print(++j);%></td>' +
					'<td style="width: 5%; text-align: center; "class="id_produto"><%out.print(item.getProduto().getId());%></td>' +
					'<td style="width: 40%;"><%out.print(item.getProduto().getTitulo());%></td>' +
					'<td style="width: 5%; text-align: center;" class="valor"><%out.print(df.format(item.getValor()));%></td>' +
					'<td style="width: 15%; text-align: center;" class="id_qtde_disponivel"><%out.print(item.getDisponivel_troca());%></td>' +
					'<td style="width: 15%; text-align: center;"><input class="qtde" style="border:solid 1px black;border-radius: 5px;text-align: center;" type="text"></td>' +
					'<td style="width: 15%; text-align: center; vertical-align: middle;"><input type="checkbox" class="check_troca"></td>' +
					'</tr>')					
			<% } %>
			$("#corpo_head").append("<script>$('.qtde').mask('###',{reverse: true});</scr"+"ipt>");
			
			$("#select_motivos").val(0)
			$('#txtObservacao').val('')
			
			//$('#btnSolicitar').val(6)
			$('#modalCancelamentoTroca').modal('show');
		})
		
		$("#btnSolicitar").click(function(){
			var troca = {}
			
			troca.pedido = <%out.print(pedido.getId());%>
			troca.observacao = $('#txtObservacao').val()
			
			troca.itens = []
			
			var quantidade_disferente = 0;
			$('#tabela_itens_troca tr').each(function(){
				if($(this).find('.check_troca').is(':checked')){
					var item = {
						"produto" : parseInt($(this).find('.id_produto').text()),
						"quantidade" : parseInt($(this).find('.qtde').val()),
						"valor" : parseFloat($(this).find(".valor").text().replace(".","").replace(",","."))
					}
					
					if(parseInt($(this).find('.qtde').val()) > parseInt($(this).find('.id_qtde_disponivel').text()) || parseInt($(this).find('.qtde').val()) <= 0 || $(this).find('.qtde').val().trim() == ""){
						quantidade_disferente = 1
					}
					
					troca.itens.push(item)
				}
			})
			
			if(troca.itens.length <= 0){
				toastr.error('Selecione ao menos um item para solicitar a troca !!')
				return;
			} else if(quantidade_disferente == 1){ 
				toastr.error('Quantidade de troca do Item divergente da quantidade disponivel')
				return;
			} else if($('#select_motivos').val() == 0 || $('#select_motivos').val() == null){
				toastr.error('Selecione um motivo valido !!')
				return;
			} else {
				troca.motivo = parseInt($('#select_motivos').val())
			}
			
			console.log(troca)
			$.ajax({
			    type: "POST",
			    url: "troca?operacao=Salvar",
			    contentType: "application/json", // NOT dataType!
			    data: JSON.stringify(troca),
			    success: function(retorno) {
			    	toastr.success('Solicitação de troca ' + retorno.id + ' gerada com sucesso !!')
			    	setTimeout(function() {
			    		window.location.href = "index.jsp";
			    	}, 1000);
			    }
			});
		});
		
		$("#btnCancelarPedido").click(function(){
			
			$.ajax({
			    type: "POST",
			    url: "pedido_status?operacao=Salvar&id=<%out.print(pedido.getId());%>&status=10",
			    contentType: "application/json", 
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
		
		$(document).ready(function() {
			$.get("troca_motivo?operacao=Lista",function(retorno) {
				$.each(retorno, function(key, item) {
					$("#select_motivos").append("<option value=" + item.id + ">"+ item.descricao + "</option>");
				});
			});
		})
	</script>
</body>

<%@ include file="complements/footer.jsp"%>
</html>
