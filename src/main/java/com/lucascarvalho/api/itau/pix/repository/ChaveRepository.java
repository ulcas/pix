package com.lucascarvalho.api.itau.pix.repository;

import com.lucascarvalho.api.itau.pix.model.ChaveModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ChaveRepository extends JpaRepository<ChaveModel, UUID> {
    public List<ChaveModel> findByNmTipoChaveIgnoreCase(String nmTipoChave);
    public List<ChaveModel> findByNmTipoChaveAndVlChave(String nmTipoChave, String vlChave);
    public List<ChaveModel> findByVlChaveEquals(String vlChave);
    public List<ChaveModel> findByNmNomeCorrentistaLikeIgnoreCase(String nmCorrentista);
    public List<ChaveModel> findByNuAgenciaAndNuConta(String nuAgencia, String nuConta);
    public List<ChaveModel> findByNmTipoChaveIgnoreCaseAndVlChaveAndNuContaAndNuAgencia(String nmTipoChave, String vlChave, String nuAgencia, String nuConta);
    public List<ChaveModel> findByNuContaAndNuAgenciaAndNmTipoPessoa(String nuConta, String nuAgencia, String nmTipoPessoa);
}
