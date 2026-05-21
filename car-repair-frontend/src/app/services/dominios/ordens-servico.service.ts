import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiBaseService } from '../../core/http/api-base.service';
import { OrdemServico, OrdemStatus } from '../../modelos/modelos';

@Injectable({ providedIn: 'root' })
export class OrdensServicoService extends ApiBaseService {
  private readonly ep = 'ordens';
  constructor(http: HttpClient) { super(http); }

  listar(): Observable<OrdemServico[]> {
    return this.getPaged<OrdemServico>(this.ep);
  }

  listarPorVeiculo(veiculoId: string): Observable<OrdemServico[]> {
    return this.getPaged<OrdemServico>(`${this.ep}/veiculo/${veiculoId}`);
  }

  listarPorCliente(clienteId: string): Observable<OrdemServico[]> {
    return this.getPaged<OrdemServico>(`${this.ep}/cliente/${clienteId}`);
  }

  listarPorStatus(status: OrdemStatus): Observable<OrdemServico[]> {
    return this.getPaged<OrdemServico>(`${this.ep}/status/${status}`);
  }

  obter(id: string): Observable<OrdemServico> {
    return this.get<OrdemServico>(`${this.ep}/${id}`);
  }

  adicionar(ordem: Omit<OrdemServico, 'id'>): Observable<OrdemServico> {
    return this.post<OrdemServico, Omit<OrdemServico, 'id'>>(this.ep, ordem);
  }

  atualizar(id: string, ordem: Partial<OrdemServico>): Observable<OrdemServico> {
    return this.put<OrdemServico, Partial<OrdemServico>>(this.ep, id, ordem);
  }

  alterarStatus(id: string, status: OrdemStatus): Observable<OrdemServico> {
    return this.patch<OrdemServico>(this.ep, id, `/status/${status}`);
  }

  remover(id: string): Observable<void> {
    return this.delete(this.ep, id);
  }
}
