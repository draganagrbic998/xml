import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { OSNOVA, RESENJE } from 'src/app/constants/namespaces';
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

  private dateToString(date: Date): string {
    return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
  }

  private resenjeToXml(resenje: Resenje): string{

    let odbrana = `<resenje:datumSlanja>${this.dateToString(resenje.datumSlanja)}</resenje:datumSlanja>`;
    if (resenje.datumOdbrane && resenje.odgovorOdbrane){
      odbrana += `
        <resenje:datum>${this.dateToString(resenje.datumSlanja)}</resenje:datum>
        <resenje:odgovor>${resenje.odgovorOdbrane}</resenje:odgovor>
      `;
    }
    return `
      <resenje:Resenje xmlns="${OSNOVA}"
      xmlns:resenje="${RESENJE}">
        <resenje:status>${resenje.status}</resenje:status>
        ${odbrana}
        ${resenje.odluka}
      </resenje:Resenje>
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
