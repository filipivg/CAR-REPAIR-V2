package br.edu.senai.fatesg.ads3.car_repair.business.clientes;

import br.edu.senai.fatesg.ads3.car_repair.core.exceptions.FieldValidationException;
import br.edu.senai.fatesg.ads3.car_repair.core.exceptions.RuleValidationException;
import br.edu.senai.fatesg.ads3.car_repair.core.validations.GenericValidation;
import org.springframework.stereotype.Component;

@Component
public class ClienteValidation extends GenericValidation<ClienteModel, IClienteRepository> {

    @Override
    public void validateFields(ClienteModel entity) {
        super.validateFields(entity);

        if (entity.getNome() == null || entity.getNome().isBlank()) {
            throw new FieldValidationException("nome", "O nome do cliente é obrigatório.");
        }
        if (entity.getCpf() == null || entity.getCpf().isBlank()) {
            throw new FieldValidationException("cpf", "O CPF do cliente é obrigatório.");
        }
        if (entity.getCpf().length() != 11) {
            throw new FieldValidationException("cpf", "O CPF deve conter exatamente 11 dígitos.");
        }
    }

    @Override
    public void validateInsert(ClienteModel entity) {
        if (repository.existsByCpfAndAtivoTrue(entity.getCpf())) {
            throw new RuleValidationException("cpf", "Já existe um cliente ativo com este CPF.");
        }
    }

    @Override
    public void validateUpdate(ClienteModel entity) {
        // Verifica duplicidade de CPF, excluindo o próprio registro
        repository.findByCpfAndAtivoTrue(entity.getCpf()).ifPresent(existing -> {
            if (!existing.getId().equals(entity.getId())) {
                throw new RuleValidationException("cpf", "Já existe outro cliente ativo com este CPF.");
            }
        });
    }
}
