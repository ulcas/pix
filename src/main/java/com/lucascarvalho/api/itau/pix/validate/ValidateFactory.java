package com.lucascarvalho.api.itau.pix.validate;

import com.lucascarvalho.api.itau.pix.TipoChaveEnum;
import com.lucascarvalho.api.itau.pix.repository.ChaveRepository;
import com.lucascarvalho.api.itau.pix.validate.regra.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
public class ValidateFactory {

    @Autowired
    private ChaveRepository chaveRepository;
    public static Optional<Regra> getInstance(String tipoDeChave, String valor) {
        Optional<TipoChaveEnum> tipo = TipoChaveEnum.getByStringDeTipoChave(tipoDeChave);

        if (!tipo.isPresent()) {
            return Optional.empty();
        }

        switch (tipo.get()) {
            case CELULAR:
                return Optional.of(new RegraCelular(valor));
            case CNPJ:
                return Optional.of(new RegraCnpj(valor));
            case CPF:
                return Optional.of(new RegraCpf(valor));
            case EMAIL:
                return Optional.of(new RegraEmail(valor));
            case ALEATORIO:
                return Optional.of(new RegraAleatorio(valor));
            default:
                return Optional.empty();
        }
    }
}
