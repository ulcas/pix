package com.lucascarvalho.api.itau.pix.validate.regra;
public class RegraAleatorio extends RegraAbstract<String> {

    public final static String ERRO_TAMANHO_INVALIDO = "A conta deve possuir no máximo 8 digitos";
    public final static String ERRO_FORMATO_INVALIDO = "A conta deve possuir apenas caracteres numericos";


    public RegraAleatorio(String stringValidar) {
        super(stringValidar);
    }

    @Override
    protected boolean validarRegra(String str) {

        RegraAlfanumericos apenasAlfanumericos = new RegraAlfanumericos(str);

        if(str == null || !apenasAlfanumericos.valida()) {
            this.registraErro(ERRO_FORMATO_INVALIDO);
        }

        if(str.length() > 36) {
            this.registraErro(ERRO_TAMANHO_INVALIDO);
        }

        return this.getErros().size() == 0;
    }
}
