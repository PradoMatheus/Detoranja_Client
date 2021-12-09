<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="br.edu.fatec.detoranja.dominio.Cliente"%>
<html>
<head>
<title>Novo Usuario</title>
<meta charset="UTF-8">
<%@ include file="../complements/complements_css.jsp"%>

<style>
.alert {
	padding: 20px;
	background-color: #f44336;
	color: white;
}

.closebtn {
	margin-left: 15px;
	color: white;
	font-weight: bold;
	float: right;
	font-size: 22px;
	line-height: 20px;
	cursor: pointer;
	transition: 0.3s;
}

.closebtn:hover {
	color: black;
}
</style>

</head>
<body style="background-color: #EAE6E5">

	<%@ include file="../complements/navbar.jsp"%>

	<br>
	<div class="container"
		style="background-color: #FFFFFF; height: 40em; border-radius: 1em; width: 50%">
		<div class="py-4 text-center">
			<h3>Preecha com seus dados</h3>
		</div>
		<div class="row">
			<div class="col-md-6 order-md-2 mb-4">
				<div class="form-group row">
					<div class="col-md-12 position-relative">
						<label for="txtNome" class="form-label">Nome</label> <input
							type="text" class="form-control" id="txtNome"
							placeholder="Digite o seu nome">
						<div class="invalid-tooltip">Preencha um Nome Valido.</div>
					</div>
				</div>
				<div class="form-group row">
					<div class="col-md-12 position-relative">
						<label for="txtEmail" class="form-label">E-Mail</label> <input
							type="email" class="form-control" id="txtEmail"
							placeholder="Digite um E-mail">
						<div class="invalid-tooltip" id="msg_error_email"></div>
					</div>
				</div>
				<div class="form-group row">
					<div class="col-md-12 position-relative">
						<label for="txtSenha" class="form-label">Senha</label>
						<div class="input-group mb-3">
							<input type="password" class="form-control" id="txtSenha"
								placeholder="Digite uma Senha">
							<div class="invalid-tooltip">Preencha uma senha Valida.</div>
							<input type="hidden" id="txtSenhaValor">
							<div class="input-group-prepend">
								<span class="input-group-text" id="senha_color"
									style="background-color: red;"></span>
							</div>
						</div>
					</div>
				</div>
				<div class="form-group row">
					<div class="col-md-12 position-relative">
						<label for="txtConfirmarPassword" class="form-label">Confirmar
							Senha</label>
						<div class="input-group mb-3">
							<input type="password" class="form-control"
								id="txtConfirmarPassword" placeholder="Digite uma Senha">
							<div class="invalid-tooltip" id="confirmarSenhaErr">
								<div>
								</div>
							</div>
							<input type="hidden" id="txtSenhaConfirmarValor">
							<div class="input-group-prepend">
								<span class="input-group-text" id="senha_confirmar"
									style="background-color: red;"></span>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-6 order-md-2 mb-4">
				<div class="form-group row">
					<div class="col-md-12 position-relative">
						<label for="txtDataNascimento" class="form-label">Data de
							Nascimento</label> <input type="date" class="form-control" id="txtData">
						<div class="invalid-tooltip">Preencha uma Data de Nascimento
							Valida.</div>
					</div>
				</div>
				<div class="form-group row">
					<div class="col-md-12 position-relative">
						<label for="txtCPF" class="form-label">CPF</label> <input
							type="text" class="form-control" id="txtCPF"
							placeholder="XXX.XXX.XXX-XX">
						<div class="invalid-tooltip" id="msg_error_cpf"></div>
					</div>
				</div>
				<div class="form-group row">
					<div class="col-md-12 position-relative">
						<label for="txtSexo" class="form-label">Gênero</label> <select
							class="form-control" id="txtSexo" name="txtSexo">
							<option value="" selected disabled>Selecione</option>
						</select>
						<div class="invalid-tooltip">Preencha um Sexo Valido.</div>
					</div>
				</div>
				<div class="form-group row">
					<!-- <div class="col-md-6 position-relative">
						<label for="txtContato" class="form-label">Contato</label> <input
							type="text" class="form-control" id="txtContato">
						<div class="invalid-tooltip" id="msg_error_contato"></div>
					</div> -->
					<div class="col-md-12 position-relative">
						<label for="txtTelefone" class="form-label">Telefone</label> <input
							type="text" class="form-control" id="txtTelefone"
							placeholder="(XX) XXXXX-XXXX">
						<div class="invalid-tooltip" id="msg_error_telefone"></div>
					</div>
				</div>
			</div>
		</div>
		<div class="py-5 text-center">
			<button class="btn btn-primary btn-lg" type="submit"
				id="btnCadastrar">Cadastrar</button>
		</div>
		<div class="modal" id="modal_informativo" data-backdrop="static"
			tabindex="-1" role="dialog">
			<div class="modal-dialog modal-dialog-centered" role="document">
				<div class="modal-content">
					<div class="modal-body" id="success_message"
						style="text-align: center;"></div>
				</div>
			</div>
		</div>
	</div>
	|

	<script src="./Bootstrap/js/jquery.min.js"></script>
	<script src="./Bootstrap/js/jquery.mask.min.js"></script>
	<script src="./Bootstrap/js/jquery.mask.js"></script>
	<script src="./Bootstrap/js/popper.min.js"></script>
	<script src="./Bootstrap/js/bootstrap.min.js"></script>

	<script type="text/javascript">
		$(document).ready(
				function() {
					
					$.get("cliente_genero?operacao=Lista",function(retorno) {
						console.log(retorno)
						retorno.forEach(function(genero){
							$("#txtSexo").append('<option value="' + genero.id + '">' + genero.descricao + '</option>')
						})
					});
					
					$("#txtSenha").keyup(function(){
						var senha = $(this).val();
						var regex = /^(?=(?:.*?[A-Z]){1})(?=(?:.*?[0-9]){1})(?=(?:.*?[!@#$%*()_+^&}{:;?.]){1})(?!.*\s)[0-9a-zA-Z!@#$%;*(){}_+^&]*$/;
						
						if(senha.length == 0){		
							$("#senha_color").css('background-color',"red");
							$("#txtSenhaValor").val(false);
						} else if(senha.length < 8){
							$("#senha_color").css('background-color',"orange");
							$("#txtSenhaValor").val(false);
						} else {	
							if(regex.exec(senha)){
								$("#senha_color").css('background-color',"green");
								$("#txtSenhaValor").val(true);
							} else{
								$("#senha_color").css('background-color',"orange");
								$("#txtSenhaValor").val(false);
							}
						}
					});
					$("#txtConfirmarPassword").keyup(function(){
						var opcao = false;
						var senha = $(this).val();
						var regex = /^(?=(?:.*?[A-Z]){1})(?=(?:.*?[0-9]){1})(?=(?:.*?[!@#$%*()_+^&}{:;?.]){1})(?!.*\s)[0-9a-zA-Z!@#$%;*(){}_+^&]*$/;
						
						if(senha.length == 0){		
							$("#senha_confirmar").css('background-color',"red");
						} else if(senha.length < 8){
							$("#senha_confirmar").css('background-color',"orange");
						} else {	
							if(regex.exec(senha)){
								$("#senha_confirmar").css('background-color',"green");
								opcao = true;
							}
						}
						
						if($("#txtSenha").val() != senha){
							$('#confirmarSenhaErr div').remove()
							$('#confirmarSenhaErr').append("<div>Senha divergente</div>")
							document.getElementById("txtConfirmarPassword").classList.add('is-invalid');
							$("#txtSenhaConfirmarValor").val(false)
						} else {
							document.getElementById("txtConfirmarPassword").classList.remove('is-invalid');
							if(opcao == true)
								$("#txtSenhaConfirmarValor").val(true)
						}
					});
					$("#btnCadastrar").click(function() {
						//Invalidar
						//document.getElementById("txtCPF").classList.remove('is-invalid');
						validacao();
					});
					// FUNÇÃO QUE VALIDA SE TODOS OS CAMPOS ESTÃO PREENCHIDOS
					function validacao() {
						var validador = true;
						
						var CPF = $("#txtCPF").val();
						if(CPF.length != 14){
							$('#msg_error_cpf div').remove()
							$('#msg_error_cpf').append("<div>Preencha um CPF Valido.</div>")
							document.getElementById("txtCPF").classList.add('is-invalid');
							validador = false;
						} else{
							document.getElementById("txtCPF").classList.remove('is-invalid');
						}
						
						var dt_nacismento = $("#txtData").val();
						if(dt_nacismento.trim() == ''){
							document.getElementById("txtData").classList.add('is-invalid');
							validador = false;
						} else{
							document.getElementById("txtData").classList.remove('is-invalid');
						}
						
						var nome = $("#txtNome").val();
						if(nome.trim() == ''){
							document.getElementById("txtNome").classList.add('is-invalid');
							validador = false;
						} else{
							document.getElementById("txtNome").classList.remove('is-invalid');
						}
						
						var email = $("#txtEmail").val();
						if(email.trim() == ''){
							$('#msg_error_email div').remove()
							$('#msg_error_email').append("<div>Preencha um E-mail Valido.</div>")
							document.getElementById("txtEmail").classList.add('is-invalid');
							validador = false;
						} else{
							document.getElementById("txtEmail").classList.remove('is-invalid');
						}	
						
						if($("#txtSexo").val() == null){
							document.getElementById("txtSexo").classList.add('is-invalid');
							validador = false;
						} else{
							document.getElementById("txtSexo").classList.remove('is-invalid');
						}

						var senha = $("#txtSenha").val();
						if(senha == ''){
							document.getElementById("txtSenha").classList.add('is-invalid');
							validador = false;
						} else{
							document.getElementById("txtSenha").classList.remove('is-invalid');
						}
						
						var senhaConfirmar = $("#txtConfirmarPassword").val();
						if(senhaConfirmar == ''){
							$('#confirmarSenhaErr div').remove()
							$('#confirmarSenhaErr').append("<div>Preencha uma senha Valida.</div>")
							document.getElementById("txtConfirmarPassword").classList.add('is-invalid');
							validador = false;
						} else{
							document.getElementById("txtConfirmarPassword").classList.remove('is-invalid');
						}
						
						/*var contato = $("#txtContato").val();
						if(contato == ''){
							$('#msg_error_contato div').remove()
							$('#msg_error_contato').append("<div>Preencha um contato Valido.</div>")
							document.getElementById("txtContato").classList.add('is-invalid');
							validador = false;
						} else{
							document.getElementById("txtContato").classList.remove('is-invalid');
						}*/
						
						var telefone = $("#txtTelefone").val();
						console.log(telefone.length != 15)
						if(telefone == '' || telefone.length < 14){
							$('#msg_error_telefone div').remove()
							$('#msg_error_telefone').append("<div>Preencha um telefone Valido.</div>")
							document.getElementById("txtTelefone").classList.add('is-invalid');
							validador = false;
						} else{
							document.getElementById("txtTelefone").classList.remove('is-invalid');
						}
						
						if(validador == true){
							var senha = $("#txtSenhaValor").val();
							var senhaConfirmacao = $("#txtSenhaConfirmarValor").val()
							if (senha == "true" && senhaConfirmacao == "true"){
								var data = {
										txtCPF: CPF,
										txtData: dt_nacismento,
										txtEmail: email,
										txtNome: nome,
										txtSexo: $("#txtSexo").val(),
										txtPassword: $("#txtSenha").val(),
										//txtContato: contato,
										txtTelefone: telefone
									};
								$.ajax({
								    type: "POST",
								    url: "cliente?operacao=Salvar",
								    contentType: "application/json", // NOT dataType!
								    data: JSON.stringify(data),
								    success: function(retorno) {
								    	
								    	if(retorno.includes("E-mail")){
								    		$('#msg_error_email div').remove()
											$('#msg_error_email').append("<div>" + retorno + "</div>")
											document.getElementById("txtEmail").classList.add('is-invalid');
								    	} else if(retorno.includes("CPF")){
								    		$('#msg_error_cpf div').remove()
											$('#msg_error_cpf').append("<div>" + retorno + "</div>")
											document.getElementById("txtCPF").classList.add('is-invalid');
								    	} else {
								    		$('#modal_informativo').modal('show')
											$('#success_message').append(
													"Cadastro Realizado com Sucesso");
											
											setTimeout(function() {
												window.location.href = "index.jsp";
											}, 500);
								    	}								    		
								    }
								});
							}
							else	
								alert("SENHAS ESTÃO COM ERRO")
								
						}
					};
					// MASCARA DE CPF PARA O CLIENTE
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