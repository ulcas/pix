package com.lucascarvalho.api.itau.pix.validate.regra;
public class RegraNumeroAgencia extends RegraAbstract<String> {

    public final static String ERRO_TAMANHO_INVALIDO = "A agencia deve possuir no máximo 4 digitos";
    public final static String ERRO_FORMATO_INVALIDO = "A agencia deve possuir apenas caracteres numericos";


    public RegraNumeroAgencia(String stringValidar) {
        super(stringValidar);
    }

    @Override
    protected boolean validarRegra(String conta) {

        RegraApenasNumeros apenasNumero = new RegraApenasNumeros(conta);

        if(conta == null || !apenasNumero.valida()) {
            this.registraErro(ERRO_FORMATO_INVALIDO);
        }

        if(conta.length() > 4) {
            this.registraErro(ERRO_TAMANHO_INVALIDO);
        }

        return this.getErros().size() == 0;
    }
}
