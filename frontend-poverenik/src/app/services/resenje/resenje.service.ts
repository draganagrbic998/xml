import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
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

  private resenjeToXml(brojZalbe, resenje: Resenje): string{

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
        <resenje:brojZalbe>${brojZalbe}</resenje:brojZalbe>
        <resenje:status>${resenje.status}</resenje:status>
        <resenje:Odbrana>
          ${odbrana}
        </resenje:Odbrana>
        ${resenje.odluka}
      </resenje:Resenje>
    `;

  }

  private xmlToResenja(xml: string): ResenjeDTO[]{
    const parser = new DOMParser();
    const document = parser.parseFromString(xml, 'text/xml').getElementsByTagNameNS(RESENJE, 'Resenje');
    const resenja: ResenjeDTO[] = [];

    for (const key of Object.keys(document)){
      resenja.push({
        broj: document[key].getElementsByTagNameNS(OSNOVA, 'broj')[0].textContent,
        datum: document[key].getElementsByTagNameNS(OSNOVA, 'datum')[0].textContent,
        status: document[key].getElementsByTagNameNS(RESENJE, 'status')[0].textContent,
        organVlasti: document[key].getElementsByTagNameNS(OSNOVA, 'naziv')[0].textContent,
      });
    }

    return resenja;
  }

  save(brojZalbe: string, resenje: Resenje): Observable<null>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<null>(this.API_RESENJA, this.resenjeToXml(brojZalbe, resenje), options);
  }

  list(): Observable<ResenjeDTO[]>{
    return this.http.get<string>(this.API_RESENJA, {responseType: 'text' as 'json'}).pipe(
      map((xml: string) => this.xmlToResenja(xml))
    );
  }

  view(broj: string): Observable<string>{
    return this.http.get<string>(`${this.API_RESENJA}/${broj}`, {responseType: 'text' as 'json'});
  }

}
