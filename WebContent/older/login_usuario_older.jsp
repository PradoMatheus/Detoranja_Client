<html>
<head>
<title>Login Conta</title>
<meta charset="UTF-8">
<link rel="stylesheet" href="./Bootstrap/css/bootstrap.min.css">
<link rel="icon" href="./complements/logo_browser.png">
<%@ include file="../complements/complements_css.jsp"%>
</head>
<body>

	<%@ include file="../complements/navbar.jsp"%>

	<main>
		<div style="padding: 5rem"></div>
		<div class="container" style="width: 18rem">
			<div class="card text-center">
				<div class="card-body">
					<img class="mb-4" src="./complements/logo_site.png" alt=""
						width="72" height="72">
					<div class="form-group">
						<input type="text" class="form-control" id="txtEmail"
							name="txtEmail" placeholder="E-mail" required>
					</div>
					<div class="form-group">
						<input type="password" class="form-control" id="txtPassword"
							name="txtPassword" placeholder="Senha" required>
					</div>
					<button class="btn btn-sm btn-primary btn-block" type="submit"
						id="btnlogin">Login</button>
					<a class="btn btn-sm btn-light btn-block" href="novo_usuario.jsp">Criar
						Conta</a>
				</div>
			</div>
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
		<div
			class="alert alert-danger alert-dismissible fade show alertError alert_message"
			role="alert"
			style="margin-left: 20%; margin-right: 20%; margin-top: 1%; display: none">
			<button type="submit" class="close" id="btnFecharModal">
			    <span aria-hidden="true">&times;</span>
			</button>
		</div>
	</main>

	<%@ include file="../complements/complements_js.jsp"%>

</body>

<footer> </footer>

<script type="text/javascript">
	$(document).ready(function() {
		function validacao(email, senha) {
			console.log(senha)
				if(email.includes("@") && email.length > 0 && email.includes(".")){
					if(senha.length >= 8){
						return true;	
					}
					else{
						$('.alert_message p').remove()
						$('.alert_message').append(
								"<p>Digite uma <Strong>Senha</Strong> valido !!!</p>");
						$(".alert_message").show();
						return false;
					}
				}
				else{
					$('.alert_message p').remove()
					$('.alert_message').append(
							"<p>Digite um <Strong>E-mail</Strong> valido !!!</p>");
					$(".alert_message").show();
					return false;
				}
			}
		
		$("#btnFecharModal").click(function(){
			$(".alert_message").hide();
		});
		
		$("#btnlogin").click(function(){
			var data = {
				txtEmail : $("#txtEmail").val(),
				txtPassword : $("#txtPassword").val(),
			};
			
			if(validacao($("#txtEmail").val(),$("#txtPassword").val()))
				$.ajax({
					type : "POST",
					url : "cliente?operacao=Buscar",
					contentType : "application/json", // NOT dataType!
					data : JSON.stringify(data),
					success : function(retorno) {
	
						if (retorno) {
							$('#modal_informativo').modal('show')
							$('#success_message').append(
									"Login Realizado com Sucesso");
							
							setTimeout(function() {
								window.location.href = "index.jsp";
							}, 500);
						}
						else {
							$('.alert_message p').remove()
							$('.alert_message').append(
									"<p>Usuario ou senhas invalidos !!!</p>");
							$(".alert_message").show();
	
						}
					}
				});
		});
	});
</script>
</html>