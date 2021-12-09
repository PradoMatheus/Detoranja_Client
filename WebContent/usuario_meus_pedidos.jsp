<%@page import="java.text.DecimalFormat"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="br.edu.fatec.detoranja.dominio.Pedido"%>
<%@page import="java.util.List"%>
<html>
<head>
<title>Meus Pedidos</title>
<meta charset="UTF-8">
<%@ include file="complements/complements_css.jsp"%>
<link type="text/css" rel="stylesheet" href="css/meus_pedidos.css">
</head>
<body>

	<%@ include file="complements/navbar.jsp"%>

	<%
	List<Pedido> listaPedidos = (List<Pedido>) request.getAttribute("listaPedido");
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	DecimalFormat df = new DecimalFormat();
	df.applyPattern("#,##0.00");
	%>

	<div class="container">
		<div class="card-body">
			<table class="table table-borderedtable table-bordered"
				id="datatablesSimple">
				<thead>
					<tr>
						<th style='vertical-align: middle; text-align: center'>Pedido</th>
						<th style='vertical-align: middle; text-align: center'>Data</th>
						<th style='vertical-align: middle; text-align: center'>Status</th>
						<th style='vertical-align: middle; text-align: center'>Valor
							Total</th>
						<th style='vertical-align: middle; text-align: center'>Consultar</th>
					</tr>
				</thead>
				<tbody>
					<%
					for (Pedido pedido : listaPedidos) {
					%>
					<tr>
						<td style='vertical-align: middle; text-align: center'>
							<%
							out.print(pedido.getId());
							%>
						</td>
						<td style='vertical-align: middle; text-align: center'>
							<%
							out.print(pedido.getData_pedido().format(formatter));
							%>
						</td>
						<td style='vertical-align: middle; text-align: center'>
							<%
							out.print(pedido.getStatus().getDescricao());
							%>
						</td>
						<td style='vertical-align: middle; text-align: center'>
							R$<%
							out.print(df.format(pedido.getValorTotal()));
							%>
						</td>
						<th style='vertical-align: middle; text-align: center'><a href="./pedido?operacao=Buscar&id=<%out.print(pedido.getId());%>"><button 
								type="button" class="btn btn-success btnConsultar">
								<i class="fa fa-eye" style="margin-right: 5px"></i>Consultar
							</button></a></th>
					</tr>
					<%
					}
					%>
				</tbody>
			</table>
		</div>
	</div>


	<%@ include file="complements/complements_js.jsp"%>

	<script type="text/javascript">
		// CONFIGURAR A PROPRIEDADE TABLE
		$(document).ready(function() {
			
			$('#datatablesSimple').DataTable({
				"language" : {
					"url" : "//cdn.datatables.net/plug-ins/1.11.1/i18n/pt_br.json"
					}
			});
		})
	</script>
</body>

<%@ include file="complements/footer.jsp"%>
</html>
