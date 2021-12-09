<!-- Modal de Inclusão e Edicao de um endereço-->
<div class="modal fade" id="modalForma" role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered modal-md"
		role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="labelForma">Novo Cartão de Crédito</h5>
			</div>
			<form id="formCartao" action="#">
				<div class="modal-body">
					<div class="form-group">
						<input type="hidden" id="txtId" name="txtId" value="0">
						<div class="row">
							<div class="col-sm-12">
								<div class="form-group">
									<label for="txtNome">Nome Impresso no Cartão</label> <input
										class="form-control" id="txtNome" type="text"
										placeholder="Preencha com o nome do cartão"
										required="required">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-8">
								<div class="form-group">
									<label for="txtNumeroCartao">Número do Cartão</label>
									<div class="input-group">
										<input class="form-control" type="text"
											placeholder="0000 0000 0000 0000" id="txtNumeroCartao"
											required="required">
										<div class="input-group-append">
											<span class="input-group-text"> <i
												class="fa fa-credit-card"></i>
											</span>
										</div>
									</div>
								</div>
							</div>
							<div class="form-group col-sm-4">
								<label for="txtBandeira">Bandeira</label> <select
									class="form-control" id="txtBandeira" required="required">
									<option selected disabled value="0">Selecione</option>
									<option value="visa">Visa</option>
									<option value="mastercard">Mastercard</option>
									<option value="american_express">American Express</option>
									<option value="elo">Elo</option>
								</select>
							</div>
						</div>
						<div class="row">
							<div class="form-group col-sm-4">
								<label for="txtMesValidade">Mês</label> <select
									class="form-control" id="txtMesValidade" required="required">
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>
									<option value="5">5</option>
									<option value="6">6</option>
									<option value="7">7</option>
									<option value="8">8</option>
									<option value="9">9</option>
									<option value="10">10</option>
									<option value="11">11</option>
									<option value="12">12</option>
								</select>
							</div>
							<div class="form-group col-sm-4">
								<label for="txtAnoValidade">Ano</label> <select
									class="form-control" id="txtAnoValidade" required="required">
									<option value="2021">2021</option>
									<option value="2022">2022</option>
									<option value="2023">2023</option>
									<option value="2024">2024</option>
									<option value="2025">2025</option>
									<option value="2025">2026</option>
									<option value="2026">2027</option>
									<option value="2028">2028</option>
								</select>
							</div>
							<div class="col-sm-4">
								<div class="form-group">
									<label for="txtCvv">CVV</label> <input class="form-control"
										id="txtCvv" type="password" placeholder="123"
										required="required">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="form-group col-sm-1">
								<label for="txtPreferencia" class="col-form-label">Principal</label>
								<div class="checkbox">
									<input type="checkbox" id="txtPreferencia">
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Cancelar</button>
					<button type="submit" class="btn btn-primary" name="operacao"
						value="Salvar">Salvar</button>
				</div>
			</form>
		</div>
	</div>
</div>