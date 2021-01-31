import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class OdlukaService {

  constructor(
    private http: HttpClient
  ) { }

  private readonly API_ODLUKE = `${environment.baseUrl}/${environment.apiOdluke}`;

  find(broj: number, format: string): Observable<string>{
    let headers = new HttpHeaders();
    headers = headers.set('Accept', format === 'text' ? 'text/html' : 'application/pdf');
    return this.http.get<string>(`${this.API_ODLUKE}/${broj}`, {responseType: format as 'json', headers});
  }

}
