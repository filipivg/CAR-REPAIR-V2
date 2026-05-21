package br.edu.senai.fatesg.ads3.car_repair.business.servicos;

import br.edu.senai.fatesg.ads3.car_repair.core.repositories.IGenericRepository;

import java.util.Optional;

public interface IServicoRepository extends IGenericRepository<ServicoModel> {

    Optional<ServicoModel> findByNomeIgnoreCaseAndAtivoTrue(String nome);

    boolean existsByNomeIgnoreCaseAndAtivoTrue(String nome);
}
