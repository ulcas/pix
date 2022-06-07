package com.lucascarvalho.api.itau.pix.validate;

import com.lucascarvalho.api.itau.pix.validate.regra.Regra;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class ValidateAbstract {
    protected List<String> erros = new ArrayList<>();

    public List<String> getErros() {
        return erros;
    }

    public boolean executa() {
        boolean validacao = true;
        for (Regra regra : configuraRegras()) {
            if(!regra.valida()) {
                validacao = false;
                this.erros.addAll(regra.getErros());
            }
        }
        return validacao;
    }

    public abstract List<Regra> configuraRegras();
}
