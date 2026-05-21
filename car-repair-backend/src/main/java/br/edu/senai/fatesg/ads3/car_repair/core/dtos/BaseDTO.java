package br.edu.senai.fatesg.ads3.car_repair.core.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public abstract class BaseDTO {
    private UUID id;
    private Date dataHoraCriacao;
    private boolean ativo;
}
