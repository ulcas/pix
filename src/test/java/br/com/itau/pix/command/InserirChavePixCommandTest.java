package br.com.itau.pix.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import br.com.itau.pix.command.inserirchave.InserirChavePixCommand;
import br.com.itau.pix.core.validacao.ValidacaoException;
import br.com.itau.pix.core.validacao.regra.RegraCelular;
import br.com.itau.pix.core.validacao.regra.RegraNumeroDaConta;
import br.com.itau.pix.core.validacao.regra.RegraTipoChavePix;

public class InserirChavePixCommandTest {
	@Test
	@DisplayName("Inserir chave pix com sucesso")
	public void deveInserirChavePixComSUcesso() {
		InserirChavePixCommand commandPix = new InserirChavePixCommand(
			"celular", "+5511986587878", null, null, "12345678",null, null
		);

		Assertions.assertDoesNotThrow(criaExecutable(commandPix));
	}

	@Test
	@DisplayName("Deve dar erro pois o celular é invalido")
	public void deveDarErroNaInsercaoCelularFormatoInvalido() {
		InserirChavePixCommand commandPix = new InserirChavePixCommand(
			"celular", "5511986587878", null, null, "12345678", null, null
		);
		
		ValidacaoException e = Assertions.assertThrows(ValidacaoException.class, criaExecutable(commandPix));
		List<String> errosNaValidacao = e.getErros();
		assertEquals(1, errosNaValidacao.size());
		assertEquals(RegraCelular.ERRO_FORMATO_INVALIDO, errosNaValidacao.get(0));
	}

	@Test
	@DisplayName("Deve dar erro pois o numero da conta tem mais de 8 caracteres")
	public void deveDarErroNaInsercaoContaFormatoInvalido() {
		InserirChavePixCommand commandPix = new InserirChavePixCommand(
			"celular", "+5511986587878", null, null, "123456789", null, null
		);

		ValidacaoException e = Assertions.assertThrows(ValidacaoException.class, criaExecutable(commandPix));
		List<String> errosNaValidacao = e.getErros();
		assertEquals(1, errosNaValidacao.size());
		assertEquals(RegraNumeroDaConta.ERRO_TAMANHO_INVALIDO, errosNaValidacao.get(0));
	}

	@Test
	@DisplayName("Deve dar erro no tipo de chave e numero de conta")
	public void deveDarErroNaInsercaoDoisDadosInvalidos() {
		InserirChavePixCommand commandPix = new InserirChavePixCommand(
			"rg", "+5511986587878", null, null, "123456789", null, null
		);

		ValidacaoException e = Assertions.assertThrows(ValidacaoException.class, criaExecutable(commandPix));
		List<String> errosNaValidacao = e.getErros();
		assertEquals(2, errosNaValidacao.size());
		assertEquals(RegraTipoChavePix.ERRO_VALOR_INVALIDO, errosNaValidacao.get(0));
		assertEquals(RegraNumeroDaConta.ERRO_TAMANHO_INVALIDO, errosNaValidacao.get(1));
	}
	
	private Executable criaExecutable(InserirChavePixCommand commandPix) {
		return new Executable() {
			@Override
			public void execute() throws Throwable {
				commandPix.executa();
			}
		};
	}
}
