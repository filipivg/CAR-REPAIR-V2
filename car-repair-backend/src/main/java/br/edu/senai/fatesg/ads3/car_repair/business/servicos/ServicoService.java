package br.edu.senai.fatesg.ads3.car_repair.business.servicos;

import br.edu.senai.fatesg.ads3.car_repair.core.services.GenericService;
import org.springframework.stereotype.Service;

@Service
public class ServicoService extends GenericService<ServicoModel, IServicoRepository, ServicoValidation> {
}
