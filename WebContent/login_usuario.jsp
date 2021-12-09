<html>
<head>
<title>Login Conta</title>
<meta charset="UTF-8">
<%@ include file="complements/complements_css.jsp"%>
</head>
<body>

	<%@ include file="complements/navbar.jsp"%>

	<main>
		<div class="container">
			<div class="row">
				<div class="col-sm-9 col-md-7 col-lg-5 mx-auto"
					style="margin-top: 8px; margin-bottom: 7px">
					<div class="card card-signin my-5">
						<div class="card-body">
							<h5 class="card-title text-center">Sign In</h5>
							<form class="form-signin" id="formLogin">
								<div class="form-label-group" style="margin: 10px">
									<input type="email" id="txtEmail" name="txtEmail"
										class="form-control" placeholder="E-mail" required autofocus>
								</div>
								<div class="form-label-group" style="margin: 10px">
									<input type="password" id="txtPassword" name="txtPassword"
										class="form-control" placeholder="Senha" required>
								</div>
								<h6 class="card-title text-center">
									<a href="#">Esqueceu a senha?</a> ou <a href="novo_usuario.jsp">Cadastrar</a>
								</h6>
								<button class="btn btn-lg btn-primary btn-block text-uppercase"
									type="submit">Login</button>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</main>

	<%@ include file="complements/complements_js.jsp"%>

	<script type="text/javascript">
		$(document).ready(function() {
			
			$("#formLogin").submit(function() {

				$.ajax({
					type : "POST",
					url : "cliente?operacao=Buscar",
					contentType : "application/json", // NOT dataType!
					data : JSON.stringify({
						txtEmail : $("#txtEmail").val(),
						txtPassword : $("#txtPassword").val()
					}),
					success : function(retorno) {
						console.log(retorno)
						if(retorno == true){
							toastr.success('Login Realizado com Sucesso!')
							setTimeout(function() {
								window.location.href = "index.jsp";
							}, 500);
						} else {
							toastr.error('E-mail ou senha Invalidos !!')
						}
					}
				})

				event.preventDefault();
			});
		});
	</script>
</body>

<%@ include file="complements/footer.jsp"%>
</html>