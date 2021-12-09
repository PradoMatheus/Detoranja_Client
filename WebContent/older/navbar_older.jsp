<%@page import="org.apache.jasper.tagplugins.jstl.core.Out"%>
<nav class="navbar navbar-dark bg-secondary fixed-top navbar-expand-lg">

	<a class="navbar-brand" href="index.jsp">
		<img src="./complements/logo_site.png" width="30" height="30" alt="">
		Detoranja
	</a>
	<button class="navbar-toggler" type="button" data-toggle="collapse"
		data-target="#conteudoNavbarSuportado"
		aria-controls="conteudoNavbarSuportado" aria-expanded="false"
		aria-label="Alterna navegação">
		<span class="navbar-toggler-icon"></span>
	</button>

	<div class="collapse navbar-collapse d-flex justify-content-end"
		id="conteudoNavbarSuportado">
		<ul class="navbar-nav">
			<li class="nav-item dropdown">
				<a class="nav-link dropdown-toggle"  href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"	> 
					<img src="./icons/game-controller.svg" alt="..." style="width: 25px; color: white;">
					Plataformas
				</a>
				<div class="dropdown-menu" aria-labelledby="navbarDropdown">
					<a class="dropdown-item" href="#">Playstation</a> 
				</div>
			</li>
			<li class="nav-item dropdown">
				<a class="nav-link dropdown-toggle"  href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"	> 
					<img src="./icons/document.svg" alt="..." style="width: 25px; color: white;">
					Minha Conta
				</a>
				<%
					// VALIDA SE O USUARIO ESTÁ LOGADO, CASO NÃO ESTEJA É DIRECIONA A TELA DE LOGIN
					if (session.getAttribute("ClientUser") == null){
						out.print("<div class='dropdown-menu' aria-labelledby='navbarDropdown'>");
						out.print("<a class='dropdown-item' href='login_usuario.jsp'>Entrar</a>");
						out.print("</div>");
					} else {
						out.print("<div class='dropdown-menu' aria-labelledby='navbarDropdown'>");
						out.print("<a class='dropdown-item' href='./cliente?operacao=Buscar'>Configurações</a>");
						out.print("<a class='dropdown-item' href='./endereco?operacao=Lista'>Endereços</a>");
						out.print("<a class='dropdown-item' href='./forma_pagamento?operacao=Lista'>Formas de Pagamentos</a>");
						out.print("<a class='dropdown-item' href='usuario_meus_pedidos.jsp'>Meus Pedidos</a>");
						out.print("<div class='dropdown-divider'></div>");
						out.print("<a class='dropdown-item btnsair' href='./cliente?operacao=Sair'>Sair</a>");
						out.print("</div>");
					}
				%>
			</li>
		</ul>
	
	<script src="./Bootstrap/js/jquery-3.3.1.slim.min.js"></script>
	
	<script type="text/javascript">
		$(".btnsair").click(function(event) {
			$('#modalSair').modal('show')
		})
	</script>
	
</nav>

<br><br>