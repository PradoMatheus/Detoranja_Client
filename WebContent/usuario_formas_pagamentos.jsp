<%@page import="br.edu.fatec.detoranja.dominio.Forma_Pagamento"%>
<%@page import="java.util.List"%>
<html>
<head>
<title>Formas de Pagamento</title>
<meta charset="UTF-8">
<%@ include file="complements/complements_css.jsp"%>
</head>
<body style="background-color: #EAE6E5">

	<%@ include file="complements/navbar.jsp"%>

	<%
	List<Forma_Pagamento> listaFormaPagamento = (List<Forma_Pagamento>) request.getAttribute("listaFormasPagamento");
	%>

	<br>
	<div class='container'
		style="background-color: #FFFFFF; height: 30em; border-radius: 1em;">
		<div class="py-3 text-center">
			<h2>Lista de Formas de Pagamentos</h2>
		</div>
		<div class="form-row" style="position: relative;">
			<div class="form-group col-md-3">
				<button class="btn btn-Danger novaForma" id="novaForma" type="submit">
					<img src="./icons/add.svg" height="20px" style="margin-right: 5px">Adicionar
					Forma de Pag.
				</button>
			</div>
		</div>
		<br>
		<div style="height: 280px">
			
			<table class="table table-bordered table-sm" <% if(listaFormaPagamento.size() >= 1) { out.print("id='datatablesSimple'"); } %>>
				<thead>
					<tr>
						<th scope="col"
							style="width: 5%; text-align: center; border: 1px solid black;">ID</th>
						<th scope="col" style="width: 60%; border: 1px solid black;">Numero
							do Cartão</th>
							<th scope="col" style="width: 5%; border: 1px solid black;">Pricipal</th>
						<th scope="col"
							style="width: 15%; text-align: center; border: 1px solid black;">Editar</th>
						<th scope="col"
							style="width: 15%; text-align: center; border: 1px solid black;">Excluir</th>
					</tr>
				</thead>
				<tbody>
					<%
					if (listaFormaPagamento != null && listaFormaPagamento.size() > 0) {
						int i = 1;
						for (Forma_Pagamento forma_pagamento : listaFormaPagamento) {
							if (i <= 5)
						out.print("<tr id='endereco_" + i++ + "'>");
							else
						out.print("<tr style='display:none' id='endereco_" + i++ + "'>");
							out.print("<th scope='row' style='vertical-align: middle;text-align: center'>" + forma_pagamento.getId()
							+ "</th>");
							out.print("<td style='vertical-align: middle;'>**** **** **** " + forma_pagamento.getNumero_cartao().toString().substring(12, 16) + "</td>");
							if(forma_pagamento.isPreferencal())
								out.print("<td style='vertical-align: middle;text-align: center'><i class='fa fa-check-circle fa-2x' sty></i></td>");
							else
								out.print("<td style='vertical-align: middle;text-align: center'><i class='fa fa-times-circle fa-2x'></i></td>");
							out.print(
							"<td style='text-align: center;''><button type='button' class='btn btn-outline-danger editarForma' value='"
									+ forma_pagamento.getId() + "'>Editar</button></td>");
							out.print("<td style='text-align: center;''><button type='button' class='btn btn-danger excluirForma' value='"
							+ forma_pagamento.getId() + "'>Excluir</button></td>");
							out.print("</tr>");
						}
					} else {
						out.print("<tr>");
						out.print("<td style='text-align: center' colspan='5'>Sem Formas de Pagamentos Cadastrados!</td>");
						out.print("</tr>");
					}
					%>
				</tbody>
			</table>
		</div>
		
		<%@ include file="modals/consultar_cartao_credito.jsp"%>
		
		<!-- Modal de Exclusão de Endereço-->
		<div class="modal fade" id="modalexcluir" tabindex="-1" role="dialog" aria-labelledby="modal_excluir" aria-hidden="true">
		  <div class="modal-dialog" role="document">
			    <div class="modal-content">
			      <div class="modal-header">
			        <h5 class="modal-title" id="labelTituloExclusao">Excluir Endereço </h5>
			        <button type="button" class="close" data-dismiss="modal" aria-label="Fechar">
			          <span aria-hidden="true">&times;</span>
			        </button>
			      </div>
			      <div class="modal-body" id="labelContentExclusao">
			      </div>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
			        <button type="button" class="btn btn-danger btnExcluir">Confirmar</button>
			      </div>
			   </div>
		  </div>
		</div>
	</div>

	<%@ include file="complements/complements_js.jsp"%>

	<script type="text/javascript">
		$(document).ready(function() {
			
			$('#datatablesSimple').DataTable({
				"language" : {
					"url" : "//cdn.datatables.net/plug-ins/1.11.1/i18n/pt_br.json"
					}
			});
			
			// FUNÇÃO ATIVADA QUANDO O CLIENTE QUER SALVAR OU ATUALIZAR UM CARTÃO
			$('#formCartao').submit(function(event) {
				event.preventDefault();
				
				$.ajax({
				    type: "POST",
				    url: "forma_pagamento?operacao=Salvar",
				    contentType: "application/json", // NOT dataType!
				    data: JSON.stringify({
				    	txtId: $("#txtId").val(),
						txtBandeira: $("#txtBandeira").val(),
						txtNumeroCartao: $("#txtNumeroCartao").val(),
						txtAnoValidade: $("#txtAnoValidade").val(),
						txtMesValidade: $("#txtMesValidade").val(),
						txtCvv: $("#txtCvv").val(),
						txtNome: $("#txtNome").val(),
						txtPreferencia: document.getElementById("txtPreferencia").checked
				    }),
				    success: function(retorno) {
				    	toastr.success('Cartão foi Atualizado/Salvo com sucesso!')
						setTimeout(function() {
							location.reload(true);
						}, 500);
				    }
				});
			});

			$('#txtCvv').mask("000");
			$('#txtNumeroCartao').mask("0000 0000 0000 0000", {
				selectOnFocus : true
			});
			
			// Função chamada quando a Modal é fechada
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

			$(".novaForma").click(function() {
				$('#labelForma').html('Nova Cartão de Credito')
				$('#btnSalvar').html('Salvar')
				$('#modalForma').modal('show');
			});
			
			$('.editarForma').click(function(){
				$('#labelForma').html('Editar Forma de Pagamento')
				$('#btnSalvar').html('Atualizar')
				$.get("forma_pagamento?operacao=Buscar&id=" + $(this).val(),function(retorno) {
					console.log(retorno)
					$("#txtId").val(retorno.id);
					$("#txtNumeroCartao").val(retorno.numero_cartao);
					$("#txtBandeira").val(retorno.bandeira);
					$("#txtAnoValidade").val(retorno.ano_validade)
					$("#txtMesValidade").val(retorno.mes_validade)
					$("#txtCvv").val(retorno.cvv)
					$("#txtNome").val(retorno.nome)
					$('#txtPreferencia').prop("checked", retorno.preferencal)
				});
				$('#modalForma').modal('show');
			});
			
			$(".excluirForma").click(function(){
				$('#modalexcluir').modal('show')
				$("#labelContentExclusao").html("Confirmar a exclusão da Forma de Pagamento " + $(this).val() + " ?")
				$('.btnExcluir').val($(this).val())
			});
			
			$(".btnExcluir").click(function(){
				$.post("forma_pagamento?operacao=Excluir&id=" + $(this).val(),function(retorno) {	
					setTimeout(function(){
						toastr.success(retorno)
						setTimeout(function() {
							location.reload(true);
						}, 500);
					},500);	
				});
			});
		})
	</script>
</body>

<%@ include file="complements/footer.jsp"%>
</html>
