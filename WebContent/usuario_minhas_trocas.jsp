<%@page import="br.edu.fatec.detoranja.dominio.Troca"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="br.edu.fatec.detoranja.dominio.Pedido"%>
<%@page import="java.util.List"%>
<html>
<head>
<title>Minhas Trocas</title>
<meta charset="UTF-8">
<%@ include file="complements/complements_css.jsp"%>
<link type="text/css" rel="stylesheet" href="css/meus_pedidos.css">
</head>
<body>

	<%@ include file="complements/navbar.jsp"%>

	<%
		List<Troca> listaTroca = (List<Troca>) request.getAttribute("listaTrocas");
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
						<th style='vertical-align: middle; text-align: center;width: 10%;'>Troca</th>
						<th style='vertical-align: middle; text-align: center;width: 10%;'>Pedido</th>
						<th style='vertical-align: middle; text-align: center;width: 20%;'>Data</th>
						<th style='vertical-align: middle; text-align: center;width: 30%;'>Status</th>
						<th style='vertical-align: middle; text-align: center;width: 30%;'>Consultar</th>
					</tr>
				</thead>
				<tbody>
					<%
						for (Troca troca : listaTroca) {
					%>
					<tr>
						<td style='vertical-align: middle; text-align: center;width: 10%;'>
							<%
							out.print(troca.getId());
							%>
						</td>
						<td style='vertical-align: middle; text-align: center;width: 10%;'>
							<%
							out.print(troca.getPedido().getId());
							%>
						</td>
						<td style='vertical-align: middle; text-align: center;width: 20%;'>
							<%
							out.print(troca.getData().format(formatter));
							%>
						</td>
						<td style='vertical-align: middle; text-align: center;width: 30%;'>
							<%
							out.print(troca.getStatus().getDescricao());
							%>
						</td>
						<th style='vertical-align: middle; text-align: center;width: 30%;'><a href="./troca?operacao=Buscar&id=<%out.print(troca.getId());%>"><button 
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
