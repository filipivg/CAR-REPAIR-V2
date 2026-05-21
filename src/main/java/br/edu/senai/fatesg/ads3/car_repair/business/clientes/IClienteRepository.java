package br.edu.senai.fatesg.ads3.car_repair.business.clientes;

import br.edu.senai.fatesg.ads3.car_repair.core.repositories.IGenericRepository;

import java.util.Optional;

public interface IClienteRepository extends IGenericRepository<ClienteModel> {

    Optional<ClienteModel> findByCpfAndAtivoTrue(String cpf);

    boolean existsByCpfAndAtivoTrue(String cpf);
}
