<%@page import="java.text.DecimalFormat"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="br.edu.fatec.detoranja.dominio.Produto_Imagens"%>
<%@page import="br.edu.fatec.detoranja.dominio.Produto"%>
<html>
<head>
<%
	Produto produto = (Produto) request.getAttribute("produto");
	DecimalFormat df = new DecimalFormat("#,###.00");
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	System.out.print(produto);
	%>
<title>Livro - <% out.print(produto.getTitulo()); %></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="complements/complements_css.jsp"%>
<link type="text/css" rel="stylesheet" href="css/product_details.css">
</head>
<body>

	<%@ include file="complements/navbar.jsp"%>

	<div class="container mt-5 mb-5">
		<div class="card">
			<div class="row g-0">
				<div class="col-md-6 border-end">
					<div class="d-flex flex-column justify-content-center">
						<div class="main_image">
							<img src="<% out.print(produto.getImagens().get(0).getPath()); %>"
								id="main_product_image" width="350">
						</div>
						<div class="thumbnail_images">
							<ul id="thumbnail">
								<% for(Produto_Imagens imagem : produto.getImagens()) { %>
								<li><img onclick="changeImage(this)"
									src="<% out.print(imagem.getPath()); %>"
									width="70"></li>
								<% } %>
							</ul>
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="p-3 right-side">
						<div class="d-flex justify-content-between align-items-center">
							<h3><% out.print(produto.getTitulo()); %></h3>
							<span class="heart"><i class="fa fa-heart"></i></span>
						</div>
						<div class="mt-2 pr-3 content">
							<p><% out.print(produto.getSinopse()); %></p>
						</div>
						<h3>R$ <% out.print(df.format(produto.getValor() * ((produto.getPrecificacao().getLucro() / 100) + 1))); %></h3>
						<div class="ratings d-flex flex-row align-items-center">
							<div class="d-flex flex-row">
								<i class='fa fa-star'></i> <i class='fa fa-star'></i> <i
									class='fa fa-star'></i> <i class='fa fa-star'></i> <i
									class='fa fa-star'></i>
							</div>
							<span>0 Reviews</span>
						</div>
						<div class="mt-4">
							<strong>Editora: </strong><% out.print(produto.getEditora().getDescricao()); %>
						</div>
						<div class="mt-2">
							<strong>Data de Publicacao: </strong><% out.print(produto.getData_lancamento().format(formatter)); %>
						</div>
						<div class="mt-2">
							<strong>Edicao: </strong><% out.print(produto.getEdicao()); %>
						</div>
						<div class="mt-2">
							<strong>Idioma: </strong><% out.print(produto.getIdioma().getDescricao()); %>
						</div>
						<div class="mt-2">
							<strong>Páginas: </strong><% out.print(produto.getNumero_paginas()); %>
						</div>
						<div class="mt-2">
							<strong>Dimensao: </strong>‎ <% out.print(produto.getDimensao().getAltura() + " x " + produto.getDimensao().getLargura() + " x " + produto.getDimensao().getProfundidade()); %>
													</div>
						<div class="buttons d-flex flex-row mt-4 gap-3">
							<button class="btn btn-outline-dark">Comprar Agora</button>
							<button class="btn btn-dark" id="btnAdd">
								<i class="fa fa-cart-plus mr-2"></i>Adicionar ao Carrinho
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<%@ include file="complements/complements_js.jsp"%>

	<script type="text/javascript">
		function changeImage(element) {

			var main_prodcut_image = document
					.getElementById('main_product_image');
			main_prodcut_image.src = element.src;

		}
		
		$(document).ready(function() {	
			$("#btnAdd").click(function() {
				<%if(request.getSession().getAttribute("ClientUser") != null) {%>
					$.ajax({
						type : "POST",
						url : "carrinho_itens?operacao=Salvar",
						contentType : "application/json", // NOT dataType!
						data : JSON.stringify({
							id_produto : <% out.print(produto.getId()); %>,
							quantidade : 1
						}),
						success : function(retorno) {
							console.log(retorno)
							if(retorno == true){
								toastr.success('Livro <% out.print(produto.getTitulo()); %> adicionado ao Carrinho !')
								setTimeout(function() {
									window.location.href = "./carrinho?operacao=Buscar&s=cart";
								}, 500);
							} else {
								toastr.error('Livro já adicionado ao Carrinho !!')
								
							}
						},
						error : function(data){
							console.log(data)
						}
					});
				<%} else {%>
					toastr.info('Para adicionar um item ao carrinho realize o Login !!')
				<% } %>
			});
		});
	</script>

</body>

<%@ include file="complements/footer.jsp"%>

</html>