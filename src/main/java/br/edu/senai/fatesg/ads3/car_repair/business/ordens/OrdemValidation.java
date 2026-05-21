package br.edu.senai.fatesg.ads3.car_repair.business.ordens;

import br.edu.senai.fatesg.ads3.car_repair.core.exceptions.FieldValidationException;
import br.edu.senai.fatesg.ads3.car_repair.core.exceptions.RuleValidationException;
import br.edu.senai.fatesg.ads3.car_repair.core.validations.GenericValidation;
import org.springframework.stereotype.Component;

@Component
public class OrdemValidation extends GenericValidation<OrdemModel, IOrdemRepository> {

    @Override
    public void validateFields(OrdemModel entity) {
        super.validateFields(entity);

        if (entity.getVeiculo() == null || entity.getVeiculo().getId() == null) {
            throw new FieldValidationException("veiculoId", "O veículo é obrigatório.");
        }
    }

    @Override
    public void validateUpdate(OrdemModel entity) {
        super.validateFieldsUpdate(entity);

        // Busca a ordem atual no banco para verificar transições de status
        repository.findById(entity.getId()).ifPresent(ordemAtual -> {
            OrdemStatus statusAtual = ordemAtual.getStatus();
            OrdemStatus novoStatus = entity.getStatus();

            if (novoStatus == null) return;

            if (statusAtual == OrdemStatus.CONCLUIDA || statusAtual == OrdemStatus.CANCELADA) {
                throw new RuleValidationException("status",
                        "Não é possível alterar uma ordem " + statusAtual.name().toLowerCase() + ".");
            }

            if (novoStatus == OrdemStatus.CONCLUIDA && statusAtual != OrdemStatus.EM_ANDAMENTO) {
                throw new RuleValidationException("status",
                        "Só é possível concluir uma ordem que esteja em andamento.");
            }
        });
    }
}
