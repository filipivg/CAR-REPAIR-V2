package br.edu.senai.fatesg.ads3.car_repair.business.veiculos;

import br.edu.senai.fatesg.ads3.car_repair.core.exceptions.FieldValidationException;
import br.edu.senai.fatesg.ads3.car_repair.core.exceptions.RuleValidationException;
import br.edu.senai.fatesg.ads3.car_repair.core.validations.GenericValidation;
import org.springframework.stereotype.Component;

@Component
public class VeiculoValidation extends GenericValidation<VeiculoModel, IVeiculoRepository> {

    @Override
    public void validateFields(VeiculoModel entity) {
        super.validateFields(entity);

        if (entity.getCliente() == null || entity.getCliente().getId() == null) {
            throw new FieldValidationException("clienteId", "O cliente é obrigatório.");
        }
        if (entity.getPlaca() == null || entity.getPlaca().isBlank()) {
            throw new FieldValidationException("placa", "A placa é obrigatória.");
        }
        if (entity.getMarca() == null || entity.getMarca().isBlank()) {
            throw new FieldValidationException("marca", "A marca é obrigatória.");
        }
        if (entity.getModelo() == null || entity.getModelo().isBlank()) {
            throw new FieldValidationException("modelo", "O modelo é obrigatório.");
        }
    }

    @Override
    public void validateInsert(VeiculoModel entity) {
        if (repository.existsByPlacaAndAtivoTrue(entity.getPlaca())) {
            throw new RuleValidationException("placa", "Já existe um veículo ativo com esta placa.");
        }
    }

    @Override
    public void validateUpdate(VeiculoModel entity) {
        repository.findByPlacaAndAtivoTrue(entity.getPlaca()).ifPresent(existing -> {
            if (!existing.getId().equals(entity.getId())) {
                throw new RuleValidationException("placa", "Já existe outro veículo ativo com esta placa.");
            }
        });
    }
}
