package com.lucascarvalho.api.itau.pix.validate.regra;

import com.lucascarvalho.api.itau.pix.TipoChaveEnum;

public class RegraTipoChave extends RegraAbstract<String> {
    public final static String ERRO_VALOR_INVALIDO = "O tipo de chave pix deve possuir um valor valido";
    public final static String ERRO_VALOR_DUPLICADO = "Chave já existe";

    public RegraTipoChave(String stringValidar) {
        super(stringValidar);
    }

    @Override
    protected boolean validarRegra(String tipoChavePix) {
        if(!TipoChaveEnum.getByStringDeTipoChave(tipoChavePix).isPresent()) {
            this.registraErro(ERRO_VALOR_INVALIDO);
        }
        return TipoChaveEnum.getByStringDeTipoChave(tipoChavePix).isPresent();
    }
}
