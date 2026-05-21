package br.edu.senai.fatesg.ads3.car_repair.business.ordens;

import br.edu.senai.fatesg.ads3.car_repair.core.exceptions.BusinessException;
import br.edu.senai.fatesg.ads3.car_repair.core.exceptions.RuleValidationException;
import br.edu.senai.fatesg.ads3.car_repair.core.services.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
public class OrdemService extends GenericService<OrdemModel, IOrdemRepository, OrdemValidation> {

    @Autowired
    private IOrdemRepository ordemRepository;

    public Page<OrdemModel> findByVeiculo(UUID veiculoId, Pageable pageable) {
        return ordemRepository.findAllByVeiculoIdAndAtivoTrue(veiculoId, pageable);
    }

    public Page<OrdemModel> findByCliente(UUID clienteId, Pageable pageable) {
        return ordemRepository.findAllByVeiculoClienteIdAndAtivoTrue(clienteId, pageable);
    }

    public Page<OrdemModel> findByStatus(OrdemStatus status, Pageable pageable) {
        return ordemRepository.findAllByStatusAndAtivoTrue(status, pageable);
    }

    /** Altera o status de uma OS e aplica regras de negócio (ex: seta dataConclusao). */
    @Transactional
    public OrdemModel alterarStatus(UUID id, OrdemStatus novoStatus) {
        OrdemModel ordem = findByIdActive(id);

        if (ordem.getStatus() == OrdemStatus.CONCLUIDA || ordem.getStatus() == OrdemStatus.CANCELADA) {
            throw new RuleValidationException("status",
                    "Não é possível alterar uma ordem " + ordem.getStatus().name().toLowerCase() + ".");
        }

        if (novoStatus == OrdemStatus.CONCLUIDA) {
            if (ordem.getStatus() != OrdemStatus.EM_ANDAMENTO) {
                throw new RuleValidationException("status",
                        "Só é possível concluir uma ordem que esteja em andamento.");
            }
            ordem.setDataConclusao(new Date());
            ordem.recalcularTotal();
        }

        if (novoStatus == OrdemStatus.CANCELADA) {
            try {
                validarCancelamento(ordem);
            } catch (BusinessException be) {
                throw be;
            }
        }

        ordem.setStatus(novoStatus);
        return repository.save(ordem);
    }

    private void validarCancelamento(OrdemModel ordem) {
        if (ordem.getStatus() == OrdemStatus.CONCLUIDA) {
            throw new BusinessException("Não é possível cancelar uma ordem já concluída.", HttpStatus.CONFLICT);
        }
    }

    @Override
    protected void beforeInsert(OrdemModel entity) {
        entity.recalcularTotal();
    }

    @Override
    protected void beforeUpdate(OrdemModel entity) {
        entity.recalcularTotal();
    }
}
