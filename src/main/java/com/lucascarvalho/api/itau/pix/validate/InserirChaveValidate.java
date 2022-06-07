package com.lucascarvalho.api.itau.pix.validate;

import com.lucascarvalho.api.itau.pix.validate.regra.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InserirChaveValidate extends ValidateAbstract {

    private String tipoChave;
    private String valorChave;
    private String tipoConta;
    private String numeroAgencia;
    private String numeroConta;
    private String nomeCorrentista;
    private String sobrenomeCorrentista;
    private String tipoPessoa;

    public InserirChaveValidate(String tipoChave,
                                String valorChave,
                                String tipoConta,
                                String numeroAgencia,
                                String numeroConta,
                                String nomeCorrentista,
                                String sobrenomeCorrentista,
                                String tipoPessoa) {

        this.tipoChave = tipoChave;
        this.valorChave = valorChave;
        this.tipoConta = tipoConta;
        this.numeroAgencia = numeroAgencia;
        this.numeroConta = numeroConta;
        this.nomeCorrentista = nomeCorrentista;
        this.sobrenomeCorrentista = sobrenomeCorrentista;
        this.tipoPessoa = tipoPessoa;
    }

    @Override
    public List<Regra> configuraRegras() {
        List<Regra> regras = new ArrayList<Regra>();

        // Adiciona regra de tipo de chave pix
        RegraTipoChave regraTipoChavePix = new RegraTipoChave(tipoChave);
        regras.add(regraTipoChavePix);

        // Adiciona regras para o numero da conta
        RegraNumeroConta regraNumeroDaConta = new RegraNumeroConta(numeroConta);
        regras.add(regraNumeroDaConta);

        // Adiciona regras para o numero da agencia
        RegraNumeroAgencia regraNumeroAgencia = new RegraNumeroAgencia(numeroAgencia);
        regras.add(regraNumeroAgencia);

        // Regra para valor da chave pix
        Optional<Regra> regraChave = ValidateFactory.getInstance(tipoChave, valorChave);
        if (regraChave.isPresent()) {
            regras.add(regraChave.get());
        }

        // Adiciona regras para o tipo de conta
        RegraTipoConta regraTipoConta = new RegraTipoConta(tipoConta);
        regras.add(regraTipoConta);

        // Adiciona regras para o tipo de pessoa
        RegraTipoPessoa regraTipoPessoa = new RegraTipoPessoa(tipoPessoa);
        regras.add(regraTipoPessoa);

        return regras;
    }
}
