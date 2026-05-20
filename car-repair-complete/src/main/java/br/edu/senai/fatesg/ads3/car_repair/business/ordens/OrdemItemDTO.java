package br.edu.senai.fatesg.ads3.car_repair.business.ordens;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class OrdemItemDTO {

    private UUID id;

    @NotNull(message = "O ID do serviço é obrigatório.")
    private UUID servicoId;

    private String servicoNome;

    @NotNull(message = "A quantidade é obrigatória.")
    @Min(value = 1, message = "A quantidade deve ser pelo menos 1.")
    private Integer quantidade;

    @NotNull(message = "O preço unitário é obrigatório.")
    @DecimalMin(value = "0.01", message = "O preço unitário deve ser maior que zero.")
    private BigDecimal precoUnitario;

    private BigDecimal subtotal;

    private String observacao;
}
