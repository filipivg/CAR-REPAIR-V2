package br.edu.senai.fatesg.ads3.car_repair.business.veiculos;

import br.edu.senai.fatesg.ads3.car_repair.business.clientes.ClienteModel;
import br.edu.senai.fatesg.ads3.car_repair.core.domains.BaseModel;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "veiculos")
@Data
@EqualsAndHashCode(callSuper = true)
public class VeiculoModel extends BaseModel {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private ClienteModel cliente;

    @Column(name = "placa", length = 8, nullable = false, unique = true)
    private String placa;

    @Column(name = "marca", length = 60, nullable = false)
    private String marca;

    @Column(name = "modelo", length = 80, nullable = false)
    private String modelo;

    @Column(name = "ano_fabricacao")
    private Integer anoFabricacao;

    @Column(name = "ano_modelo")
    private Integer anoModelo;

    @Column(name = "cor", length = 40)
    private String cor;

    @Column(name = "renavam", length = 11)
    private String renavam;

    @Column(name = "combustivel", length = 20)
    private String combustivel;
}
