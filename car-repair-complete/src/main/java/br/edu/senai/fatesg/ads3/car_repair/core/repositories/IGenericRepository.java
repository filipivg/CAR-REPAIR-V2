package br.edu.senai.fatesg.ads3.car_repair.core.repositories;

import br.edu.senai.fatesg.ads3.car_repair.core.domains.BaseModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;
import java.util.UUID;

@NoRepositoryBean
public interface IGenericRepository<E extends BaseModel> extends JpaRepository<E, UUID> {
    Optional<E> findByIdAndAtivoTrue(UUID id);
    Page<E> findAllByAtivoTrue(Pageable pageable);
}
