// ─── clientes.service.ts ────────────────────────────────────
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiBaseService } from '../../core/http/api-base.service';
import { Cliente } from '../../modelos/modelos';

@Injectable({ providedIn: 'root' })
export class ClientesService extends ApiBaseService {
  private readonly ep = 'clientes';
  constructor(http: HttpClient) { super(http); }

  listar(): Observable<Cliente[]> {
    return this.getPaged<Cliente>(this.ep);
  }

  obter(id: string): Observable<Cliente> {
    return this.get<Cliente>(`${this.ep}/${id}`);
  }

  adicionar(cliente: Omit<Cliente, 'id'>): Observable<Cliente> {
    return this.post<Cliente, Omit<Cliente, 'id'>>(this.ep, cliente);
  }

  atualizar(id: string, cliente: Partial<Cliente>): Observable<Cliente> {
    return this.put<Cliente, Partial<Cliente>>(this.ep, id, cliente);
  }

  remover(id: string): Observable<void> {
    return this.delete(this.ep, id);
  }
}
