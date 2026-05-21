package br.edu.senai.fatesg.ads3.car_repair.core.services;

import br.edu.senai.fatesg.ads3.car_repair.core.domains.BaseModel;
import br.edu.senai.fatesg.ads3.car_repair.core.exceptions.BusinessException;
import br.edu.senai.fatesg.ads3.car_repair.core.exceptions.FieldValidationException;
import br.edu.senai.fatesg.ads3.car_repair.core.exceptions.RuleValidationException;
import br.edu.senai.fatesg.ads3.car_repair.core.repositories.IGenericRepository;
import br.edu.senai.fatesg.ads3.car_repair.core.validations.IGenericValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public abstract class GenericService<
        E extends BaseModel,
        R extends IGenericRepository<E>,
        V extends IGenericValidation<E, R>>
        implements IGenericService<E, R, V> {

    @Autowired
    protected R repository;

    @Autowired
    protected V validation;

    @Override
    @Transactional(readOnly = true)
    public E findByIdActive(UUID id) {
        try {
            return repository.findByIdAndAtivoTrue(id)
                    .orElseThrow(() -> new BusinessException(
                            "O registro com o ID informado não foi encontrado ou está inativo.",
                            HttpStatus.NOT_FOUND));
        } catch (BusinessException be) {
            throw be;
        } catch (Exception e) {
            throw new BusinessException("Erro ao localizar o registro em " + getEntityName(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<E> findAllActive(Pageable pageable) {
        try {
            return repository.findAllByAtivoTrue(pageable);
        } catch (BusinessException be) {
            throw be;
        } catch (Exception e) {
            throw new BusinessException("Erro ao listar registros em " + getEntityName(), e);
        }
    }

    @Override
    @Transactional
    public E insert(E entity) {
        try {
            validation.validateFieldsInsert(entity);
            validation.validateInsert(entity);
            beforeInsert(entity);

            E saved = repository.save(entity);
            afterInsert(saved, entity);
            return saved;
        } catch (BusinessException | FieldValidationException | RuleValidationException be) {
            throw be;
        } catch (DataAccessException dae) {
            throw new BusinessException("Falha de persistência ao inserir " + getEntityName(), dae);
        } catch (Exception e) {
            throw new BusinessException("Erro inesperado ao processar inserção em " + getEntityName(), e);
        }
    }

    @Override
    @Transactional
    public E update(E entity) {
        try {
            validation.validateFieldsUpdate(entity);
            validation.validateUpdate(entity);
            beforeUpdate(entity);

            E saved = repository.save(entity);
            afterUpdate(saved, entity);
            return saved;
        } catch (BusinessException | FieldValidationException | RuleValidationException be) {
            throw be;
        } catch (DataAccessException dae) {
            throw new BusinessException("Erro de integridade ao atualizar " + getEntityName(), dae);
        } catch (Exception e) {
            throw new BusinessException("Erro inesperado ao processar atualização em " + getEntityName(), e);
        }
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        try {
            validation.validateDelete(id);
            E entity = findByIdActive(id);
            beforeDelete(entity);
            entity.setAtivo(false); // CORRIGIDO: era true (bug de soft-delete)
            repository.save(entity);
            afterDelete(entity);
        } catch (BusinessException | FieldValidationException | RuleValidationException be) {
            throw be;
        } catch (Exception e) {
            throw new BusinessException("Não foi possível excluir o registro em " + getEntityName(), e);
        }
    }

    protected String getEntityName() {
        return this.getClass().getSimpleName().replace("Service", "");
    }

    // Hooks — sobrescreva nos serviços especializados quando necessário
    protected void beforeInsert(E entity) {}
    protected void afterInsert(E entity, E original) {}
    protected void beforeUpdate(E entity) {}
    protected void afterUpdate(E entity, E original) {}
    protected void beforeDelete(E entity) {}
    protected void afterDelete(E entity) {}
}
