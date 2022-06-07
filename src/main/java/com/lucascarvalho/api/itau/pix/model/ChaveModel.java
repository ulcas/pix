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
    private String nmTipoChave;
    @Column(nullable = false, length = 77)
    private String vlChave;
    @Column(nullable = false, length = 10)
    private String nmTipoConta;
    @Column(nullable = false, length = 4)
    private String nuAgencia;
    @Column(nullable = false, length = 8)
    private String nuConta;
    @Column(nullable = false, length = 30)
    private String nmNomeCorrentista;
    @Column(length = 45)
    private String nmSobrenomeCorrentista;
    @Column(nullable = false, length = 1)
    private String nmTipoPessoa;
    @Column(insertable = false, updatable = false)
    private Date dtInclusao;
    @PrePersist
    void dt() {
        this.dtInclusao = new java.util.Date();
    }
}
