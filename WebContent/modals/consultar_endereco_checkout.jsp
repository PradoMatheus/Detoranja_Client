<!-- Modal de Inclusão e Edicao de um endereço-->
<div class="modal fade" id="modalEndereco" role="dialog"
	aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered modal-lg"
		role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="labelEndereco">Novo Endereço</h5>
			</div>
			<form id="formEndereco_body">
				<div class="modal-body">
					<div class="form-group">
						<input type="hidden" id="txtId_endereco" name="txtId_endereco" value="0">
						<div class="row">
							<div class="col-md-8 mb-3">
								<label for="txtDescricao" class="col-form-label">Descrição</label>
								<input type="text" class="form-control" id="txtDescricao"
									name="txtDescricao" required="required">
							</div>
							<div class="col-md-4 mb-3">
								<label for="txtCEP" class="col-form-label">CEP</label> <input
									type="text" class="form-control" id="txtCEP" name="txtCEP"
									required="required">
							</div>
						</div>
						<div class="row">
							<div class="col-md-3 mb-3">
								<label for="txtBairro" class="col-form-label">Bairro</label> <input
									type="text" class="form-control" id="txtBairro"
									name="txtBairro" required="required">
							</div>
							<div class="col-md-3 mb-3">
								<label for="txtCidade" class="col-form-label">Cidade</label> <input
									type="text" class="form-control" id="txtCidade"
									name="txtCidade" required="required">
							</div>
							<div class="col-md-3 mb-3">
								<label for="txtEstado" class="col-form-label">Estado</label> <input
									type="text" class="form-control" id="txtEstado"
									name="txtEstado" required="required">
							</div>
							<div class="col-md-3 mb-3">
								<label for="txtPais" class="col-form-label">Pais</label> <input
									type="text" class="form-control" id="txtPais" name="txtPais"
									required="required">
							</div>
						</div>
						<div class="row">
							<div class="col-md-2 mb-3">
								<label for="txtTipoLogradouro" class="col-form-label">Tipo
									de Log.</label> <select class="form-control" id="txtTipoLogradouro"
									name="txtTipoLogradouro" required>
									<option value="" selected disabled>Selecione</option>
								</select>
							</div>
							<div class="col-md-7 mb-3">
								<label for="txtLogradouro" class="col-form-label">Logradouro</label>
								<input type="text" class="form-control" id="txtLogradouro"
									name="txtLogradouro" required="required">
							</div>
							<div class="col-md-3 mb-3">
								<label for="txtNumero" class="col-form-label">Número</label> <input
									type="text" class="form-control" id="txtNumero"
									name="txtNumero" required="required">
							</div>
						</div>
						<div class="row">
							<div class="col-md-6 mb-3">
								<label for="txtComplemento" class="col-form-label">Complemento</label>
								<input type="text" class="form-control" id="txtComplemento"
									name="txtComplemento">
							</div>
							<div class="col-md-6 mb-3">
								<label for="txtReferencia" class="col-form-label">Referencia</label>
								<input type="text" class="form-control" id="txtReferencia"
									name="txtComplemento">
							</div>
						</div>
						<div class="row">
							<div class="form-group col-sm-8">
								<label for="txtSalvarEndereco" class="col-form-label">Salvar Endereço apos a compra ?</label>
							</div>
						</div>
						<div class="row">
							<div class="form-group col-sm-1">
								<div class="checkbox">
									<input type="checkbox" id="txtSalvarEndereco">
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Cancelar</button>
					<button type="submit" class="btn btn-primary" name="operacao"
						value="Salvar" id="btnSalvarEndereco">Salvar</button>
				</div>
			</form>
		</div>
	</div>
</div>