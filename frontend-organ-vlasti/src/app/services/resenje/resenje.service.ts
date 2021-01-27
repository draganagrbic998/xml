import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { OSNOVA, RESENJE } from 'src/app/constants/namespaces';
import { ResenjeDTO } from 'src/app/models/resenjeDTO';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ResenjeService {

  constructor(
    private http: HttpClient
  ) { }

  private readonly API_RESENJA = `${environment.baseUrl}/${environment.apiResenja}`;

  private xmlToResenja(xml: string): ResenjeDTO[]{
    const parser = new DOMParser();
    const document = parser.parseFromString(xml, 'text/xml').getElementsByTagNameNS(RESENJE, 'Resenje');
    const resenja: ResenjeDTO[] = [];

    for (const key of Object.keys(document)){
      resenja.push({
        broj: +document[key].getElementsByTagNameNS(OSNOVA, 'broj')[0].textContent,
        datum: document[key].getElementsByTagNameNS(OSNOVA, 'datum')[0].textContent,
        status: document[key].getElementsByTagNameNS(RESENJE, 'status')[0].textContent
      });
    }

    return resenja;
  }

  list(): Observable<ResenjeDTO[]>{
    return this.http.get<string>(this.API_RESENJA, {responseType: 'text' as 'json'}).pipe(
      map((xml: string) => this.xmlToResenja(xml))
    );
  }

  view(broj: number): Observable<string>{
    return this.http.get<string>(`${this.API_RESENJA}/${broj}`, {responseType: 'text' as 'json'});
  }

  obicnaPretraga(pretraga: string): Observable<ResenjeDTO[]>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml'), responseType: 'text' as 'json' };
    return this.http.post<string>(`${this.API_RESENJA}/obicna_pretraga`, pretraga, options).pipe(
      map((xml: string) => this.xmlToResenja(xml)));
  }

  naprednaPretraga(pretraga: string): Observable<ResenjeDTO[]>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml'), responseType: 'text' as 'json' };
    return this.http.post<string>(`${this.API_RESENJA}/napredna_pretraga`, pretraga, options).pipe(
      map((xml: string) => this.xmlToResenja(xml))
    );
  }

}
