package br.edu.senai.fatesg.ads3.car_repair.business.veiculos;

import br.edu.senai.fatesg.ads3.car_repair.business.clientes.ClienteModel;
import br.edu.senai.fatesg.ads3.car_repair.core.helpers.IGenericMapper;
import org.springframework.stereotype.Component;

@Component
public class VeiculoMapper implements IGenericMapper<VeiculoModel, VeiculoDTO> {

    @Override
    public VeiculoDTO toDto(VeiculoModel entity) {
        if (entity == null) return null;
        VeiculoDTO dto = new VeiculoDTO();
        dto.setId(entity.getId());
        dto.setDataHoraCriacao(entity.getDataHoraCriacao());
        dto.setAtivo(entity.isAtivo());

        if (entity.getCliente() != null) {
            dto.setClienteId(entity.getCliente().getId());
            dto.setClienteNome(entity.getCliente().getNome());
        }

        dto.setPlaca(entity.getPlaca());
        dto.setMarca(entity.getMarca());
        dto.setModelo(entity.getModelo());
        dto.setAnoFabricacao(entity.getAnoFabricacao());
        dto.setAnoModelo(entity.getAnoModelo());
        dto.setCor(entity.getCor());
        dto.setRenavam(entity.getRenavam());
        dto.setCombustivel(entity.getCombustivel());
        return dto;
    }

    @Override
    public VeiculoModel toEntity(VeiculoDTO dto) {
        if (dto == null) return null;
        VeiculoModel entity = new VeiculoModel();
        entity.setId(dto.getId());

        if (dto.getClienteId() != null) {
            ClienteModel cliente = new ClienteModel();
            cliente.setId(dto.getClienteId());
            entity.setCliente(cliente);
        }

        entity.setPlaca(dto.getPlaca());
        entity.setMarca(dto.getMarca());
        entity.setModelo(dto.getModelo());
        entity.setAnoFabricacao(dto.getAnoFabricacao());
        entity.setAnoModelo(dto.getAnoModelo());
        entity.setCor(dto.getCor());
        entity.setRenavam(dto.getRenavam());
        entity.setCombustivel(dto.getCombustivel());
        return entity;
    }
}
