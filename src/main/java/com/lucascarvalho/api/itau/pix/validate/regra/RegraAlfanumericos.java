package com.lucascarvalho.api.itau.pix.validate.regra;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegraAlfanumericos extends RegraAbstract<String> {

    public final static String ERRO_FORMATO_INVALIDO = "São aceitos apenas caracteres alfanumericos!";

    public RegraAlfanumericos(String stringValidar) {
        super(stringValidar);
    }

    @Override
    protected boolean validarRegra(String str) {
        Matcher matcher = Pattern.compile("^[-\\w' &&[^_]]+$", Pattern.UNICODE_CHARACTER_CLASS).matcher(str);
        if(!matcher.find()) {
            this.registraErro(ERRO_FORMATO_INVALIDO);
            return false;
        }
        return true;
    }
}
