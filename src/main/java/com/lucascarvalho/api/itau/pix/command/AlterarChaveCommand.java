package com.lucascarvalho.api.itau.pix.command;

import com.lucascarvalho.api.itau.pix.validate.AlterarChaveValidate;
import com.lucascarvalho.api.itau.pix.validate.InserirChaveValidate;
import com.lucascarvalho.api.itau.pix.validate.ValidateException;
import org.springframework.http.HttpStatus;

public class AlterarChaveCommand {

    private String tipoConta;
    private String numeroAgencia;
    private String numeroConta;
    private String nomeCorrentista;
    private String sobrenomeCorrentista;

    public AlterarChaveCommand(String tipoConta,
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

    public boolean executa() throws ValidateException {
        AlterarChaveValidate validador = new AlterarChaveValidate(
                tipoConta,
                numeroAgencia,
                numeroConta,
                nomeCorrentista,
                sobrenomeCorrentista
        );

        if (!validador.executa()) {
            throw new ValidateException(validador.getErros(), HttpStatus.NOT_FOUND);
        }

        return validador.executa();
    }
}
