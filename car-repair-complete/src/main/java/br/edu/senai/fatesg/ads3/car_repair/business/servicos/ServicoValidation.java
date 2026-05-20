package br.edu.senai.fatesg.ads3.car_repair.business.servicos;

import br.edu.senai.fatesg.ads3.car_repair.core.exceptions.FieldValidationException;
import br.edu.senai.fatesg.ads3.car_repair.core.exceptions.RuleValidationException;
import br.edu.senai.fatesg.ads3.car_repair.core.validations.GenericValidation;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ServicoValidation extends GenericValidation<ServicoModel, IServicoRepository> {

    @Override
    public void validateFields(ServicoModel entity) {
        super.validateFields(entity);

        if (entity.getNome() == null || entity.getNome().isBlank()) {
            throw new FieldValidationException("nome", "O nome do serviço é obrigatório.");
        }
        if (entity.getPrecoBase() == null || entity.getPrecoBase().compareTo(BigDecimal.ZERO) <= 0) {
            throw new FieldValidationException("precoBase", "O preço base deve ser maior que zero.");
        }
    }

    @Override
    public void validateInsert(ServicoModel entity) {
        if (repository.existsByNomeIgnoreCaseAndAtivoTrue(entity.getNome())) {
            throw new RuleValidationException("nome", "Já existe um serviço ativo com este nome.");
        }
    }

    @Override
    public void validateUpdate(ServicoModel entity) {
        repository.findByNomeIgnoreCaseAndAtivoTrue(entity.getNome()).ifPresent(existing -> {
            if (!existing.getId().equals(entity.getId())) {
                throw new RuleValidationException("nome", "Já existe outro serviço ativo com este nome.");
            }
        });
    }
}
