import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Page } from '../../modelos/modelos';

@Injectable({ providedIn: 'root' })
export class ApiBaseService {
  private readonly baseUrl = environment.apiBaseUrl;

  constructor(protected readonly http: HttpClient) {}

  protected get<T>(endpoint: string): Observable<T> {
    return this.http.get<T>(`${this.baseUrl}/${endpoint}`);
  }

  /** Busca paginada — retorna apenas o array .content da Page */
  protected getPaged<T>(endpoint: string, page = 0, size = 100): Observable<T[]> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    return this.http
      .get<Page<T>>(`${this.baseUrl}/${endpoint}`, { params })
      .pipe(map((p) => p.content));
  }

  protected post<T, TPayload>(endpoint: string, payload: TPayload): Observable<T> {
    return this.http.post<T>(`${this.baseUrl}/${endpoint}`, payload);
  }

  protected put<T, TPayload>(endpoint: string, id: string, payload: TPayload): Observable<T> {
    return this.http.put<T>(`${this.baseUrl}/${endpoint}/${id}`, payload);
  }

  protected patch<T>(endpoint: string, id: string, extra = ''): Observable<T> {
    return this.http.patch<T>(`${this.baseUrl}/${endpoint}/${id}${extra}`, {});
  }

  protected delete(endpoint: string, id: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${endpoint}/${id}`);
  }
}
