package br.edu.senai.fatesg.ads3.car_repair.business.veiculos;

import br.edu.senai.fatesg.ads3.car_repair.core.repositories.IGenericRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface IVeiculoRepository extends IGenericRepository<VeiculoModel> {

    Optional<VeiculoModel> findByPlacaAndAtivoTrue(String placa);

    boolean existsByPlacaAndAtivoTrue(String placa);

    Page<VeiculoModel> findAllByClienteIdAndAtivoTrue(UUID clienteId, Pageable pageable);
}
