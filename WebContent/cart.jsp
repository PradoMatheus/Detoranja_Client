<%@page import="java.text.DecimalFormat"%>
<%@page import="br.edu.fatec.detoranja.dominio.Carrinho_Itens"%>
<%@page import="br.edu.fatec.detoranja.dominio.Carrinho"%>
<html>
<head>
<title>Carrinho</title>
<meta charset="UTF-8">
<%@ include file="complements/complements_css.jsp"%>
<link type="text/css" rel="stylesheet" href="css/cart.css">
</head>
<body>

	<%@ include file="complements/navbar.jsp"%>

	<%
	Carrinho carrinho = (Carrinho) request.getAttribute("Carrinho");
	DecimalFormat df = new DecimalFormat();
	df.applyPattern("#,##0.00");
	%>

	<div class="cart_section">
		<div class="container-fluid">
			<div class="row">
				<div class="col-lg-10 offset-lg-1">
					<div class="cart_container">
						<div class="cart_title">
							Carrinho<small> (<%
						out.print(carrinho.getItens().size());
						%> item no carrinho)
							</small>
						</div>
						<%
						for (Carrinho_Itens item : carrinho.getItens()) {
						%>
						<div class="cart_items">
							<ul class="cart_list">
								<li class="cart_item clearfix">
									<div class="cart_item_image">
										<img
											src="<%out.print(item.getProduto().getImagens().get(0).getPath());%>"
											alt="" width="90">
									</div>
									<div
										class="cart_item_info d-flex flex-md-row flex-column justify-content-between">
										<div class="cart_item_name cart_info_col">
											<div class="cart_item_title">Titulo</div>
											<div class="cart_item_text">
												<%
												out.print(item.getProduto().getTitulo());
												%>
											</div>
										</div>
										<div class="cart_item_quantity cart_info">
											<div class="cart_item_title">Quantidade</div>
											<div class="cart_item_text">
												<a href="#" class="btnMenos" style="margin-right: 5%"><i
													class="fa fa-minus" aria-hidden="true"></i></a>
												<%
												out.print(item.getQuantidade());
												%>
												<a <% if(item.getQuantidade() < item.getEstoque()) { %> href="#" class="btnMais" <% } %> style="margin-left: 5%"><i
													class="fa fa-plus" aria-hidden="true"></i></a> <input
													type="hidden" class="idlivro"
													value="<%out.print(item.getId());%>" />
													<input
													type="hidden" class="quantidade"
													value="<%out.print(item.getQuantidade());%>" />
											</div>
										</div>
										<div class="cart_item_price cart_info_col">
											<div class="cart_item_title">Valor Unit.</div>
											<div class="cart_item_text">
												R$<%
											out.print(df.format(item.getProduto().getValor()));
											%>
											</div>
										</div>
										<div class="cart_item_total cart_info_col">
											<div class="cart_item_title">Valor Total</div>
											<div class="cart_item_text">
												R$<%
											out.print(df.format(item.getProduto().getValor() * item.getQuantidade()));
											%>
											</div>
										</div>
									</div>
								</li>
							</ul>
						</div>
						<%
						}
						%>
						<div class="order_total" style="height: 120px">
							<div class="row" style="margin-top: 2px; margin-left: 2px">
								<div class="p-2 d-flex"><strong>CEP</strong><input type="text" id="txtCEP" class="rounded border" style="margin-left: 5px"></div>
							</div>
							<div class="order_total_content text-md-right">
								<div class="">Total Produtos: R$ <strong style="size: 22;color: black;"><%out.print(df.format(carrinho.getValor_total()));%></strong></div>
							</div>
							<div class="order_total_content text-md-right" style="margin-top: 2px">
								<div class="">Frete: R$ <strong style="size: 22;color: black;" id="txtFrete">00,00</strong></div>
							</div>
							<div class="order_total_content text-md-right" style="margin-top: 2px">
								<div class="">Total: R$ <strong style="size: 22;color: black;" id="txtTotal"><%out.print(df.format(carrinho.getValor_total()));%></strong></div>
							</div>
						</div>
						<div class="cart_buttons">
							<a href="index.jsp"><button type="button" class="button cart_button_clear">Continuar
								Comprando</button></a>
							<button type="button" class="button cart_button_checkout btnFinalizar">
								Finalizar
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- Modal de Exclusão de Endereço-->
		<div class="modal fade" id="modalexcluir" tabindex="-1" role="dialog" aria-labelledby="modal_excluir" aria-hidden="true">
		  <div class="modal-dialog" role="document">
			    <div class="modal-content">
			      <div class="modal-header">
			        <h5 class="modal-title" id="labelTituloExclusao">Remover Livro </h5>
			        <button type="button" class="close" data-dismiss="modal" aria-label="Fechar">
			          <span aria-hidden="true">&times;</span>
			        </button>
			      </div>
			      <input type="hidden" id="id_excluir">
			      <div class="modal-body" id="labelContentExclusao">
			      	Remover o livro do carrinho ?
			      </div>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
			        <button type="button" class="btn btn-danger btnExcluir">Confirmar</button>
			      </div>
			   </div>
		  </div>
	</div>

	<%@ include file="complements/complements_js.jsp"%>

</body>

<script type="text/javascript">
	$(document).ready(function() {
		
		$(".btnFinalizar").click(function(){
			<%if(carrinho.getItens().size() > 0) {%>
				window.location.href = "./carrinho?operacao=Buscar&s=checkout";
			<%} else {%>
				toastr.info('Adicione itens ao carrinho para prosseguir!')
			<%}%>
		});
		
		// MASK DO CEP		
		$('#txtCEP').mask("00000-000");
		
		$("#txtCEP").keypress(function(e){
			
			var key = e.which;
			if(key == 13)  // the enter key code 
			{
				if ($(this).val().length == 9) {
					var frete = Math.floor(Math.random() * (30 - 8 + 1) + 8);
					var freteformat = frete.toLocaleString('pt-br', {minimumFractionDigits: 2});
					
					$("#txtFrete").text(freteformat);
					$("#txtTotal").text((frete + <%out.print(carrinho.getValor_total());%>).toLocaleString('pt-br', {minimumFractionDigits: 2}))
				} else {
					toastr.error('Digite um CEP valido')
				}
			}
		}); 
		
		$(".btnMais").click(function() {
			var id = $(this).parent().find(".idlivro").val();
			var qntd = parseInt($(this).parent().find(".quantidade").val()) + 1;
			
			$.ajax({
				type : "POST",
				url : "carrinho_itens?operacao=Salvar",
				contentType : "application/json", // NOT dataType!
				data : JSON.stringify({
					id : id,
					quantidade : qntd
				}),
				success : function(retorno) {
					console.log(retorno)
					if(retorno == true){
						toastr.success('Quantidade Atualizada adicionado ao Carrinho !')
						setTimeout(function() {
							location.reload(true);
						}, 200);
					}
				},
				error : function(data){
					console.log(data)
				}
			});
		});
		$(".btnMenos").click(function() {
			var id = $(this).parent().find(".idlivro").val();
			var qntd = parseInt($(this).parent().find(".quantidade").val()) - 1;
			
			if(qntd <= 0){
				$('#id_excluir').val(id);
				$('#modalexcluir').modal('show')
			}
			else {
				$.ajax({
					type : "POST",
					url : "carrinho_itens?operacao=Salvar",
					contentType : "application/json", // NOT dataType!
					data : JSON.stringify({
						id : id,
						quantidade : qntd
					}),
					success : function(retorno) {
						console.log(retorno)
						if(retorno == true){
							toastr.success('Quantidade Atualizada adicionado ao Carrinho !')
							setTimeout(function() {
								location.reload(true);
							}, 200);
						}
					},
					error : function(data){
						console.log(data)
					}
				});
			}
		});
		// FUNÇÃO QUE EXECUTA A EXCLUSÃO DO ENDEREÇO
		$(".btnExcluir").click(function(){		
			$.ajax({
				type : "POST",
				url : "carrinho_itens?operacao=Excluir",
				contentType : "application/json", // NOT dataType!
				data : JSON.stringify({
					id : $('#id_excluir').val()
				}),
				success : function(retorno) {
					console.log(retorno)
					if(retorno == true){
						toastr.success('Remoção do Livro realizada com sucesso !')
						setTimeout(function() {
							location.reload(true);
						}, 200);
					}
				},
				error : function(data){
					console.log(data)
				}
			});
		});
	});
</script>

<%@ include file="complements/footer.jsp"%>

</html>