import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiBaseService } from '../../core/http/api-base.service';
import { Servico } from '../../modelos/modelos';

@Injectable({ providedIn: 'root' })
export class ServicosService extends ApiBaseService {
  private readonly ep = 'servicos';
  constructor(http: HttpClient) { super(http); }

  listar(): Observable<Servico[]> {
    return this.getPaged<Servico>(this.ep);
  }

  obter(id: string): Observable<Servico> {
    return this.get<Servico>(`${this.ep}/${id}`);
  }

  adicionar(servico: Omit<Servico, 'id'>): Observable<Servico> {
    return this.post<Servico, Omit<Servico, 'id'>>(this.ep, servico);
  }

  atualizar(id: string, servico: Partial<Servico>): Observable<Servico> {
    return this.put<Servico, Partial<Servico>>(this.ep, id, servico);
  }

  remover(id: string): Observable<void> {
    return this.delete(this.ep, id);
  }
}
