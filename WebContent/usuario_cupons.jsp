<%@page import="java.time.LocalDate"%>
<%@page import="java.util.Date"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="br.edu.fatec.detoranja.dominio.Cupom"%>
<%@page import="java.util.List"%>
<html>
	<head>
		<title>Meus Cupons</title>
		<meta charset="UTF-8">
	<%@ include file="complements/complements_css.jsp"%>
	</head>
	<body style="background-color: #EAE6E5">
	
		<%@ include file="complements/navbar.jsp"%>
		
		<%
		List<Cupom> listaCupons = (List<Cupom>) request
				.getAttribute("listaCupons");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		%>
		
		<br>
		<div class='container' style="background-color: #FFFFFF;height: 30em;border-radius: 1em;">
			<div class="py-3 text-center">
				<h2>Lista de Cupons</h2>
			</div>
			<div style="height: 320px">
				<table class="table table-bordered table-sm" id="datatablesSimple">
					<thead>
						<tr>
							<th scope="col" style="width: 30%; text-align: center;border:1px solid black;">Cupom</th>
							<th scope="col" style="width: 15%;border:1px solid black;">Valor Minimo</th>
							<th scope="col" style="width: 15%; border: 1px solid black;">Desconto</th>
							<th scope="col" style="width: 15%; text-align: center;border:1px solid black;">Validade</th>
							<th scope="col" style="width: 15%; text-align: center;border:1px solid black;">Tipo</th>
							<th scope="col" style="width: 10%; text-align: center;border:1px solid black;">Status</th>																					
						</tr>
					</thead>
					<tbody>
					<%
					if (listaCupons != null && listaCupons.size() > 0) {
						int i = 1;
						for (Cupom cupom : listaCupons) {
								out.print("<th scope='row' style='vertical-align: middle;text-align: center'>" + cupom.getDesc_cupom() + "</th>");
								
								if(cupom.getValor_minimo() == 0)
									out.print("<td style='vertical-align: middle'>S/ VALOR MINIMO</td>");
								else
									out.print("<td style='vertical-align: middle'>" + cupom.getValor_minimo() + "</td>");
								
								out.print("<td style='vertical-align: middle;'>" + cupom.getDesconto() + "</td>");
								
								if(cupom.getValidade() == null)
									out.print("<td style='vertical-align: middle;text-align: center''>ILIMITADA</td>");
								else
									out.print("<td style='vertical-align: middle;text-align: center''>" + cupom.getValidade().format(formatter) + "</td>");
								
								if(cupom.getCliente().getId() != 0)
									out.print("<td style='vertical-align: middle;'>CLIENTE</td>");
								else
									out.print("<td style='vertical-align: middle;'>PROMOCIONAL</td>");
								
								if(Integer.parseInt(cupom.getDisponibilidade()) == 0)
									out.print("<td style='vertical-align: middle;text-align: center''>ATIVO</td>");
								else
									out.print("<td style='vertical-align: middle;text-align: center''>JÁ UTILIZADO</td>");
								out.print("</tr>");
						}
					} else {
						out.print("<tr>");
						out.print("<td style='text-align: center' colspan='5'>Sem Cupons Disponiveis !</td>");
						out.print("</tr>");
					}
					%>
					</tbody>
				</table>
			</div>
	</div>

		<%@ include file="complements/complements_js.jsp"%>
		
		<script type="text/javascript">
		$(document).ready(function() {
			
			$('#datatablesSimple').DataTable({
				"language" : {
					"url" : "//cdn.datatables.net/plug-ins/1.11.1/i18n/pt_br.json"
					}
			});
			
		});
		</script>

	</body>
	
	<%@ include file="complements/footer.jsp"%>
</html>
