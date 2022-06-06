package com.lucascarvalho.api.itau.pix.command;

import com.lucascarvalho.api.itau.pix.repository.ChaveRepository;
import com.lucascarvalho.api.itau.pix.validate.InserirChaveValidate;
import com.lucascarvalho.api.itau.pix.validate.ValidateException;

import java.util.ArrayList;
import java.util.List;

public class InserirChaveCommand {

    private String tipoChave;
    private String valorChave;
    private String tipoConta;
    private String numeroAgencia;
    private String numeroConta;
    private String nomeCorrentista;
    private String sobrenomeCorrentista;
    private String tipoPessoa;

    public InserirChaveCommand(String tipoChave,
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

    public boolean executa(ChaveRepository chaveRepository) throws ValidateException {
        InserirChaveValidate validador = new InserirChaveValidate(
                tipoChave,
                valorChave,
                tipoConta,
                numeroAgencia,
                numeroConta,
                nomeCorrentista,
                sobrenomeCorrentista,
                tipoPessoa
        );

        if (!validador.executa()) {
            throw new ValidateException(validador.getErros());
        }

        return validador.executa();
    }
}
