package br.edu.senai.fatesg.ads3.car_repair.business.veiculos;

import br.edu.senai.fatesg.ads3.car_repair.core.dtos.BaseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class VeiculoDTO extends BaseDTO {

    @NotNull(message = "O ID do cliente é obrigatório.")
    private UUID clienteId;

    // Campos de exibição do cliente (somente retorno)
    private String clienteNome;

    @NotBlank(message = "A placa é obrigatória.")
    @Size(max = 8, message = "A placa deve ter no máximo 8 caracteres.")
    private String placa;

    @NotBlank(message = "A marca é obrigatória.")
    @Size(max = 60, message = "A marca deve ter no máximo 60 caracteres.")
    private String marca;

    @NotBlank(message = "O modelo é obrigatório.")
    @Size(max = 80, message = "O modelo deve ter no máximo 80 caracteres.")
    private String modelo;

    private Integer anoFabricacao;

    private Integer anoModelo;

    @Size(max = 40, message = "A cor deve ter no máximo 40 caracteres.")
    private String cor;

    @Size(max = 11, message = "O RENAVAM deve ter no máximo 11 dígitos.")
    private String renavam;

    @Size(max = 20, message = "O combustível deve ter no máximo 20 caracteres.")
    private String combustivel;
}
