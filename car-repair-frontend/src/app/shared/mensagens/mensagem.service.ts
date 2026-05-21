import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

export type TipoMensagem = 'sucesso' | 'erro' | 'aviso' | 'info';

export interface MensagemApp {
  id: number;
  tipo: TipoMensagem;
  texto: string;
}

@Injectable({ providedIn: 'root' })
export class MensagemService {
  private static readonly TEMPO_MS = 5000;
  private readonly subject = new BehaviorSubject<MensagemApp[]>([]);
  readonly mensagens$ = this.subject.asObservable();
  private seq = 1;

  sucesso(texto: string): void { this.adicionar('sucesso', texto); }
  erro(texto: string): void    { this.adicionar('erro', texto); }
  aviso(texto: string): void   { this.adicionar('aviso', texto); }
  info(texto: string): void    { this.adicionar('info', texto); }

  remover(id: number): void {
    this.subject.next(this.subject.value.filter((m) => m.id !== id));
  }

  private adicionar(tipo: TipoMensagem, texto: string): void {
    const id = this.seq++;
    this.subject.next([...this.subject.value, { id, tipo, texto }]);
    setTimeout(() => this.remover(id), MensagemService.TEMPO_MS);
  }
}
