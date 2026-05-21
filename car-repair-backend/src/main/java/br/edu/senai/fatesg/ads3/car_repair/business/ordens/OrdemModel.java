package br.edu.senai.fatesg.ads3.car_repair.business.ordens;

import br.edu.senai.fatesg.ads3.car_repair.business.veiculos.VeiculoModel;
import br.edu.senai.fatesg.ads3.car_repair.core.domains.BaseModel;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ordens_servico")
@Data
@EqualsAndHashCode(callSuper = true)
public class OrdemModel extends BaseModel {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veiculo_id", nullable = false)
    private VeiculoModel veiculo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    private OrdemStatus status = OrdemStatus.ABERTA;

    @Column(name = "descricao_problema", length = 1000)
    private String descricaoProblema;

    @Column(name = "diagnostico", length = 1000)
    private String diagnostico;

    @Column(name = "observacoes", length = 500)
    private String observacoes;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_previsao_entrega")
    private Date dataPrevisaoEntrega;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_conclusao")
    private Date dataConclusao;

    @Column(name = "km_veiculo")
    private Integer kmVeiculo;

    @Column(name = "valor_total", precision = 10, scale = 2)
    private BigDecimal valorTotal = BigDecimal.ZERO;

    @OneToMany(mappedBy = "ordem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrdemItemModel> itens = new ArrayList<>();

    /** Recalcula o valorTotal com base nos itens. */
    public void recalcularTotal() {
        this.valorTotal = itens.stream()
                .map(OrdemItemModel::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
