package br.edu.senai.fatesg.ads3.car_repair.business.servicos;

import br.edu.senai.fatesg.ads3.car_repair.core.helpers.IGenericMapper;
import org.springframework.stereotype.Component;

@Component
public class ServicoMapper implements IGenericMapper<ServicoModel, ServicoDTO> {

    @Override
    public ServicoDTO toDto(ServicoModel entity) {
        if (entity == null) return null;
        ServicoDTO dto = new ServicoDTO();
        dto.setId(entity.getId());
        dto.setDataHoraCriacao(entity.getDataHoraCriacao());
        dto.setAtivo(entity.isAtivo());
        dto.setNome(entity.getNome());
        dto.setDescricao(entity.getDescricao());
        dto.setPrecoBase(entity.getPrecoBase());
        dto.setTempoEstimadoMinutos(entity.getTempoEstimadoMinutos());
        return dto;
    }

    @Override
    public ServicoModel toEntity(ServicoDTO dto) {
        if (dto == null) return null;
        ServicoModel entity = new ServicoModel();
        entity.setId(dto.getId());
        entity.setNome(dto.getNome());
        entity.setDescricao(dto.getDescricao());
        entity.setPrecoBase(dto.getPrecoBase());
        entity.setTempoEstimadoMinutos(dto.getTempoEstimadoMinutos());
        return entity;
    }
}
