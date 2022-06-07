package com.lucascarvalho.api.itau.pix.validate.regra;

public class RegraApenasNumeros extends RegraAbstract<String> {

    public final static String ERRO_FORMATO_INVALIDO = "São aceitos apenas caracteres numericos!";

    public RegraApenasNumeros(String stringValidar) {
        super(stringValidar);
    }

    @Override
    protected boolean validarRegra(String str) {
        if(!str.matches("^\\d{1,}$")) {
            this.registraErro(ERRO_FORMATO_INVALIDO);
            return false;
        }
        return true;
    }
}
