<!-- Modal de Inclusão e Edicao de um endereço-->
<div class="modal fade" id="modalForma" role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered modal-md"
		role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="labelForma">Nova Forma de Pagamento</h5>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<div class="row">
						<div class="col-md-2 mb-3">
							<label for="txtId" class="col-form-label">ID</label> <input
								type="text" class="form-control" id="txtId" name="txtId"
								readonly>
						</div>
					</div>
					<div class="row">
						<div class="col-md-8 mb-3">
							<label for="txtNumeroCartao" class="col-form-label">Número
								do Cartão</label> <input type="text" class="form-control"
								id="txtNumeroCartao" name="txtNumeroCartao" required="required">
						</div>
					</div>
					<div class="row">
						<div class="col-md-6 mb-3">
							<label for="txtBandeira" class="col-form-label">Bandeira</label>
							<input type="text" class="form-control" id="txtBandeira"
								name="txtBandeira" required="required">
						</div>
						<div class="col-md-1 mb-3"></div>
						<div class="col-md-5 mb-3">
							<label for="txtValidade" class="col-form-label">Validade</label>
							<input type="text" class="form-control" id="txtValidade"
								name="txtValidade" required="required">
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
				<button type="submit" class="btn btn-primary" name="operacao"
					value="Salvar" id="btnSalvar">Salvar</button>
			</div>
		</div>
	</div>
</div>