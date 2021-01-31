import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ZahtevService {

  constructor(
    private http: HttpClient
  ) { }

  private readonly API_ZAHTEVI = `${environment.baseUrl}/${environment.apiZahtevi}`;

  find(broj: number, format: string): Observable<string>{
    let headers = new HttpHeaders();
    headers = headers.set('Accept', format === 'text' ? 'text/html' : 'application/pdf');
    return this.http.get<string>(`${this.API_ZAHTEVI}/${broj}`, {responseType: format as 'json', headers});
  }

}
