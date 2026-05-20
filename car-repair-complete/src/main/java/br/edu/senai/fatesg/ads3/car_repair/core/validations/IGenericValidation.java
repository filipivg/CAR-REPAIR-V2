package br.edu.senai.fatesg.ads3.car_repair.core.validations;

import br.edu.senai.fatesg.ads3.car_repair.core.domains.BaseModel;
import br.edu.senai.fatesg.ads3.car_repair.core.repositories.IGenericRepository;

import java.util.UUID;

public interface IGenericValidation<E extends BaseModel, R extends IGenericRepository<E>> {

    void validateFields(E entity);

    default void validateFieldsInsert(E entity) {}

    default void validateFieldsUpdate(E entity) {}

    default void validateInsert(E entity) {}

    default void validateUpdate(E entity) {}

    default void validateDelete(UUID id) {}
}
