package com.lucascarvalho.api.itau.pix.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "chaves")
public class ChaveModel {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID    id;

    @Column(nullable = false, length = 9)
    private String  nm_tipo_chave;

    @Column(nullable = false, length = 77)
    private String  vl_chave;

    @Column(nullable = false, length = 10)
    private String  nm_tipo_conta;

    @Column(nullable = false, length = 4)
    private Long    nu_agencia;

    @Column(nullable = false, length = 8)
    private Long    nu_conta;

    @Column(nullable = false, length = 30)
    private String  nm_nome_correntista;

    @Column(length = 45)
    private String  nm_sobrenome_correntista;

    @Column(insertable = false, updatable = false)
    private Date dt_inclusao;

    @PrePersist
    void dt() {
        this.dt_inclusao = new java.util.Date();
    }
}
