package br.edu.senai.fatesg.ads3.car_repair.business.ordens;

import br.edu.senai.fatesg.ads3.car_repair.core.dtos.BaseDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class OrdemDTO extends BaseDTO {

    @NotNull(message = "O ID do veículo é obrigatório.")
    private UUID veiculoId;

    // Campos de exibição (somente retorno)
    private String veiculoPlaca;
    private String veiculoModelo;
    private String clienteNome;

    private OrdemStatus status;

    private String descricaoProblema;
    private String diagnostico;
    private String observacoes;

    private Date dataPrevisaoEntrega;
    private Date dataConclusao;

    private Integer kmVeiculo;

    private BigDecimal valorTotal;

    @Valid
    private List<OrdemItemDTO> itens = new ArrayList<>();
}
