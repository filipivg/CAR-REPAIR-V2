package br.edu.senai.fatesg.ads3.car_repair.business.clientes;

import br.edu.senai.fatesg.ads3.car_repair.core.domains.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "clientes")
@Data
@EqualsAndHashCode(callSuper = true)
public class ClienteModel extends BaseModel {

    @Column(name = "nome", length = 120, nullable = false)
    private String nome;

    @Column(name = "cpf", length = 11, nullable = false, unique = true)
    private String cpf;

    @Column(name = "telefone", length = 11)
    private String telefone;

    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "endereco", length = 255)
    private String endereco;

    @Column(name = "bairro", length = 70)
    private String bairro;

    @Column(name = "cidade", length = 70)
    private String cidade;

    @Column(name = "estado", length = 2)
    private String estado;

    @Column(name = "cep", length = 8)
    private String cep;
}
