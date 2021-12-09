<%@page import="java.text.DecimalFormat"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="br.edu.fatec.detoranja.dominio.Produto"%>
<%@page import="br.edu.fatec.detoranja.dominio.Carrinho_Itens"%>
<%@page import="br.edu.fatec.detoranja.dominio.Carrinho"%>
<html>
<head>
<title>Finalizar Pedido</title>
<meta charset="UTF-8">
<%@ include file="complements/complements_css.jsp"%>
<link type="text/css" rel="stylesheet" href="css/checkout.css">
<link type="text/css" rel="stylesheet" href="css/order_status.css">
</head>
<body>

	<%@ include file="complements/navbar.jsp"%>
	
	<%
	Carrinho carrinho = (Carrinho) request.getAttribute("Carrinho");
	DecimalFormat df = new DecimalFormat();
	df.applyPattern("#,##0.00");
	%>

	<div class="container rounded bg-white card_main">
		<div class="row d-flex justify-content-center pb-5">
			<div class="col-sm-5 col-md-5 ml-1">
				<div class="py-4 d-flex flex-row">
					<h5>
						<b>FINALIZAR PEDIDO</b>
					</h5>
				</div>
				<h4>Informacoes do Pedido</h4>
				<div class="d-flex pt-2">
					<div>
						<p>
							<b>Endereco de Entrega</b>
						</p>
					</div>
					<%@ include file="modals/consultar_endereco_checkout.jsp"%>
					<div class="ml-auto">
						<a class="text-primary novoEndereco" href="#">
							<i class="fa fa-plus-circle text-primary"></i> Adicionar Endereco
						</a>
					</div>
				</div>
				<div class="pb-3" id="formEndereco">
				</div>

				<hr>
				<div class="pt-2">
					<div class="d-flex">
						<div>
							<p>
								<b>Forma de Pagamento</b>
							</p>
						</div>
						<%@ include file="modals/consultar_cartao_credito_checkout.jsp"%>
						<div class="ml-auto p-2">
							<a class="text-primary btnCartao" href="#">
								<i class="fa fa-plus-circle text-primary"></i> Adicionar Cartao
							</a>
						</div>
					</div>
					<select class="custom-select mr-sm-2" id="txtQuantidadeCartoes" style="margin-bottom: 2px">
				        <option disabled="disabled">Quantidade de Cartoes</option>
				        <option value="0">Nao Utilizar Cartao</option>
				        <option selected value="1">1</option>
				        <option value="2">2</option>
				        <option value="3">3</option>
      				</select>
					<div class="pb-3" id="formCartoes">
					</div>
				</div>
				<div class="pt-2">
					<div class="d-flex">
						<div>
							<p>
								<b>Resumo dos Pedidos</b>
							</p>
						</div>
					</div>
					<form class="pb-3">
						<% for(Carrinho_Itens item : carrinho.getItens()) {%>
							<div class="d-flex flex-row align-content-center">
								<div class="rounded border d-flex w-100 px-2">
									<div class="pt-2">
										<p><% out.print(item.getQuantidade()); %>X <% out.print(item.getProduto().getTitulo()); %></p>
									</div>
									<div class="ml-auto pt-2">R$ <% out.print(df.format(item.getProduto().getValor())); %></div>
								</div>
							</div>
						<% } %>
					</form>
				</div>
			</div>
			<div class="col-sm-3 col-md-4 offset-md-1 mobile">
				<div class="py-4 d-flex justify-content-end">
					<h6>
						<a href="index.jsp">Cancelar e retornar para o site</a>
					</h6>
				</div>
				<div class="bg-light rounded d-flex flex-column">
					<div class="p-2 ml-3">
						<h4>Resumo</h4>
					</div>
					<div class="p-2 d-flex">
						<div class="col-8">Valor Produtos</div>
						<div class="ml-auto">R$ <% out.print(df.format(carrinho.getValor_total())); %></div>
					</div>
					<div class="p-2 d-flex">
						<div class="col-8">Frete<a data-placement="top" data-toggle="tooltip" title="5 Dias Uteis para Entrega"><i class="fa fa-info-circle"
								aria-hidden="true" style="margin-left: 2px"></i></a></div>
						<!--<div class="ml-auto">+ R$<strong id="txtFrete"><% double frete = Math.floor(Math.random() * (30 - 8 + 1) + 8); out.print(frete); %></strong></div> -->
						<div class="ml-auto">+ R$<strong id="txtFrete"><%out.print(10); %></strong></div>
					</div>
					<div class="p-2 d-flex">
						<div class="col-8">Cupom</div>
						<div class="ml-auto">
							<input class="rounded border" type="text" id="btnCupom">
						</div>
					</div>
					<div id="Listcupons"></div>
					<div class="p-2 d-flex">
						<div class="col-8">Desconto Total</div>
						<div class="ml-auto">- R$ <a id="txtDesconto">0.00</a></div>
					</div>
					<div class="border-top px-4 mx-3"></div>
					<div class="p-2 d-flex pt-3">
						<div class="col-8">
							<strong>Total</strong>
						</div>
						<div class="ml-auto">R$ <a id="txtTotal"><% out.print(carrinho.getValor_total() + 10); %></a></div>
					</div>
				</div>
				<div>
					<input type="button" id="btnConcluirPedido" value="Concluir Pedido"
						class="btn btn-primary btn-block btnConcluir">
				</div>
				<!-- Modal -->
				<div class="modal fade" id="ModalPagamentos" tabindex="-1" role="dialog" aria-hidden="true">
				  <div class="modal-dialog" role="document">
				    <div class="modal-content">
				      <div class="modal-header" style="justify-content: center;">
				        <h5 class="modal-title" id="exampleModalLabel">Finalizar Pagamentos</h5>
				      </div>
				      <div class="modal-body cartoes">
				      
				      <div style="text-align: center;margin-bottom: 10px">Valor a ser Pago: R$<strong id="txtValorPagar">00,00</strong></div>				        
				      </div>
				      <div class="modal-footer">
				        <button type="button" class="btn btn-secondary" data-dismiss="modal">Voltar</button>
				        <button type="button" class="btn btn-primary" id="btnComprar">Finalizar Pedido</button>
				      </div>
				    </div>
				  </div>
				</div>
			</div>
		</div>
	</div>

	<%@ include file="complements/complements_js.jsp"%>

</body>

<script type="text/javascript">

	var pedido = {};
	var cumpom_troca = false;

	$("#btnConcluirPedido").click(function(){
		
		pedido.Frete = $("#txtFrete").text();
		pedido.ValorTotal = $("#txtTotal").text();
		pedido.quantidade = <%out.print(carrinho.getQuantidade());%>
		
		pedido.Cupons = [];
		$('#Listcupons .cupons .id_cupom').each(function(index, element) {
			pedido.Cupons.push({
				"cupom_id" : element.id,
			})
		});
		
		pedido.Endereco = [];		
		// RECUPERAR O ID DO ENDERECO SELECIONADO
		if(typeof $('input[name="radioEndereco"]:checked').val() !== "undefined") {
			$('input[name="radioEndereco"]:checked').each(function() {
				pedido.Endereco.push(JSON.parse($(this).parent().find(".json_endereco").val()));
			});
		} else {
			toastr.error('Selecione um Endereço!')
			return;
		}
		
		pedido.Cartoes = [];
		// RECUPERAR O ID DO CARTAO SELECIONADO
		if(typeof $('input[name="radioCartao"]:checked').val() !== "undefined" || $('#txtQuantidadeCartoes').val() == 0) {
			if (parseInt($("#txtTotal").text()) >= 0  && $('#txtQuantidadeCartoes').val() == 0) {
				toastr.error('Adicione no minimo um cartão !!')
				return;
			}
			$('input[name="radioCartao"]:checked').each(function() {
				pedido.Cartoes.push(JSON.parse($(this).parent().find(".json_cartao").val()));
			});
		} else if (typeof $('input[name="radioCartao"]:checked').val() != $('#txtQuantidadeCartoes').val()){
			toastr.error('Selecione ' + $('#txtQuantidadeCartoes').val() + ' Cartao(s)!')
			return;
		}
		
		console.log(pedido);
		
		$("#txtValorPagar").text(parseFloat($("#txtTotal").text()).toFixed(2).replace(".",","));
		$("#ModalPagamentos .cartoes .card").remove()
		pedido.Cartoes.forEach(function(cartao){
			$("#ModalPagamentos .cartoes").append('<div class="card" style="width: 18rem;padding: 5px;margin: 0 auto; float:none; margin-bottom: 4px">' +
			'<div style="margin:3px" class="d-flex justify-content-center">' + cartao.bandeira  + ' - ' + cartao.numero_cartao + '</div>' +
				'<div class="row">' +
					'<div class="col">' +
				     	'<input type="text" class="form-control valorParcela" name="valorParcela" placeholder="Valor" value="">' +
				    '</div>' +
				    '<div class="col">' +
 				     	'<select name="txtParcelaCartao" style="margin-bottom: 2px">' +
					       	'<option disabled="disabled">Parcelas</option>' +
					        '<option selected value="1">1 x</option>' +
					        '<option value="2">2 x</option>' +
					        '<option value="3">3 x</option>' +
      					'</select>' +
				    '</div>' +
				'</div>' +
			'</div>');
		})
		
		$("#ModalPagamentos .cartoes").append("<script>$('.valorParcela').mask('#.##0,00',{reverse: true});</scr"+"ipt>");
		
		
		$('#ModalPagamentos').modal({
		    backdrop: 'static',
		    keyboard: false  // to prevent closing with Esc button (if you want this too)
		})
	});
	
	$("#btnComprar").click(function(){
		var totalizador = 0;
		var index = 0;
		var minimo_valor = 0;
		
		// RECUPERAR O ID DO ENDERECO SELECIONADO
		$('input[name="valorParcela"]').each(function() {
			if(parseFloat($(this).val()) < 10 && cumpom_troca == false){
				minimo_valor += 1;
			}
			totalizador += parseFloat($(this).val().replace(".","").replace(",","."));
			console.log(totalizador)
			pedido.Cartoes[index].valorParcela = $(this).val();
			index += parseInt(1);
		});
		
		if(minimo_valor == 0){
			console.log(cumpom_troca)
			if(parseFloat($("#txtTotal").text()) < 0 && cumpom_troca == true){
				
			} else if(parseFloat(totalizador) != parseFloat($("#txtTotal").text())){
				toastr.info("Valor a ser pago difere do valor final");
				return;
			} else {
			
				index = (0);
				// RECUPERAR O ID DO ENDERECO SELECIONADO
				$('select[name="txtParcelaCartao"]').each(function() {
					pedido.Cartoes[index].QtdeParcelas = $(this).val();
					index += parseInt(1);
				});
			}
			
			
			console.log(pedido)
	
				$.ajax({
				    type: "POST",
				    url: "pedido?operacao=Salvar",
				    contentType: "application/json", // NOT dataType!
				    data: JSON.stringify(pedido),
				    success: function(retorno) {
				    	toastr.success('Pedido ' + retorno.id + ' gerado com sucesso !!')
				    	setTimeout(function() {
				    		window.location.href = "index.jsp";
				    	}, 1000);
				    }
				});
		} else {
			toastr.warning('Cartao nao atingiu valor valor minimo de 10 reais')
		}
		
		});
	
	
	////////////////////////////////////------------------ CUPOM------------------
	$("#btnCupom").keypress(function(e){
		
		var key = e.which;
		if(key == 13)  // the enter key code 
		{		    
			if($(this).val().trim().length > 1)
				 // Função para buscar o cupom digitado
				$.get("cupom?operacao=Buscar&txt=" + $(this).val(),function(retorno) {
					
					if(!retorno.hasOwnProperty('id')){
						toastr.info(retorno)
					} else {
						cumpom_troca = false;
						
						if(retorno.tipo.id == 1){
							cumpom_troca = true;
							console.log(cumpom_troca)
						}
						
						var existe = false;
						var promocionais = 0;
						$('#Listcupons .cupons .id_cupom').each(function(index, element) {
							console.log(retorno.id + ' - ' +  element.id);
							if(retorno.id == element.id){
								existe = true
							}
							
							if(retorno.tipo.id == 2){
								promocionais += 1;
							} else {
								cumpom_troca = true;
							}
						});
						
						if(promocionais > 1){
							toastr.info('Limite de 1 cupom promocional atingido	 !!')
						} else if(existe){
							toastr.info('Cupom já aplicado !!')
						} else if(parseFloat(retorno.valor_minimo) > parseFloat($("#txtTotal").text()) && retorno.tipo.id == 2){
							toastr.info('Cupom promocional não pode ser utilizado !!')
						} else if(retorno.tipo.id == 1 && parseFloat($("#txtTotal").text()) < 0){
							toastr.info('Valor já pago com cupons !!')
						} else {
							$("#Listcupons").append('<div class="p-2 d-flex cupons">' +
								'<a href="#" class="id_cupom" id="' + retorno.id + '"><i class="fa fa-trash" style="margin-left: 2px"></i></a>' +
								'<div class="col-8">' + retorno.desc_cupom + '</div>' +
								'<div class="ml-auto desconto_cupom">- R$ <a>' + retorno.desconto + '</a></div>' +
							'</div>');
							
							$("#Listcupons .cupons #" + retorno.id).append(
									"<script>" + 
										"$('.id_cupom').click(function(){" + 
											"$(this).closest('.cupons').remove();" + 
											"somarDesconto();" + 
										"});" + 
										//"$('#txtDesconto').text(parseFloat(retorno.desconto) + parseFloat($('txtDesconto').text()))" +
									"</scr"+"ipt>");
							
							$("#btnCupom").val('')		
						}
					}
					somarDesconto()
				});
			else{
		    	toastr.info('Nenhum cupom digitado !!')
			}
		}
	});
	
	function somarDesconto(){
		var somatoria = 0
		$('#Listcupons .cupons .desconto_cupom a').each(function(index, element) {
			somatoria += parseFloat(element.text)
		});
		$("#txtDesconto").text(somatoria)
		const total = parseFloat(<% out.print(carrinho.getValor_total()); %>) + parseFloat($("#txtFrete").text()) - parseFloat(somatoria)
		$("#txtTotal").text(total.toFixed(2))
	}
		
	$(document).ready(function(){
		
		pedido.Itens = [];
		<%for(Carrinho_Itens item : carrinho.getItens()) {%>
			var item = {
					id_produto : <%out.print(item.getProduto().getId());%>,
					quantidade : <%out.print(item.getQuantidade());%>,
					valor : <% out.print(item.getProduto().getValor()); %>
			}
			pedido.Itens.push(item)
		<%}%>
		
		////////////////////////////////////------------------ ENDERECOS------------------
		
		// CLICAR NO CHECK DE UM Endereco
		$(document).on('click', '.inputEndereco', function () {
			//var frete = Math.floor(Math.random() * (30 - 8 + 1) + 8);
			$("#txtFrete").text(10)
			somarDesconto()
		})

		// Função para buscar os enderecos do usuario
		$.get("endereco?operacao=Lista&s=json",function(retorno) {
			$.each(retorno, function(key, item) {
				
				var endereco = "<div class='d-flex flex-row align-content-center'>" +
				"<div class='rounded border d-flex w-100 px-2'>" +
					"<div style='width:50%'>" +
						"<p>" + item.descricao + "</p>" +
					"</div>" +
					"<div style='width:35%'>" + item.cidade + "</div>" +	
					"<div style='width:10%'><a class='editEndereco' href='#'><i class='fa fa-edit'></i></a></div>" +	
					"<div style='width:5%'>" +
						"<div class='form-check'>";
				if(item.preferencial == true)
					endereco +=	"<input class='form-check-input inputEndereco' type='radio' name='radioEndereco' id='radioEndereco' value='" + item.id + "' checked>";
				else
					endereco +=	"<input class='form-check-input inputEndereco' type='radio' name='radioEndereco' id='radioEndereco' value='" + item.id + "'>";
				item.preferencial = false;
				endereco += "<input class='json_endereco' type='hidden' value='" + JSON.stringify(item) + "'>";
						"</div>" +
					"</div>" +
				"</div>"
				
				$("#formEndereco").append(endereco);
			});
		});
		
		// FUNÇÃO ATIVADA QUANDO O CLIENTE QUER SALVAR OU ATUALIZAR UM ENDERECO
		$('#formEndereco_body').submit(function(event) {
			event.preventDefault();
			
			var endereco = {
					//txtId: $("#txtId").val(),
					txtId: Math.random().toString(36).replace(/[^a-z]+/g, '').substr(0, 5),
					txtDescricao: $("#txtDescricao").val(),
					txtCEP: $("#txtCEP").val(),
					txtBairro: $("#txtBairro").val(),
					txtCidade: $("#txtCidade").val(),
					txtPais: $("#txtPais").val(),
					txtEstado: $("#txtEstado").val(),
					txtTipoLogradouro: $("#txtTipoLogradouro").val(),
					txtLogradouro: $("#txtLogradouro").val(),
					txtNumero: $("#txtNumero").val(),
					txtComplemento: $("#txtComplemento").val(),
					txtReferencia: $("#txtReferencia").val(),
					txtSalvar: document.getElementById("txtSalvarEndereco").checked
				};
			
			var adicionarEndereco = "<div class='d-flex flex-row align-content-center'>" +
			"<div class='rounded border d-flex w-100 px-2'>" +
				"<div style='width:50%'>" +
					"<p>" + endereco.txtDescricao + "</p>" +
				"</div>" +
				"<div style='width:35%'>" + endereco.txtCidade + "</div>" +	
				"<div style='width:10%'><a class='editEndereco' href='#'><i class='fa fa-edit'></i></a></div>" +	
				"<div style='width:5%'>" +
					"<div class='form-check'>"  +
					"<input class='json_endereco' type='hidden' value='" + JSON.stringify(endereco) + "'>" +
					"<input class='form-check-input' type='radio' name='radioEndereco' id='radioEndereco' value='" + endereco.txtId + "'>" +
					"</div>" +
				"</div>" +
			"</div>";
			
			$("#formEndereco").append(adicionarEndereco);
			
			$('#modalEndereco').modal('hide');
		});
		
		// MASCARA PARA COLOCAR O CEP
		$('#txtCEP').mask("00000-000", {selectOnFocus: true});
		
		// Função para buscar as opções de categorias cadastradas e disponibiliza-las ao usuario
		$.get("tipo_logradouro?operacao=Lista",function(retorno) {
			$.each(retorno, function(key, item) {
				$("#txtTipoLogradouro").append("<option value=" + item.id + ">"+ item.descricao + "</option>");
			});
		});
		
		// BOTÃO QUE CARREGA UM MODAL PARA CADASTRAR UM NOVO ENDEREÇO
		$(".novoEndereco").click(function() {
			$('#labelEndereco').html('Novo Endereço')
			$("#btnSalvar").html("Salvar");
			$('#modalEndereco').modal('show')
		});
		
		// Função chamada quando a Modal é fechada
		$("#modalEndereco").on('hidden.bs.modal',function(){
			$("#txtId").val(0)
			$("#btnSalvar").html('Salvar')
			$("#txtDescricao").val('');
			$("#txtCEP").val('');
			$("#txtBairro").val('');
			$("#txtCidade").val('');
			$("#txtEstado").val('');
			$("#txtPais").val('');
			$("#txtLogradouro").val('');
			$("#txtTipoLogradouro").val('');
			$("#txtNumero").val('');
			$("#txtComplemento").val('');
			$("#txtReferencia").val('');
			$('#txtSalvarEndereco').prop("checked", false)
		})
		
		/*$(document).on('click', '.editEndereco', function (evt) {
			$('input[name="radioEndereco"]').each(function() {
				alert($(this).val());
			});
		});*/
		
		////////////////////////////////////------------------ CARTOES------------------
		
		$('#txtQuantidadeCartoes').change(function(){
			$("input[name=radioCartao]").prop({
		        checked: false
		    });
		});
		
		// CLICAR NO CHECK DE UM CARTAO
		$(document).on('click', '.validarCartao', function () {
			var limite = $('#txtQuantidadeCartoes').val();
			
			if($('.validarCartao:checked').length > limite) {
				event.preventDefault();
				toastr.info('O(s) Cartoes ja forao selecionados, verifique !!')
			}
		})
		
		// MASK PARA O MODAL DE CARTAO		
		$('#txtCvv').mask("000");
		$('#txtNumeroCartao').mask("0000 0000 0000 0000", {
			selectOnFocus : true
		});
		
		// Função para buscar os Cartoes do usuario
		$.get("forma_pagamento?operacao=Lista&s=json",function(retorno) {
			$.each(retorno, function(key, item) {
				var numero = item.numero_cartao.toString();
				var cartao = "<div class='d-flex flex-row align-content-center'>" +
					 "<div class='rounded border d-flex w-100 px-2'>" +
						"<div style='width:35%'>" +
							"<p><i class='fa fa-cc-visa text-primary pr-2'></i>" + item.bandeira + "</p>" +
						"</div>" +
						"<div style='width:40%'>**** **** **** " + numero.substring(12,16) + "</div>" +
						"<div style='width:20%'><a class='editcartao' href='#'><i class='fa fa-edit'></i></a></div>" +	
						"<div style='width:5%'>" +
							"<div class='form-check'>";
					if(item.preferencal == true)
						cartao +=	"<input class='form-check-input validarCartao' type='checkbox' name='radioCartao' id='radioCartao' value='" + item.id + "' checked>";
					else
						cartao +=	"<input class='form-check-input validarCartao' type='checkbox' name='radioCartao' id='radioCartao' value='" + item.id + "'>";
					cartao += "<input class='json_cartao' type='hidden' value='" + JSON.stringify(item) + "'>" +
					    "</div>" +
						"</div>" +
					"</div>" +
				"</div>"
				
				$("#formCartoes").append(cartao);
					
			});
		});
		
		//FUNCAO PARA LIMPAR A MODAL DE CARTAO QUANDO ELA É FECHADA
		$("#modalForma").on('hidden.bs.modal',function(){
			$("#txtId").val(0)
			$("#txtBandeira").val(0)
			$("#txtNumeroCartao").val('')
			$("#txtAnoValidade").val(2021)
			$("#txtMesValidade").val(1)
			$("#txtCvv").val('')
			$("#txtNome").val('')
			$('#txtPreferencia').prop("checked", false)
		});
		
		// FUNÇÃO PARA EXIBIR O FORM DE CARTAO
		$(".btnCartao").click(function() {
			$('#labelForma').html('Nova Cartão de Credito')
			$('#btnSalvar').html('Salvar')
			$('#modalForma').modal('show');
		});
		
		// FUNÇÃO ATIVADA QUANDO O CLIENTE QUER SALVAR OU ATUALIZAR UM CARTÃO
		$('#formCartao').submit(function(event) {
			event.preventDefault();
			
			var cartao = {
			    	txtId: Math.random().toString(36).replace(/[^a-z]+/g, '').substr(0, 5),
					txtBandeira: $("#txtBandeira").val(),
					txtNumeroCartao: $("#txtNumeroCartao").val(),
					txtAnoValidade: $("#txtAnoValidade").val(),
					txtMesValidade: $("#txtMesValidade").val(),
					txtCvv: $("#txtCvv").val(),
					txtNome: $("#txtNome").val(),
					txtSalvar: document.getElementById("txtPreferencia").checked
			    };
			
			var adicionarCartao = "<div class='d-flex flex-row align-content-center'>" +
				 "<div class='rounded border d-flex w-100 px-2'>" +
					"<div style='width:55%'>" +
						"<p><i class='fa fa-cc-visa text-primary pr-2'></i>" + cartao.txtBandeira + "</p>" +
					"</div>" +
					"<div style='width:40%'>**** **** **** " + cartao.txtNumeroCartao.substring(15,19) + "</div>" +
					"<div style='width:10%'><a class='editcartao' href='#'><i class='fa fa-edit'></i></a></div>" +	
					"<div style='width:5%'>" +
						"<div class='form-check'>" +
						"<input class='json_cartao' type='hidden' value='" + JSON.stringify(cartao) + "'>";
						adicionarCartao +=	"<input class='form-check-input validarCartao' type='checkbox' name='radioCartao' id='radioCartao' value='" + cartao.txtId + "'>";
						adicionarCartao +="</div>" +
					"</div>" +
				"</div>" +
			"</div>";
			
			console.log(cartao)
			
			$("#formCartoes").append(adicionarCartao);
			
			$('#modalForma').modal('hide');
		});
	});
</script>

<%@ include file="complements/footer.jsp"%>

</html>