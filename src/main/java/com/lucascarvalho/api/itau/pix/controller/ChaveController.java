package com.lucascarvalho.api.itau.pix.controller;

import com.lucascarvalho.api.itau.pix.command.AlterarChaveCommand;
import com.lucascarvalho.api.itau.pix.command.InserirChaveCommand;
import com.lucascarvalho.api.itau.pix.model.ChaveModel;
import com.lucascarvalho.api.itau.pix.repository.ChaveRepository;
import com.lucascarvalho.api.itau.pix.validate.ValidateException;
import com.lucascarvalho.api.itau.pix.validate.regra.RegraNumeroAgencia;
import com.lucascarvalho.api.itau.pix.validate.regra.RegraNumeroConta;
import com.lucascarvalho.api.itau.pix.validate.regra.RegraTipoConta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
public class ChaveController {

    @Autowired
    private ChaveRepository chaveRepository;

    @GetMapping(path = "/api/pix/chave/{id}")
    public ResponseEntity<ChaveModel> consultar(@PathVariable("id") UUID id) {

        List<String> errors = new ArrayList<>();
        errors.add("Chave não encontrada");

        boolean chaveExists = chaveRepository.findById(id).isPresent();

        if (chaveExists) {
            ChaveModel chave = chaveRepository.findById(id).get();
            return ResponseEntity.ok().body(chave);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping(path = "/api/pix/chave/tipo-chave/{nmTipoChave}")
    public ResponseEntity<List<ChaveModel>> consultarByTipoChave(@PathVariable("nmTipoChave") String nmTipoChave) {

        List <ChaveModel> result = chaveRepository.findByNmTipoChaveIgnoreCase(nmTipoChave);

        if (result.size() == 0) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(result);
    }

    @GetMapping(path = "/api/pix/chave/tipo-valor-chave/{nmTipoChave}/{vlChave}")
    public ResponseEntity<List<ChaveModel>> consultarByTipoChaveAndVlChave(
            @PathVariable("nmTipoChave") String nmTipoChave,
            @PathVariable("vlChave") String vlChave) {

        List <ChaveModel> result = chaveRepository.findByNmTipoChaveAndVlChave(nmTipoChave, vlChave);

        if (result.size() == 0) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(result);
    }

    @GetMapping(path = "/api/pix/chave/nm-correntista/{nmCorrentista}")
    public ResponseEntity<List<ChaveModel>> consultarByNmNomeCorrentista(
            @PathVariable("nmCorrentista") String nmCorrentista) {

        List <ChaveModel> result = chaveRepository.findByNmNomeCorrentistaLikeIgnoreCase(nmCorrentista);

        if (result.size() == 0) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(result);
    }

    @GetMapping(path = "/api/pix/chave/agencia-conta/{nuAgencia}/{nuConta}")
    public ResponseEntity<List<ChaveModel>> consultarByNmAgenciaNmConta(
            @PathVariable("nuAgencia") String nuAgencia,
            @PathVariable("nuConta") String nuConta) {

        List <ChaveModel> result = chaveRepository.findByNuAgenciaAndNuConta(nuAgencia, nuConta);

        if (result.size() == 0) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(result);
    }

    @GetMapping(path = "/api/pix/chave/consulta-combinada")
    public ResponseEntity<List<ChaveModel>> consultarByTipoChaveAndVlChaveAndNuContaAndNuAgencia(
            @RequestParam("nmTipoChave") String nmTipoChave,
            @RequestParam("vlChave") String vlChave,
            @RequestParam("nuConta") String nuConta,
            @RequestParam("nuAgencia") String nuAgencia) {

        List <ChaveModel> result =
                chaveRepository.
                        findByNmTipoChaveIgnoreCaseAndVlChaveAndNuContaAndNuAgencia(
                                nmTipoChave,
                                vlChave,
                                nuConta,
                                nuAgencia
                        );

        if (result.size() == 0) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(result);
    }

    @GetMapping(path = "/api/pix/chave/listar-todos")
    public List<ChaveModel> listarTodos() {
        return chaveRepository.findAll();
    }

    @PostMapping(path = "/api/pix/chave/salvar")
    public ResponseEntity<ChaveModel> salvar(@RequestBody ChaveModel chave) throws ValidateException {

        InserirChaveCommand insert = new InserirChaveCommand(
                chave.getNmTipoChave(),
                chave.getVlChave(),
                chave.getNmTipoConta(),
                chave.getNuAgencia(),
                chave.getNuConta(),
                chave.getNmNomeCorrentista(),
                chave.getNmSobrenomeCorrentista(),
                chave.getNmTipoPessoa()
        );

        if (!insert.executa()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        List <ChaveModel> chaveExists = chaveRepository.findByVlChaveEquals(chave.getVlChave());

        List <ChaveModel> chavesCount = chaveRepository.
                findByNuContaAndNuAgenciaAndNmTipoPessoa(
                        chave.getNuConta(),
                        chave.getNuAgencia(),
                        chave.getNmTipoPessoa()
                );

        if (Objects.equals(chave.getNmTipoPessoa(), "F") && chavesCount.size() == 5) {
            throw new HttpClientErrorException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Quantidade de chaves excedeu 5"
            );
        }

        if (Objects.equals(chave.getNmTipoPessoa(), "J") && chavesCount.size() == 20) {
            throw new HttpClientErrorException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Quantidade de chaves excedeu 20"
            );
        }

        if (chaveExists.isEmpty()) {
            chaveRepository.save(chave);
            return ResponseEntity.ok().body(chave);
        }

        throw new HttpClientErrorException(
                HttpStatus.UNPROCESSABLE_ENTITY,
                "Chave já existente"
        );
    }
    @PutMapping(path = "/api/pix/chave/alteracao/{id}")
    public ResponseEntity alteracao(@PathVariable("id") UUID id,
                                    @RequestBody ChaveModel chave) throws HttpClientErrorException {
        return chaveRepository.findById(id)
                .map(record -> {
                    record.setNmTipoConta(chave.getNmTipoConta());
                    record.setNuConta(chave.getNuConta());
                    record.setNuAgencia(chave.getNuAgencia());
                    record.setNmNomeCorrentista(chave.getNmNomeCorrentista());
                    record.setNmSobrenomeCorrentista(chave.getNmSobrenomeCorrentista());

                    AlterarChaveCommand alter = new AlterarChaveCommand(
                            chave.getNmTipoConta(),
                            chave.getNuAgencia(),
                            chave.getNuConta(),
                            chave.getNmNomeCorrentista(),
                            chave.getNmSobrenomeCorrentista()
                    );

                    try {
                        if (!alter.executa()) {
                            return ResponseEntity.unprocessableEntity().build();
                        }

                        ChaveModel atualizado = chaveRepository.save(record);
                        return ResponseEntity.ok().body(atualizado);
                    } catch (ValidateException e) {
                        throw new RuntimeException(e);
                    }
                }).orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping(path = "/api/pix/chave/alteracao/{id}")
    public ResponseEntity<ChaveModel> alteracaoParcial(@PathVariable("id") UUID id,
                                                       @RequestBody ChaveModel chave) {
        ChaveModel reg = chaveRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "ID não existente"));

        try {
            if (chave.getNmTipoConta() != null) {
                RegraTipoConta regraTipoConta = new RegraTipoConta(chave.getNmTipoConta());
                if (!regraTipoConta.valida()) {
                    throw new HttpClientErrorException(
                            HttpStatus.UNPROCESSABLE_ENTITY,
                            "Valor inválido"
                    );
                }
                reg.setNmTipoConta(chave.getNmTipoConta());
                chaveRepository.save(reg);
            }
            //Número da conta
            if (chave.getNuConta() != null) {
                RegraNumeroConta regraNumeroConta = new RegraNumeroConta(chave.getNuConta());
                if (!regraNumeroConta.valida()) {
                    throw new HttpClientErrorException(
                            HttpStatus.UNPROCESSABLE_ENTITY,
                            "Valor inválido"
                    );
                }
                reg.setNuConta(chave.getNuConta());
                chaveRepository.save(reg);
            }
            //Número da Agência
            if (chave.getNuAgencia() != null) {
                RegraNumeroAgencia regraNumeroAgencia = new RegraNumeroAgencia(chave.getNuAgencia());
                if (!regraNumeroAgencia.valida()) {
                    throw new HttpClientErrorException(
                            HttpStatus.UNPROCESSABLE_ENTITY,
                            "Valor inválido"
                    );
                }
                reg.setNuAgencia(chave.getNuAgencia());
                chaveRepository.save(reg);
            }
            //Nome do correntista
            if (chave.getNmNomeCorrentista() != null) {
                reg.setNmNomeCorrentista(chave.getNmNomeCorrentista());
                chaveRepository.save(reg);
            }
            //Sobrenome do conta
            if (chave.getNmSobrenomeCorrentista() != null) {
                reg.setNmSobrenomeCorrentista(chave.getNmSobrenomeCorrentista());
                chaveRepository.save(reg);
            }

            return ResponseEntity.ok().body(reg);
        } catch (HttpClientErrorException exception) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }
}
