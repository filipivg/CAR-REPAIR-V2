package br.edu.senai.fatesg.ads3.car_repair.core.helpers;

import br.edu.senai.fatesg.ads3.car_repair.core.domains.BaseModel;
import br.edu.senai.fatesg.ads3.car_repair.core.dtos.BaseDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public interface IGenericMapper<E extends BaseModel, D extends BaseDTO> {

    D toDto(E entity);

    E toEntity(D dto);

    default List<D> toDtoList(List<E> entities) {
        if (entities == null) return null;
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

    default Page<D> toDtoPage(Page<E> page) {
        if (page == null) return null;
        return page.map(this::toDto);
    }
}
