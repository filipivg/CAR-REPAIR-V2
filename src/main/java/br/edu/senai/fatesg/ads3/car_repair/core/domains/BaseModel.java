package br.edu.senai.fatesg.ads3.car_repair.core.domains;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@MappedSuperclass
public abstract class BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", insertable = false, updatable = false)
    private UUID id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_hora_criacao", updatable = false)
    private Date dataHoraCriacao;

    @Column(name = "ativo", nullable = false)
    private boolean ativo = true;

    /**
     * Preenche automaticamente dataHoraCriacao e ativo antes de inserir no banco.
     */
    @PrePersist
    protected void prePersist() {
        if (this.dataHoraCriacao == null) {
            this.dataHoraCriacao = new Date();
        }
        this.ativo = true;
    }
}
