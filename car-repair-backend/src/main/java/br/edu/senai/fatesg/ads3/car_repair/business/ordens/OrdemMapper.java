package br.edu.senai.fatesg.ads3.car_repair.business.ordens;

import br.edu.senai.fatesg.ads3.car_repair.business.servicos.ServicoModel;
import br.edu.senai.fatesg.ads3.car_repair.business.veiculos.VeiculoModel;
import br.edu.senai.fatesg.ads3.car_repair.core.helpers.IGenericMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrdemMapper implements IGenericMapper<OrdemModel, OrdemDTO> {

    @Override
    public OrdemDTO toDto(OrdemModel entity) {
        if (entity == null) return null;
        OrdemDTO dto = new OrdemDTO();
        dto.setId(entity.getId());
        dto.setDataHoraCriacao(entity.getDataHoraCriacao());
        dto.setAtivo(entity.isAtivo());

        if (entity.getVeiculo() != null) {
            dto.setVeiculoId(entity.getVeiculo().getId());
            dto.setVeiculoPlaca(entity.getVeiculo().getPlaca());
            dto.setVeiculoModelo(entity.getVeiculo().getModelo());
            if (entity.getVeiculo().getCliente() != null) {
                dto.setClienteNome(entity.getVeiculo().getCliente().getNome());
            }
        }

        dto.setStatus(entity.getStatus());
        dto.setDescricaoProblema(entity.getDescricaoProblema());
        dto.setDiagnostico(entity.getDiagnostico());
        dto.setObservacoes(entity.getObservacoes());
        dto.setDataPrevisaoEntrega(entity.getDataPrevisaoEntrega());
        dto.setDataConclusao(entity.getDataConclusao());
        dto.setKmVeiculo(entity.getKmVeiculo());
        dto.setValorTotal(entity.getValorTotal());
        dto.setItens(toItemDtoList(entity.getItens()));
        return dto;
    }

    @Override
    public OrdemModel toEntity(OrdemDTO dto) {
        if (dto == null) return null;
        OrdemModel entity = new OrdemModel();
        entity.setId(dto.getId());

        if (dto.getVeiculoId() != null) {
            VeiculoModel veiculo = new VeiculoModel();
            veiculo.setId(dto.getVeiculoId());
            entity.setVeiculo(veiculo);
        }

        entity.setStatus(dto.getStatus() != null ? dto.getStatus() : OrdemStatus.ABERTA);
        entity.setDescricaoProblema(dto.getDescricaoProblema());
        entity.setDiagnostico(dto.getDiagnostico());
        entity.setObservacoes(dto.getObservacoes());
        entity.setDataPrevisaoEntrega(dto.getDataPrevisaoEntrega());
        entity.setKmVeiculo(dto.getKmVeiculo());

        if (dto.getItens() != null) {
            List<OrdemItemModel> itens = new ArrayList<>();
            for (OrdemItemDTO itemDto : dto.getItens()) {
                OrdemItemModel item = toItemEntity(itemDto);
                item.setOrdem(entity);
                itens.add(item);
            }
            entity.setItens(itens);
            entity.recalcularTotal();
        }

        return entity;
    }

    private OrdemItemDTO toItemDto(OrdemItemModel item) {
        OrdemItemDTO dto = new OrdemItemDTO();
        dto.setId(item.getId());
        if (item.getServico() != null) {
            dto.setServicoId(item.getServico().getId());
            dto.setServicoNome(item.getServico().getNome());
        }
        dto.setQuantidade(item.getQuantidade());
        dto.setPrecoUnitario(item.getPrecoUnitario());
        dto.setSubtotal(item.getSubtotal());
        dto.setObservacao(item.getObservacao());
        return dto;
    }

    private OrdemItemModel toItemEntity(OrdemItemDTO dto) {
        OrdemItemModel item = new OrdemItemModel();
        item.setId(dto.getId());
        if (dto.getServicoId() != null) {
            ServicoModel servico = new ServicoModel();
            servico.setId(dto.getServicoId());
            item.setServico(servico);
        }
        item.setQuantidade(dto.getQuantidade() != null ? dto.getQuantidade() : 1);
        item.setPrecoUnitario(dto.getPrecoUnitario());
        item.setObservacao(dto.getObservacao());
        return item;
    }

    private List<OrdemItemDTO> toItemDtoList(List<OrdemItemModel> itens) {
        if (itens == null) return new ArrayList<>();
        return itens.stream().map(this::toItemDto).toList();
    }
}
