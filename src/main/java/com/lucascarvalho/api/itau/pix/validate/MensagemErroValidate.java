package com.lucascarvalho.api.itau.pix.validate;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MensagemErroValidate {

    public static List<String> mensagem(String msg) {
        List<String> errors = new ArrayList<>();
        errors.add(msg);

        return errors;
    }
}
