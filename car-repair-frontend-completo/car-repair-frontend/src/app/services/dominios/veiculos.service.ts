import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiBaseService } from '../../core/http/api-base.service';
import { Veiculo } from '../../modelos/modelos';

@Injectable({ providedIn: 'root' })
export class VeiculosService extends ApiBaseService {
  private readonly ep = 'veiculos';
  constructor(http: HttpClient) { super(http); }

  listar(): Observable<Veiculo[]> {
    return this.getPaged<Veiculo>(this.ep);
  }

  listarPorCliente(clienteId: string): Observable<Veiculo[]> {
    return this.getPaged<Veiculo>(`${this.ep}/cliente/${clienteId}`);
  }

  obter(id: string): Observable<Veiculo> {
    return this.get<Veiculo>(`${this.ep}/${id}`);
  }

  adicionar(veiculo: Omit<Veiculo, 'id'>): Observable<Veiculo> {
    return this.post<Veiculo, Omit<Veiculo, 'id'>>(this.ep, veiculo);
  }

  atualizar(id: string, veiculo: Partial<Veiculo>): Observable<Veiculo> {
    return this.put<Veiculo, Partial<Veiculo>>(this.ep, id, veiculo);
  }

  remover(id: string): Observable<void> {
    return this.delete(this.ep, id);
  }
}
