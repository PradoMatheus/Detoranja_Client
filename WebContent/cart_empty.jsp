<html>
<head>
<title>Carrinho</title>
<meta charset="UTF-8">
<%@ include file="complements/complements_css.jsp"%>
<link type="text/css" rel="stylesheet" href="css/cart.css">
</head>
<body>

	<%@ include file="complements/navbar.jsp"%>

	<div class="cart_section">
		<div class="container-fluid">
			<div class="row">
				<div class="col-lg-10 offset-lg-1">
					<div class="cart_container">
						<div class="cart_title">
							Carrinho<small> (0 item no carrinho) </small>
						</div>
						<div class="order_total">
							<div class="order_total_content text-md-right">
								<div class="order_total_title">Valor Total:</div>
								<div class="order_total_amount">R$00,00</div>
							</div>
						</div>
						<div class="cart_buttons">
							<a href="login_usuario.jsp"><button type=	"button" class="button cart_button_clear">Login/Criar Conta</button></a>
							<button type="button" class="button cart_button_checkout" disabled>
								Finalizar
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<%@ include file="complements/complements_js.jsp"%>

</body>

<%@ include file="complements/footer.jsp"%>

</html>