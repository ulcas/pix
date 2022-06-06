package com.lucascarvalho.api.itau.pix.controller;

import com.lucascarvalho.api.itau.pix.command.InserirChaveCommand;
import com.lucascarvalho.api.itau.pix.model.ChaveModel;
import com.lucascarvalho.api.itau.pix.repository.ChaveRepository;
import com.lucascarvalho.api.itau.pix.validate.ValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.UUID;

@RestController
public class ChaveController {

    @Autowired
    private ChaveRepository chaveRepository;

    @GetMapping(path = "/api/pix/chave/{id}")
    public ResponseEntity<ChaveModel> consultar(@PathVariable("id") UUID id)
        throws HttpClientErrorException {
        ChaveModel chave = chaveRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "bçaçasdçdasdas"));
        return ResponseEntity.ok().body(chave);
    }

    @GetMapping(path = "/api/pix/chave/listar-todos")
    public List<ChaveModel> listarTodos() {
        return chaveRepository.findAll();
    }

    @PostMapping(path = "/api/pix/chave/salvar")
    public ResponseEntity<ChaveModel> salvar(@RequestBody ChaveModel chave) throws ValidateException {

        InserirChaveCommand insert = new InserirChaveCommand(
                chave.getNm_tipo_chave(),
                chave.getVl_chave(),
                chave.getNm_tipo_conta(),
                chave.getNu_agencia(),
                chave.getNu_conta(),
                chave.getNm_nome_correntista(),
                chave.getNm_sobrenome_correntista(),
                chave.getNm_tipo_pessoa()
        );

        if (!insert.executa(chaveRepository)) {
            return ResponseEntity.notFound().build();
        }

        chaveRepository.save(chave);
        return ResponseEntity.ok().body(chave);
    }
    @PutMapping(path = "/api/pix/chave/alteracao/{id}")
    public ResponseEntity alteracao(@PathVariable("id") UUID id,
                                    @RequestBody ChaveModel chave) throws HttpClientErrorException {
        return chaveRepository.findById(id)
                .map(record -> {
                    record.setNm_tipo_conta(chave.getNm_tipo_conta());
                    record.setNu_conta(chave.getNu_conta());
                    record.setNu_agencia(chave.getNu_agencia());
                    record.setNm_nome_correntista(chave.getNm_nome_correntista());
                    record.setNm_sobrenome_correntista(chave.getNm_sobrenome_correntista());
                    ChaveModel atualizado = chaveRepository.save(record);
                    return ResponseEntity.ok().body(atualizado);
                }).orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping(path = "/api/pix/chave/alteracao/{id}")
    public ResponseEntity<ChaveModel> alteracaoParcial(@PathVariable("id") UUID id,
                                           @RequestBody ChaveModel chave) {
        ChaveModel reg = chaveRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "bçaçasdçdasdas"));

        try {
            //Tipo conta
            if (chave.getNm_tipo_conta() != null) {
                reg.setNm_tipo_conta(chave.getNm_tipo_conta());
                chaveRepository.save(reg);
            }
            //Número da conta
            if (chave.getNu_conta() != null) {
                reg.setNu_conta(chave.getNu_conta());
                chaveRepository.save(reg);
            }
            //Número da Agência
            if (chave.getNu_agencia() != null) {
                reg.setNu_agencia(chave.getNu_agencia());
                chaveRepository.save(reg);
            }
            //Nome do correntista
            if (chave.getNm_nome_correntista() != null) {
                reg.setNm_nome_correntista(chave.getNm_nome_correntista());
                chaveRepository.save(reg);
            }
            //Sobrenome do conta
            if (chave.getNm_sobrenome_correntista() != null) {
                reg.setNm_sobrenome_correntista(chave.getNm_sobrenome_correntista());
                chaveRepository.save(reg);
            }

            return ResponseEntity.ok().body(reg);
        } catch (HttpClientErrorException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ValidateException.class)
    public ResponseEntity<Object> exception(ValidateException exception) {
        return new ResponseEntity<>(exception.getErros(),HttpStatus.NOT_FOUND);
    }
}
