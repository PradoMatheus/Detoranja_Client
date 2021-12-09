<%@page import="java.text.DecimalFormat"%>
<%@page import="br.edu.fatec.detoranja.dominio.Produto"%>
<%@page import="java.util.List"%>
<html>
<head>
<title>Livros</title>
<meta charset="UTF-8">
<%@ include file="complements/complements_css.jsp"%>
<link type="text/css" rel="stylesheet" href="css/products.css">
</head>
<body>

	<%@ include file="complements/navbar.jsp"%>
	
		<%
	List<Produto> listaProdutos = (List<Produto>) request.getAttribute("listaProdutos");
	DecimalFormat df = new DecimalFormat("#,###.00");
	%>

	<div
		class="d-flex align-items-center justify-content-end px-sm-3 pt-3 px-1">
		<div class="text-muted">
			Itens por página (<b><% out.print(listaProdutos.size()); %> Itens</b> )
		</div>
		<select name="num" id="num" class="px-2 py-1 ml-sm-2 ml-1">
			<option value="10">10</option>
			<option value="11">11</option>
			<option value="12">12</option>
			<option value="13">13</option>
			<option value="14">14</option>
			<option value="15">15</option>
		</select> <select name="sort" id="sort" class="px-1 py-1 ml-2">
			<option value="" selected hidden>Ordenar</option>
			<option value="rating">Ranking</option>
			<option value="popular">Popular</option>
		</select>
	</div>
	<div class="container d-flex justify-content-center mt-50 mb-50" style="margin-bottom: 15px">
		<div class="row">
			<%
			for (Produto produto : listaProdutos) {
			%>
			<div class="col-md-4 mt-2">
				<div class="card">
					<div class="card-body">
						<div class="card-img-actions" style="display: flex;justify-content: center;">
							<img
								src="<% out.print(produto.getImagens().get(0).getPath()); %>"
								class="your-img" alt="" style="position: relative;">
							<% if (produto.getQtde_estoque() <= 0) {%>
								<img
									src="https://rj.aguiamontagemdemoveis.com.br/wp-content/uploads/2021/02/indisponivel-3.png"
									class="your-img" alt="" style="position: absolute;">
							<% } %>
						</div>
					</div>
					<div class="card-body bg-light text-center">
						<div class="mb-2">
							<h6 class="font-weight-semibold mb-2">
								<a <% if (produto.getQtde_estoque() > 0) {%>href="./produto?operacao=Buscar&id=<% out.print(produto.getId()); %>"<% } %> class="text-default mb-2" data-abc="true"><% out.print(produto.getTitulo()); %></a>
							</h6>
							<a href="./produto?operacao=Lista&c=<% out.print(produto.getCategoria().getId()); %>" class="text-muted" data-abc="true"><% out.print(produto.getCategoria().getDescricao()); %></a>
						</div>
						<h3 class="mb-0 font-weight-semibold">R$ <% out.print(df.format(produto.getValor() * ((produto.getPrecificacao().getLucro() / 100) + 1))); %></h3>
						<div>
							<i class="fa fa-star star"></i> <i class="fa fa-star star"></i> <i
								class="fa fa-star star"></i> <i class="fa fa-star star"></i>
						</div>
						<div class="text-muted mb-3">0 reviews</div>
						<button class="btn bg-cart btnincluir" value="<%out.print(produto.getId());%>" <%if(produto.getQtde_estoque() <= 0) { out.print("disabled"); }%>>
							<i class="fa fa-cart-plus mr-2"></i> Adicionar ao Carrinho
						</button>
					</div>
				</div>
			</div>
			<%
			}
			%>
		</div>
	</div>

	<%@ include file="complements/complements_js.jsp"%>

</body>

<ul class="pagination justify-content-center">
    <li class="page-item disabled">
      <a class="page-link" href="#" tabindex="-1">Anterior</a>
    </li>
    <li class="page-item disabled"><a class="page-link" href="#" >1</a></li>
    <% for(int i = 2; i <= Math.round(listaProdutos.size() / 9 ); i++){
    	out.print(" <li class='page-item'><a class='page-link' href='#'> " + i + "</a></li>");
    }%>
    <%if(Math.round(listaProdutos.size() / 9 ) == 1) {%>
    <li class="page-item">
      <a class="page-link" href="#">Próximo</a>
    </li>
    <%} %>
  </ul>
  
  <script type="text/javascript">
  $(document).ready(function() {	
		$(".btnincluir").click(function() {
			console.log(<%out.print(request.getSession().getAttribute("ClientUser"));%>)
			if(<%out.print(request.getSession().getAttribute("ClientUser"));%> == null)
				toastr.info('Realize o login para poder adicionar produtos ao carrinho!!')
			else
				$.ajax({
					type : "POST",
					url : "carrinho_itens?operacao=Salvar",
					contentType : "application/json", // NOT dataType!
					data : JSON.stringify({
						id_produto : $(this).val().trim(),
						quantidade : 1
					}),
					success : function(retorno) {
						console.log(retorno)
						if(retorno == true){
							toastr.success('Livro adicionado ao Carrinho !')
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
		});
	});
  </script>

<%@ include file="complements/footer.jsp"%>

</html>