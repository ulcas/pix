package com.lucascarvalho.api.itau.pix.validate.regra;

import br.com.itau.pix.core.TipoDeChavePix;
import com.lucascarvalho.api.itau.pix.model.ChaveModel;

import java.util.ArrayList;
import java.util.List;

public class RegraTipoChave extends RegraAbstract<String> {
    public final static String ERRO_VALOR_INVALIDO = "O tipo de chave pix deve possuir um valor valido";
    public final static String ERRO_VALOR_DUPLICADO = "Chave já existe";

    public RegraTipoChave(String stringValidar) {
        super(stringValidar);
    }

    @Override
    protected boolean validarRegra(String tipoChavePix) {
        if(!TipoDeChavePix.getByStringDeTipoDeChave(tipoChavePix).isPresent()) {
            this.registraErro(ERRO_VALOR_INVALIDO);
        }
        return TipoDeChavePix.getByStringDeTipoDeChave(tipoChavePix).isPresent();
    }
}
