package br.edu.fatec.detoranja.strategy;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import br.edu.fatec.detoranja.dominio.Cliente;
import br.edu.fatec.detoranja.dominio.IDominio;

public class ValidarCPF implements IStrategy {

	@Override
	public String processar(IDominio dominio) throws InvalidStateException {
		Cliente cliente = (Cliente) dominio;

		// String CPF = cliente.getCpf().replace(".", "").replace("-", "");
		String CPF = cliente.getCpf();

		CPFValidator cpfValidator = new CPFValidator();
		try {
			cpfValidator.assertValid(CPF);
			return null;
		} catch (InvalidStateException e) {
			e.printStackTrace();
			return "CPF Invalido !!";
		}
	};
}
