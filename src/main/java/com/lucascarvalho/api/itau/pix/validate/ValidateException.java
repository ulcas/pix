package com.lucascarvalho.api.itau.pix.validate;

import java.util.List;

public class ValidateException extends Exception {

    private static final long serialVersionUID = 1L;

    private List<String> erros;

    public ValidateException(List<String> erros) {
        super(erros.toString());
        this.erros = erros;
    }

    public List<String> getErros() {
        return erros;
    }
}
