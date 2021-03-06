package com.lucascarvalho.api.itau.pix.validate.regra;

import br.com.caelum.stella.ValidationMessage;
import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;

import java.util.List;

public class RegraCnpj extends RegraAbstract<String> {

    public final static String ERRO_FORMATO_INVALIDO = "CNPJ Inválido";
    public final static String ERRO_APENAS_NUMEROS = "CNPJ: Somente números são aceitos";


    public RegraCnpj(String stringValidar) {
        super(stringValidar);
    }

    @Override
    protected boolean validarRegra(String cnjp) {

        RegraApenasNumeros apenasNumero = new RegraApenasNumeros(cnjp);

        if(cnjp == null || !apenasNumero.valida()) {
            this.registraErro(ERRO_APENAS_NUMEROS);
            return false;
        }

        CNPJValidator cnpjValidator = new CNPJValidator();
        List<ValidationMessage> erros = cnpjValidator.invalidMessagesFor(this.removeCaracteresEspeciais(cnjp));
        if(erros.size() > 0){
            this.registraErro(ERRO_FORMATO_INVALIDO);
            return false;
        }

        return true;
    }
}