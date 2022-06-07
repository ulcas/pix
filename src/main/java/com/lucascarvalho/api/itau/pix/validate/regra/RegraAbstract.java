package com.lucascarvalho.api.itau.pix.validate.regra;

import java.util.ArrayList;
import java.util.List;

public abstract class RegraAbstract<T> implements Regra {

    private boolean permiteObjetoNull = false;
    private T objetoValidar;
    private List<String> erros = new ArrayList<String>();

    protected abstract boolean validarRegra(T objetoValidar);

    public RegraAbstract(T objetoValidar) {
        this.objetoValidar =  objetoValidar;
    }

    @Override
    public boolean valida() {
        if(objetoValidar == null) {
            return permiteObjetoNull();
        }

        return validarRegra(objetoValidar);
    }

    public void registraErro(String mensagemErro) {
        erros.add(mensagemErro);
    }

    public List<String> getErros() {
        return erros;
    }

    public boolean permiteObjetoNull() {
        return permiteObjetoNull;
    }

    public void setpermiteObjetoNull(boolean opcional) {
        this.permiteObjetoNull = opcional;
    }

    public String removeCaracteresEspeciais(String doc) {
        if (doc.contains(".")) {
            doc = doc.replace(".", "");
        }
        if (doc.contains("-")) {
            doc = doc.replace("-", "");
        }
        if (doc.contains("/")) {
            doc = doc.replace("/", "");
        }
        return doc;
    }
}
