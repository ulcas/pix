package com.lucascarvalho.api.itau.pix.validate.regra;

import br.com.caelum.stella.ValidationMessage;
import br.com.caelum.stella.validation.CPFValidator;

import java.util.List;

public class RegraCpf extends RegraAbstract<String> {

    public final static String ERRO_FORMATO_INVALIDO = "CPF Inválido";
    public final static String ERRO_APENAS_NUMEROS = "CPF: Somente números são aceitos";

    public RegraCpf(String stringValidar) {
        super(stringValidar);
    }

    @Override
    protected boolean validarRegra(String cpf) {
        CPFValidator cpfValidator = new CPFValidator();

        RegraApenasNumeros apenasNumero = new RegraApenasNumeros(cpf);

        if(cpf == null || !apenasNumero.valida()) {
            this.registraErro(ERRO_APENAS_NUMEROS);
            return false;
        }

        List<ValidationMessage> erros = cpfValidator.invalidMessagesFor(this.removeCaracteresEspeciais(cpf));
        if(erros.size() > 0){
            this.registraErro(ERRO_FORMATO_INVALIDO);
            return false;
        }else{
            return true;
        }
    }
}