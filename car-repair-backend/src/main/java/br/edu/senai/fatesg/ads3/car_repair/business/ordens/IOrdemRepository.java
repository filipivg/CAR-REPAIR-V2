package br.edu.senai.fatesg.ads3.car_repair.business.ordens;

import br.edu.senai.fatesg.ads3.car_repair.core.repositories.IGenericRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IOrdemRepository extends IGenericRepository<OrdemModel> {

    Page<OrdemModel> findAllByVeiculoIdAndAtivoTrue(UUID veiculoId, Pageable pageable);

    Page<OrdemModel> findAllByStatusAndAtivoTrue(OrdemStatus status, Pageable pageable);

    Page<OrdemModel> findAllByVeiculoClienteIdAndAtivoTrue(UUID clienteId, Pageable pageable);
}
