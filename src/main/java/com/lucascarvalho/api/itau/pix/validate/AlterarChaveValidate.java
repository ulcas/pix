package com.lucascarvalho.api.itau.pix.validate;

import com.lucascarvalho.api.itau.pix.validate.regra.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AlterarChaveValidate extends ValidateAbstract {

    private String tipoConta;
    private String numeroAgencia;
    private String numeroConta;
    private String nomeCorrentista;
    private String sobrenomeCorrentista;

    public AlterarChaveValidate(String tipoConta,
                                String numeroAgencia,
                                String numeroConta,
                                String nomeCorrentista,
                                String sobrenomeCorrentista) {

        this.tipoConta = tipoConta;
        this.numeroAgencia = numeroAgencia;
        this.numeroConta = numeroConta;
        this.nomeCorrentista = nomeCorrentista;
        this.sobrenomeCorrentista = sobrenomeCorrentista;
    }

    @Override
    public List<Regra> configuraRegras() {
        List<Regra> regras = new ArrayList<Regra>();

        // Adiciona regras para o numero da conta
        RegraNumeroConta regraNumeroDaConta = new RegraNumeroConta(numeroConta);
        regras.add(regraNumeroDaConta);

        // Adiciona regras para o numero da agencia
        RegraNumeroAgencia regraNumeroAgencia = new RegraNumeroAgencia(numeroAgencia);
        regras.add(regraNumeroAgencia);

        // Adiciona regras para o tipo de conta
        RegraTipoConta regraTipoConta = new RegraTipoConta(tipoConta);
        regras.add(regraTipoConta);

        return regras;
    }
}
