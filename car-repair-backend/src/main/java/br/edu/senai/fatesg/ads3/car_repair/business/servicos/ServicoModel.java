package br.edu.senai.fatesg.ads3.car_repair.business.servicos;

import br.edu.senai.fatesg.ads3.car_repair.core.domains.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Entity
@Table(name = "servicos")
@Data
@EqualsAndHashCode(callSuper = true)
public class ServicoModel extends BaseModel {

    @Column(name = "nome", length = 120, nullable = false)
    private String nome;

    @Column(name = "descricao", length = 500)
    private String descricao;

    @Column(name = "preco_base", nullable = false, precision = 10, scale = 2)
    private BigDecimal precoBase;

    @Column(name = "tempo_estimado_minutos")
    private Integer tempoEstimadoMinutos;
}
