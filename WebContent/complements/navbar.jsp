
<header class="section-header">
	<section class="header-main border-bottom">
		<div class="container-fluid">
			<div class="row align-items-center">
				<div class="col-lg-3 col-sm-4 col-md-4 col-5">
					<a href="index.jsp" class="brand-wrap" data-abc="true"> <img
						class="image" src="./complements/logo_site.png"> <span
						class="logo">DETORANJA</span>
					</a>
				</div>
				<div class="col-lg-4 col-xl-5 col-sm-8 col-md-4 d-none d-md-block">
					<div class="input-group w-100 d-flex justify-content-end">
						<input type="text" class="form-control search-form"
							style="width: 55%;" placeholder="Pesquisar">
						<div class="input-group-append">
							<button class="btn btn-primary search-button" type="submit">
								<i class="fa fa-search"></i>
							</button>
						</div>
					</div>
				</div>
				<div class="col-lg-2 col-xl-4 col-sm-4 col-md-4 col-7">
					<div class="d-flex justify-content-end">
						<a class="login" href="login_usuario.jsp" data-toggle="dropdown"
							style="margin-right: 5%"><i class="fa fa-user"
							aria-hidden="true"></i></a>
						<div class="dropdown-menu">
							<%// VALIDA SE O USUARIO ESTÁ LOGADO, CASO NÃO ESTEJA É DIRECIONA A TELA DE LOGIN
							if (session.getAttribute("ClientUser") == null){ %>
							<a class="dropdown-item" href="login_usuario.jsp" data-abc="true">Login</a>
							<%} else { %>
							<a class="dropdown-item" href="./cliente?operacao=Buscar" data-abc="true">Meus Dados</a>
							<a class="dropdown-item" href="./endereco?operacao=Lista&s=java" data-abc="true">Endereços</a>
							<a class="dropdown-item" href="./forma_pagamento?operacao=Lista&s=java" data-abc="true">Cartões</a>
							<a class="dropdown-item" href="./cupom?operacao=Lista" data-abc="true">Cupons</a>	
							<a class="dropdown-item" href="./pedido?operacao=Lista" data-abc="true">Meus Pedidos</a>
							<a class="dropdown-item" href="./troca?operacao=Lista" data-abc="true">Minhas Trocas</a>
							<div class="dropdown-divider"></div>
							<a class="dropdown-item" href="./cliente?operacao=Sair" data-abc="true">Sair</a>
							<%} %>
						</div>
						<a class="login" href="./carrinho?operacao=Buscar&s=cart" style="margin-right: 10%"><i
							class="fa fa-shopping-cart" aria-hidden="true"
							style="margin-right: 1px"></i><span
							class="badge badge-pill badge-primary" id="txtQtdeCarrinho">0</span> </a>
					</div>
				</div>
			</div>
		</div>
	</section>
	<nav class="navbar navbar-expand-md navbar-main border-bottom">
		<div class="container-fluid">
			<form class="d-md-none my-2">
				<div class="input-group">
					<input type="search" name="search" class="form-control"
						placeholder="Search">
					<div class="input-group-append">
						<button type="submit" class="btn btn-secondary">
							<i class="fa fa-search"></i>
						</button>
					</div>
				</div>
			</form>
			<button class="navbar-toggler collapsed" type="button"
				data-toggle="collapse" data-target="#dropdown6"
				aria-expanded="false">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="navbar-collapse collapse" id="dropdown6" style="">
				<ul class="navbar-nav mr-auto">
					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" href="" data-toggle="dropdown"
						data-abc="true" aria-expanded="false">Categorias</a>
						<div class="dropdown-menu">
							<a class="dropdown-item" href="./produto?operacao=Lista&c=2" data-abc="true">Drama</a>
							<a class="dropdown-item" href="./produto?operacao=Lista&c=1" data-abc="true">Ficção</a>
							<a class="dropdown-item" href="./produto?operacao=Lista&c=3" data-abc="true">Romance</a>
							<a class="dropdown-item" href="./produto?operacao=Lista&c=4" data-abc="true">Infantil</a>
							<a class="dropdown-item" href="./produto?operacao=Lista&c=5" data-abc="true">História</a>
						</div></li>
					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" href="" data-toggle="dropdown"
						data-abc="true" aria-expanded="false">Idiomas</a>
						<div class="dropdown-menu">
							<a class="dropdown-item" href="./produto?operacao=Lista&i=1" data-abc="true">Português</a>
							<a class="dropdown-item" href="./produto?operacao=Lista&i=2" data-abc="true">Inglês</a>
						</div></li>
				</ul>
			</div>
		</div>
	</nav>
</header>
