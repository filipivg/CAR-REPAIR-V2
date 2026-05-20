package br.edu.senai.fatesg.ads3.car_repair.business.veiculos;

import br.edu.senai.fatesg.ads3.car_repair.core.controllers.GenericController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/veiculos")
public class VeiculoController extends GenericController<
        VeiculoModel,
        VeiculoDTO,
        VeiculoService,
        VeiculoMapper> {

    @Autowired
    private VeiculoService veiculoService;

    @Autowired
    private VeiculoMapper veiculoMapper;

    /** Endpoint extra: listar veículos por cliente */
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<Page<VeiculoDTO>> findByCliente(
            @PathVariable UUID clienteId, Pageable pageable) {
        Page<VeiculoModel> veiculos = veiculoService.findByCliente(clienteId, pageable);
        return ResponseEntity.ok(veiculoMapper.toDtoPage(veiculos));
    }
}
