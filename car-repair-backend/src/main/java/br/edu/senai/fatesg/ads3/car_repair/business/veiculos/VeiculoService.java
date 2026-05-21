package br.edu.senai.fatesg.ads3.car_repair.business.veiculos;

import br.edu.senai.fatesg.ads3.car_repair.core.services.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VeiculoService extends GenericService<VeiculoModel, IVeiculoRepository, VeiculoValidation> {

    @Autowired
    private IVeiculoRepository veiculoRepository;

    /** Busca todos os veículos ativos de um cliente específico. */
    public Page<VeiculoModel> findByCliente(UUID clienteId, Pageable pageable) {
        return veiculoRepository.findAllByClienteIdAndAtivoTrue(clienteId, pageable);
    }
}
