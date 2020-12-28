import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DOKUMENT_NAMESPACE, OSNOVA_NAMESPACE } from 'src/app/constants/namespaces';
import { Resenje } from 'src/app/models/resenje';
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

  private resenjeToXml(resenje: Resenje): string{

    let odbrana = '<datumSlanja>2020-12-12</datumSlanja>';
    if (resenje.datum && resenje.odgovor){
      odbrana += `
        <datum>2020-12-12</datum>
        <odgovor>${resenje.odgovor}</odgovor>
      `;
    }
    return `
    <Resenje xmlns="${DOKUMENT_NAMESPACE}"
    xmlns:osnova="${OSNOVA_NAMESPACE}">
      <status>${resenje.status}</status>
      ${resenje.odluka}
      ${odbrana}
      </Resenje>
    `;

  }

  save(brojZalbe: string, resenje: Resenje): Observable<null>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<null>(`${this.API_RESENJA}/${brojZalbe}`, this.resenjeToXml(resenje), options);
  }

  list(): Observable<ResenjeDTO[]>{
    return this.http.get<ResenjeDTO[]>(this.API_RESENJA);
  }

}
