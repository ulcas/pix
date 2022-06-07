package com.lucascarvalho.api.itau.pix.validate.regra;

import java.util.List;

public interface Regra {
    public boolean valida();
    public List<String> getErros();
}
