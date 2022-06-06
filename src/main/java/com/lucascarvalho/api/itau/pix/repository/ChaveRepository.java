package com.lucascarvalho.api.itau.pix.repository;

import com.lucascarvalho.api.itau.pix.model.ChaveModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ChaveRepository extends JpaRepository<ChaveModel, UUID> {

    @Query(value = "SELECT '*' FROM chaves ch WHERE ch.vl_chave = ?1")
    List<ChaveModel> findChaveModelByVl_chave(String vl_chave);
}
