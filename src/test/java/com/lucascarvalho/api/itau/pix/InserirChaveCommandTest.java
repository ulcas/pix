package com.lucascarvalho.api.itau.pix;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import com.lucascarvalho.api.itau.pix.command.InserirChaveCommand;
import com.lucascarvalho.api.itau.pix.validate.ValidateException;
import com.lucascarvalho.api.itau.pix.validate.regra.RegraCelular;
import com.lucascarvalho.api.itau.pix.validate.regra.RegraNumeroConta;
import com.lucascarvalho.api.itau.pix.validate.regra.RegraTipoChave;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class InserirChaveCommandTest {
	@Test
	@DisplayName("Inserir chave pix com sucesso")
	public void deveInserirChavePixComSUcesso() {
		InserirChaveCommand commandPix = new InserirChaveCommand(
			"celular",
				"+5511986587878",
				"corrente",
				"1234",
				"12345678",
				"Teste",
				null,
				"J"
		);

		Assertions.assertDoesNotThrow(criaExecutable(commandPix));
	}

	@Test
	@DisplayName("Deve dar erro pois o celular é invalido")
	public void deveDarErroNaInsercaoCelularFormatoInvalido() {
		InserirChaveCommand commandPix = new InserirChaveCommand(
			"celular",
			"5511986587878",
			null,
			null,
			"12345678",
			null,
			null,
			null
		);
		
		ValidateException e = Assertions.assertThrows(ValidateException.class, criaExecutable(commandPix));
		List<String> errosNaValidacao = e.getErros();
		assertEquals(1, errosNaValidacao.size());
		assertEquals(RegraCelular.ERRO_FORMATO_INVALIDO, errosNaValidacao.get(0));
	}

	@Test
	@DisplayName("Deve dar erro pois o numero da conta tem mais de 8 caracteres")
	public void deveDarErroNaInsercaoContaFormatoInvalido() {
		InserirChaveCommand commandPix = new InserirChaveCommand(
			"celular",
				"+5511986587878",
				null,
				null,
				"123456789",
				null,
				null,
				null
		);

		ValidateException e = Assertions.assertThrows(ValidateException.class, criaExecutable(commandPix));
		List<String> errosNaValidacao = e.getErros();
		assertEquals(1, errosNaValidacao.size());
		assertEquals(RegraNumeroConta.ERRO_TAMANHO_INVALIDO, errosNaValidacao.get(0));
	}

	@Test
	@DisplayName("Deve dar erro no tipo de chave e numero de conta")
	public void deveDarErroNaInsercaoDoisDadosInvalidos() {
		InserirChaveCommand commandPix = new InserirChaveCommand(
			"rg",
				"+5511986587878",
				null,
				null,
				"123456789",
				null,
				null,
				null
		);

		ValidateException e = Assertions.assertThrows(ValidateException.class, criaExecutable(commandPix));
		List<String> errosNaValidacao = e.getErros();
		assertEquals(2, errosNaValidacao.size());
		assertEquals(RegraTipoChave.ERRO_VALOR_INVALIDO, errosNaValidacao.get(0));
		assertEquals(RegraNumeroConta.ERRO_TAMANHO_INVALIDO, errosNaValidacao.get(1));
	}
	
	private Executable criaExecutable(InserirChaveCommand commandPix) {
		return new Executable() {
			@Override
			public void execute() throws Throwable {
				commandPix.executa();
			}
		};
	}
}
