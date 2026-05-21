package br.edu.senai.fatesg.ads3.car_repair.business.clientes;

import br.edu.senai.fatesg.ads3.car_repair.core.helpers.IGenericMapper;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper implements IGenericMapper<ClienteModel, ClienteDTO> {

    @Override
    public ClienteDTO toDto(ClienteModel entity) {
        if (entity == null) return null;
        ClienteDTO dto = new ClienteDTO();
        dto.setId(entity.getId());
        dto.setDataHoraCriacao(entity.getDataHoraCriacao());
        dto.setAtivo(entity.isAtivo());
        dto.setNome(entity.getNome());
        dto.setCpf(entity.getCpf());
        dto.setTelefone(entity.getTelefone());
        dto.setEmail(entity.getEmail());
        dto.setEndereco(entity.getEndereco());
        dto.setBairro(entity.getBairro());
        dto.setCidade(entity.getCidade());
        dto.setEstado(entity.getEstado());
        dto.setCep(entity.getCep());
        return dto;
    }

    @Override
    public ClienteModel toEntity(ClienteDTO dto) {
        if (dto == null) return null;
        ClienteModel entity = new ClienteModel();
        entity.setId(dto.getId());
        entity.setNome(dto.getNome());
        entity.setCpf(dto.getCpf());
        entity.setTelefone(dto.getTelefone());
        entity.setEmail(dto.getEmail());
        entity.setEndereco(dto.getEndereco());
        entity.setBairro(dto.getBairro());
        entity.setCidade(dto.getCidade());
        entity.setEstado(dto.getEstado());
        entity.setCep(dto.getCep());
        return entity;
    }
}
