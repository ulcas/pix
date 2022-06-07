package com.lucascarvalho.api.itau.pix.validate.regra;

public class RegraCelular extends RegraAbstract<String> {

    public final static String ERRO_FORMATO_INVALIDO = "O celular deve iniciar com \"+\" seguido de codigo do pais, DDD e numero de 9 digitos, ex: +5511999999999";

    public RegraCelular(String stringValidar) {
        super(stringValidar);
    }

    @Override
    protected boolean validarRegra(String celular) {
        if(!celular.matches("^\\+\\d{2,3}\\d{2}\\d{9}$")) {
            this.registraErro(ERRO_FORMATO_INVALIDO);
            return false;
        }
        return true;
    }
}
