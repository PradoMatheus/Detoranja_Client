<%@page import="br.edu.fatec.detoranja.dominio.Endereco"%>
<%@page import="java.util.List"%>
<html>
	<head>
		<title>Endereços</title>
		<meta charset="UTF-8">
	<%@ include file="complements/complements_css.jsp"%>
	</head>
	<body style="background-color: #EAE6E5">
	
		<%@ include file="complements/navbar.jsp"%>
		
		<%
		List<Endereco> listaEnderecos = (List<Endereco>) request
				.getAttribute("listaEndereco");
		%>
		
		<br>
		<div class='container' style="background-color: #FFFFFF;height: 30em;border-radius: 1em;">
			<div class="py-3 text-center">
				<h2>Lista de Endereços</h2>
			</div>
			<div class="form-row" style="position: relative;">
			  <div class="form-group col-md-3">
			  		<button class="btn btn-Danger novoEndereco" type="submit"><img src="./icons/add.svg" height="20px" style="margin-right: 5px">Adicionar Endereço</button>
			  </div>
			</div>
			<div style="height: 280px">
				<table class="table table-bordered table-sm" <% if(listaEnderecos.size() >= 1) { out.print("id='datatablesSimple'"); } %>>
					<thead>
						<tr>
							<th scope="col" style="width: 5%; text-align: center;border:1px solid black;">ID</th>
							<th scope="col" style="width: 60%;border:1px solid black;">Descrição</th>
							<th scope="col" style="width: 5%; border: 1px solid black;">Pricipal</th>
							<th scope="col" style="width: 15%; text-align: center;border:1px solid black;">Editar</th>
							<th scope="col" style="width: 15%; text-align: center;border:1px solid black;">Excluir</th>																					
						</tr>
					</thead>
					<tbody>
					<%
					if (listaEnderecos != null && listaEnderecos.size() > 0) {
						int i = 1;
						for (Endereco endereco : listaEnderecos) {
							if(i <= 5)
								out.print("<tr id='endereco_" + i++ + "'>");
							else
								out.print("<tr style='display:none' id='endereco_" + i++ + "'>");
							out.print("<th scope='row' style='vertical-align: middle;text-align: center'>" + endereco.getId() + "</th>");
							out.print("<td style='vertical-align: middle;'>" + endereco.getDescricao() + "</td>");
							if(endereco.isPreferencial())
								out.print("<td style='vertical-align: middle;text-align: center'><i class='fa fa-check-circle fa-2x' sty></i></td>");
							else
								out.print("<td style='vertical-align: middle;text-align: center'><i class='fa fa-times-circle fa-2x'></i></td>");
							out.print("<td style='text-align: center;''><button type='button' class='btn btn-outline-danger editarEndereco' value='" + endereco.getId() + "'>Editar</button></td>");
							out.print("<td style='text-align: center;''><button type='button' class='btn btn-danger excluirEndereco' value='" + endereco.getId() + "'>Excluir</button></td>");
							out.print("</tr>");
						}
					} else {
						out.print("<tr>");
						out.print("<td style='text-align: center' colspan='5'>Sem Endereços Cadastrados!</td>");
						out.print("</tr>");
					}
					%>
					</tbody>
				</table>
			</div>
		</div>
		
		<%@ include file="modals/consultar_endereco.jsp"%>
	
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

		<%@ include file="complements/complements_js.jsp"%>
		
		<script type="text/javascript">
		$(document).ready(function() {
			
			$('#datatablesSimple').DataTable({
				"language" : {
					"url" : "//cdn.datatables.net/plug-ins/1.11.1/i18n/pt_br.json"
					}
			});
			
			// FUNÇÃO ATIVADA QUANDO O CLIENTE QUER SALVAR OU ATUALIZAR UM ENDERECO
			$('#formEndereco').submit(function(event) {
				event.preventDefault();
				
				$.ajax({
				    type: "POST",
				    url: "endereco?operacao=Salvar",
				    contentType: "application/json", // NOT dataType!
				    data: JSON.stringify({
						txtId: $("#txtId").val(),
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
						txtPreferencial: document.getElementById("txtPreferencia").checked
					}),
				    success: function(retorno) {
				    		toastr.success('Endereco foi Atualizado/Salvo com sucesso!')
						setTimeout(function() {
							location.reload(true);
						}, 500);
				    }
				});
			});
			
			// Função para buscar as opções de categorias cadastradas e disponibiliza-las ao usuario
			$.get("tipo_logradouro?operacao=Lista",function(retorno) {
				$.each(retorno, function(key, item) {
					$("#txtTipoLogradouro").append("<option value=" + item.id + ">"+ item.descricao + "</option>");
				});
			});
			
			// MASCARA PARA COLOCAR O CEP
			$('#txtCEP').mask("00000-000", {selectOnFocus: true});
			
			// BOTÃO PARA CHAMAR O MODAL DE CONFIRMAÇÃO DE EXCLUSÃO
			$(".excluirEndereco").click(function(){
				$('#modalexcluir').modal('show')
				$("#labelContentExclusao").html("Confirmar a exclusão do Endereço " + $(this).val() + " ?")
				$('.btnExcluir').val($(this).val())
			});
			
			// FUNÇÃO QUE EXECUTA A EXCLUSÃO DO ENDEREÇO
			$(".btnExcluir").click(function(){
				$.post("endereco?operacao=Excluir&id=" + $(this).val(),function(retorno) {			
					
					toastr.success('Endereco Excluido com sucesso !!')

					setTimeout(function(){
						location.reload(true);
					},500);
				});
			});
			
			// FUNÇÃO QUE CARREGA O ENDEREÇO SELECIONADO ATRAVEZ DE UM GET.
			$(".editarEndereco").click(function(){
				$('#labelEndereco').html('Editar Endereço')
				$.get("endereco?operacao=Buscar&id=" + $(this).val(),function(retorno) {
					console.log(retorno)
					$("#txtId").val(retorno.id);
					$("#txtDescricao").val(retorno.descricao);
					$("#txtCEP").val(retorno.cep);
					$("#txtBairro").val(retorno.bairro);
					$("#txtCidade").val(retorno.cidade);
					$("#txtEstado").val(retorno.estado);
					$("#txtPais").val(retorno.pais);
					$("#txtLogradouro").val(retorno.pais);
					$("#txtTipoLogradouro").val(retorno.tipo_logradouro.id);
					$("#txtNumero").val(retorno.numero);
					$("#txtComplemento").val(retorno.complemento);
					$("#txtReferencia").val(retorno.referencia);
					$('#txtPreferencia').prop("checked", retorno.preferencial);
					$("#btnSalvar").html("Atualizar");
				});
				$('#modalEndereco').modal('show')
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
				$('#txtPreferencia').prop("checked", false)
			})
		});
		</script>

	</body>
	
	<%@ include file="complements/footer.jsp"%>
</html>
