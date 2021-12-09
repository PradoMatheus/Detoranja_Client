<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="br.edu.fatec.detoranja.dominio.Cliente"%>
<html>
<head>
<title>Meus Dados</title>
<meta charset="UTF-8">
<%@ include file="complements/complements_css.jsp"%>
</head>
<body>

	<%@ include file="complements/navbar.jsp"%>

	<%
	Cliente cliente = (Cliente) request.getAttribute("cliente");
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	%>

	<div class="container">
		<div class="py-5 text-center">
			<h2>Configurações</h2>
		</div>
		<form id="formConfiguracoes" action="#">
		<div class="row">
			<div class="col-md-6 order-md-2 mb-4">
				<div class="form-group row">
					<label for="txtNome" class="col-sm-2 col-form-label">Nome</label>
					<div class="col-sm-10">
						<%
						out.print("<input type='text' class='form-control' id='txtNome' name='txtNome' placeholder='Nome' value='"
								+ cliente.getNome() + "' required>");
						%>
					</div>
				</div>
				<div class="form-group row">
					<label for="txtEmail" class="col-sm-2 col-form-label">E-mail</label>
					<%
					out.print("<div class='col-sm-7'>");
					out.print("<input type='text' class='form-control' id='txtEmail' name='txtEmail' value='" + cliente.getEmail()
							+ "' required readonly>");
					out.print("</div>");
					%>
					<div class="col-sm-3">
						<button type="button" class="btn btn-success btn-block">Alterar</button>
					</div>
				</div>
				<br>
				<div class="form-group row">
					<label for="txtPasswordFake" class="col-sm-2 col-form-label">Senha</label>
					<div class="col-sm-7">
						<%
						out.print("<input type='hidden' id='txtPassword' name='txtPassword' value='" + cliente.getSenha() + "'>");
						%>
						<input type="password" class="form-control" id="txtPasswordFake"
							name="txtPasswordFake" placeholder="********" readonly>
					</div>
					<div class="col-sm-3">
						<button type="button" class="btn btn-success btn-block">Alterar</button>
					</div>
				</div>
			</div>
			<div class="col-md-6 order-md-2 mb-4">
				<div class="form-group row">
					<label for="txtDataNascimento" class="col-sm-4 col-form-label">Data
						de Nascimento</label>
					<div class="col-sm-8">
						<%
						out.print(
								"<input type='date' class='form-control' id='txtData' value='"
								+ cliente.getData_nascimento().format(formatter) + "'>");
						%>
					</div>
				</div>
				<div class="form-group row">
					<label for="txtCPF" class="col-sm-4 col-form-label">CPF</label>
					<div class="col-sm-8">
						<%
						out.print("<input type='text' class='form-control' id='txtCPF' name='txtCPF' placeholder='XXX.XXX.XXX-XX' value='"
								+ cliente.getCpf() + "' required>");
						%>
					</div>
				</div>
				<div class="form-group row">
					<label class="d-inline-block" for="txtSexo">Gênero</label>
					<div class="col-sm-10">
					    <select class="form-control" id="txtSexo" name="txtSexo">
							<option value="" disabled>Selecione</option>
						</select>
					</div>
				</div>
				<div class="form-group row">
					<label for="txtTelefone" class="col-sm-4 col-form-label">Telefone</label>
					<div class="col-sm-8">
						<%
						out.print("<input type='text' class='form-control' id='txtTelefone' name='txtTelefone' placeholder='(XX) XXXXX-XXXX' value='"
								+ cliente.getTelefone() + "' required>");
						%>
					</div>
				</div>
			</div>
		</div>
		<div class="py-5 text-center">
			<button class="btn btn-primary" type="submit" name="operacao"
				value="Salvar" id="btnSalvar">Salvar Configurações</button>
		</div>
		<div class="modal" id="modal_informativo" data-backdrop="static" tabindex="-1" role="dialog">
		  <div class="modal-dialog modal-dialog-centered" role="document">
		    <div class="modal-content">
		      <div class="modal-body" id="alert_message" style="text-align: center;">
		      </div>
		    </div>
		  </div>
		</div>
	</form>
	</div>


	<%@ include file="complements/complements_js.jsp"%>

	<script type="text/javascript">
		$(document).ready(function() {
			
			//Função Get para buscar os generos
			$.get("cliente_genero?operacao=Lista",function(retorno) {
				console.log(retorno)
				retorno.forEach(function(genero){
					if(genero.id == <%out.print(cliente.getGenero().getId());%>)
						$("#txtSexo").append('<option value="' + genero.id + '" selected="selected">' + genero.descricao + '</option>')
					else
						$("#txtSexo").append('<option value="' + genero.id + '">' + genero.descricao + '</option>')
				})
			});

			// FUNÇÃO ATIVADA QUANDO O CLIENTE QUER SALVAR OU ATUALIZAR UM CARTÃO
			$('#formConfiguracoes').submit(function(event) {
				event.preventDefault();
				
				var data = {
					txtNome : $("#txtNome").val(),
					txtCPF : $("#txtCPF").val(),
					txtEmail : $("#txtEmail").val(),
					txtData : $("#txtData").val(),
					txtPassword : $("#txtPassword").val(),
					txtSexo: $("#txtSexo").val(),
					txtTelefone: $("#txtTelefone").val()
				};
				$.ajax({
					type : "POST",
					url : "cliente?operacao=Salvar",
					contentType : "application/json", // NOT dataType!
					data : JSON.stringify(data),
					success : function(retorno) {
						$('#modal_informativo').modal('show')
						
						$('#alert_message').append("Configurações Salvas com sucesso !!");

						setTimeout(function(){
							location.reload(true);
						},500);
					}
				});
			});
			// MASCARA DO CPF
			$('#txtCPF').mask("000.000.000-00", {
				selectOnFocus : true
			});
			// MASCARA DA DATA DE NASCIMENTO
			$('#txtDataNascimento').mask("00/00/0000", {
				selectOnFocus : true
			});
			// MASCARA DO TELEFONE
			$('#txtTelefone').mask("(00) 00000-0000", {
				selectOnFocus : true
			});
		});
	</script>

</body>

<%@ include file="complements/footer.jsp"%>
</html>