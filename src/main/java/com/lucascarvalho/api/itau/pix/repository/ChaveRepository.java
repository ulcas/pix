package com.lucascarvalho.api.itau.pix.repository;

import com.lucascarvalho.api.itau.pix.model.ChaveModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChaveRepository extends JpaRepository<ChaveModel, UUID> {
}
