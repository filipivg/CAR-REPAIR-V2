package br.edu.senai.fatesg.ads3.car_repair.business.ordens;

import br.edu.senai.fatesg.ads3.car_repair.core.controllers.GenericController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/ordens")
public class OrdemController extends GenericController<
        OrdemModel,
        OrdemDTO,
        OrdemService,
        OrdemMapper> {

    @Autowired
    private OrdemService ordemService;

    @Autowired
    private OrdemMapper ordemMapper;

    /** Listar ordens por veículo */
    @GetMapping("/veiculo/{veiculoId}")
    public ResponseEntity<Page<OrdemDTO>> findByVeiculo(
            @PathVariable UUID veiculoId, Pageable pageable) {
        return ResponseEntity.ok(
                ordemMapper.toDtoPage(ordemService.findByVeiculo(veiculoId, pageable)));
    }

    /** Listar ordens por cliente */
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<Page<OrdemDTO>> findByCliente(
            @PathVariable UUID clienteId, Pageable pageable) {
        return ResponseEntity.ok(
                ordemMapper.toDtoPage(ordemService.findByCliente(clienteId, pageable)));
    }

    /** Listar ordens por status */
    @GetMapping("/status/{status}")
    public ResponseEntity<Page<OrdemDTO>> findByStatus(
            @PathVariable OrdemStatus status, Pageable pageable) {
        return ResponseEntity.ok(
                ordemMapper.toDtoPage(ordemService.findByStatus(status, pageable)));
    }

    /** Alterar status de uma OS */
    @PatchMapping("/{id}/status/{status}")
    public ResponseEntity<OrdemDTO> alterarStatus(
            @PathVariable UUID id,
            @PathVariable OrdemStatus status) {
        OrdemModel atualizada = ordemService.alterarStatus(id, status);
        return ResponseEntity.ok(ordemMapper.toDto(atualizada));
    }
}
