package com.lucascarvalho.api.itau.pix.validate.regra;

import java.util.Objects;

public class RegraTipoPessoa extends RegraAbstract<String> {

    public final static String ERRO_VALOR_INVALIDO = "O tipo de pessoa é inválido. Ex: tipo_pessoa = F";
    public final static String ERRO_TAMANHO_INVALIDO = "É aceito somente P=pessoa fisica ou J=pessoa juridica";

    public RegraTipoPessoa(String stringValidar) {
        super(stringValidar);
    }

    @Override
    protected boolean validarRegra(String tipoPessoa) {

        switch (tipoPessoa) {
            case "F":
            case "J":
            return true;
        }

        this.registraErro(ERRO_VALOR_INVALIDO);
        if(tipoPessoa.length() > 1) {
            this.registraErro(ERRO_TAMANHO_INVALIDO);
        }

        return false;
    }
}
