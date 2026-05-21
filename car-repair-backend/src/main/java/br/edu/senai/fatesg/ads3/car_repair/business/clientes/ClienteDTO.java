package br.edu.senai.fatesg.ads3.car_repair.business.clientes;

import br.edu.senai.fatesg.ads3.car_repair.core.dtos.BaseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteDTO extends BaseDTO {

    @NotBlank(message = "Nome é obrigatório.")
    @Size(max = 120, message = "Nome deve ter no máximo 120 caracteres.")
    private String nome;

    @NotBlank(message = "CPF é obrigatório.")
    @Size(min = 11, max = 11, message = "CPF deve conter exatamente 11 dígitos.")
    private String cpf;

    @Size(max = 11, message = "Telefone deve ter no máximo 11 dígitos.")
    private String telefone;

    @Size(max = 150, message = "E-mail deve ter no máximo 150 caracteres.")
    private String email;

    @Size(max = 255, message = "Endereço deve ter no máximo 255 caracteres.")
    private String endereco;

    @Size(max = 70, message = "Bairro deve ter no máximo 70 caracteres.")
    private String bairro;

    @Size(max = 70, message = "Cidade deve ter no máximo 70 caracteres.")
    private String cidade;

    @Size(max = 2, message = "Estado deve ter no máximo 2 caracteres.")
    private String estado;

    @Size(max = 8, message = "CEP deve ter no máximo 8 dígitos.")
    private String cep;
}
