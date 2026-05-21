package br.edu.senai.fatesg.ads3.car_repair.core.controllers;

import br.edu.senai.fatesg.ads3.car_repair.core.domains.BaseModel;
import br.edu.senai.fatesg.ads3.car_repair.core.dtos.BaseDTO;
import br.edu.senai.fatesg.ads3.car_repair.core.helpers.IGenericMapper;
import br.edu.senai.fatesg.ads3.car_repair.core.services.IGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller genérico que fornece CRUD completo.
 *
 * @param <E> Entity (herda BaseModel)
 * @param <D> DTO    (herda BaseDTO)
 * @param <S> Service especializado
 * @param <M> Mapper especializado
 */
public abstract class GenericController<
        E extends BaseModel,
        D extends BaseDTO,
        S extends IGenericService<E, ?, ?>,
        M extends IGenericMapper<E, D>> {

    @Autowired
    protected S service;

    @Autowired
    protected M mapper;

    @GetMapping("/{id}")
    public ResponseEntity<D> findById(@PathVariable UUID id) {
        E entity = service.findByIdActive(id);
        return ResponseEntity.ok(mapper.toDto(entity));
    }

    @GetMapping
    public ResponseEntity<Page<D>> findAll(Pageable pageable) {
        Page<E> entities = service.findAllActive(pageable);
        return ResponseEntity.ok(mapper.toDtoPage(entities));
    }

    @PostMapping
    public ResponseEntity<D> insert(@RequestBody D dto) {
        E entity = mapper.toEntity(dto);
        E saved = service.insert(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<D> update(@PathVariable UUID id, @RequestBody D dto) {
        E entity = mapper.toEntity(dto);
        entity.setId(id);
        E updated = service.update(entity);
        return ResponseEntity.ok(mapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.ok("Registro removido com sucesso.");
    }
}
