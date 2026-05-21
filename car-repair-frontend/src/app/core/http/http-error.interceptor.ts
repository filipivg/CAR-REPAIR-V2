import { Injectable } from '@angular/core';
import {
  HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpErrorResponse
} from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
import { MensagemService } from '../../shared/mensagens/mensagem.service';

@Injectable()
export class HttpErrorInterceptor implements HttpInterceptor {
  constructor(private readonly mensagemService: MensagemService) {}

  intercept(req: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const token = localStorage.getItem('authToken');
    let headers = req.headers;
    if (!headers.has('Content-Type')) {
      headers = headers.set('Content-Type', 'application/json');
    }
    if (token) {
      headers = headers.set('Authorization', `Bearer ${token}`);
    }
    const cloned = req.clone({ headers });

    return next.handle(cloned).pipe(
      catchError((err: unknown) => {
        const httpErr = err as HttpErrorResponse;
        this.mensagemService.erro(this.getFriendlyMessage(httpErr));
        return throwError(() => err);
      })
    );
  }

  private getFriendlyMessage(err: HttpErrorResponse): string {
    if (!err || err.status === 0) return 'Falha de rede. Verifique sua conexão e tente novamente.';
    if (err.status >= 500) return 'Erro no servidor. Tente novamente mais tarde.';
    if (err.status === 404) return 'Recurso não encontrado.';
    if (err.status === 401) return 'Sessão expirada. Faça login novamente.';
    if (err.status === 403) return 'Acesso negado a este recurso.';
    const msg = err.error?.message || err.error?.title || '';
    return msg || 'Ocorreu um erro. Tente novamente.';
  }
}
