package com.lucascarvalho.api.itau.pix;

import java.util.Arrays;
import java.util.Optional;

public enum TipoChaveEnum {

    CELULAR("celular"),

    EMAIL("email"),

    CPF("cpf"),

    CNPJ("cnpj"),

    ALEATORIO("aleatorio");

    private String tipo;

    TipoChaveEnum(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public static Optional<TipoChaveEnum> getByStringDeTipoChave(String strTipoChave) {
        if (strTipoChave == null) {
            return Optional.empty();
        }

        return Arrays.stream(values())
                .filter(at -> at.getTipo().contentEquals(strTipoChave))
                .findFirst();
    }
}
