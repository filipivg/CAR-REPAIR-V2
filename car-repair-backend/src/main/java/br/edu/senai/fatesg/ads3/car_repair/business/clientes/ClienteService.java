package br.edu.senai.fatesg.ads3.car_repair.business.clientes;

import br.edu.senai.fatesg.ads3.car_repair.core.services.GenericService;
import org.springframework.stereotype.Service;

@Service
public class ClienteService extends GenericService<ClienteModel, IClienteRepository, ClienteValidation> {
    // Toda a lógica genérica de CRUD já está em GenericService.
    // Sobrescreva os hooks (beforeInsert, afterInsert, etc.) se precisar
    // de comportamento adicional específico de Cliente.
}
