package com.lucascarvalho.api.itau.pix.validate.regra;

import br.com.itau.pix.core.TipoDeChavePix;

public class RegraTipoConta extends RegraAbstract<String> {

    public final static String ERRO_VALOR_INVALIDO = "O tipo de conta é inválido. Deve ser: corrente|poupança";

    public RegraTipoConta(String stringValidar) {
        super(stringValidar);
    }

    @Override
    protected boolean validarRegra(String tipoConta) {

        switch (tipoConta) {
            case "corrente":
            case "poupança":
                return true;

            default:
                this.registraErro(ERRO_VALOR_INVALIDO);
                return false;
        }
    }
}
