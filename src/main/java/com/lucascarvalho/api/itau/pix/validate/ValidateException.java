package com.lucascarvalho.api.itau.pix.validate;

import org.springframework.http.HttpStatus;

import java.util.List;

public class ValidateException extends Exception {

    private static final long serialVersionUID = 1L;

    private List<String> erros;

    public ValidateException(List<String> erros, HttpStatus statusCode) {
        super(erros.toString());
        this.erros = erros;
    }

    public List<String> getErros() {
        return erros;
    }
}
