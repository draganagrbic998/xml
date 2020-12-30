import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { OBAVESTENJE, OSNOVA } from 'src/app/constants/namespaces';
import { Obavestenje } from 'src/app/models/obavestenje';
import { ObavestenjeDTO } from 'src/app/models/obavestenjeDTO';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ObavestenjeService {

  constructor(
    private http: HttpClient
  ) { }

  private readonly API_OBAVESTENJA = `${environment.baseUrl}/${environment.apiObavestenja}`;

  private dateToString(date: Date): string {
    return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
  }

  private obavestenjeToXml(obavestenje: Obavestenje): string{

    return `
      <obavestenje:Obavestenje xmlns="${OSNOVA}"
      xmlns:obavestenje="${OBAVESTENJE}">
        ${obavestenje.detalji}
        <obavestenje:Uvid>
          <obavestenje:datumUvida>${this.dateToString(obavestenje.datumUvida)}</obavestenje:datumUvida>
          <obavestenje:pocetak>${obavestenje.pocetak}</obavestenje:pocetak>
          <obavestenje:kraj>${obavestenje.kraj}</obavestenje:kraj>
          <obavestenje:kancelarija>${obavestenje.kancelarija}</obavestenje:kancelarija>
        </obavestenje:Uvid>
        <obavestenje:kopija>${obavestenje.kopija}</obavestenje:kopija>
      </obavestenje:Obavestenje>
    `;

  }

  private xmlToObavestenja(xml: string): ObavestenjeDTO[]{
    const parser = new DOMParser();
    const document = parser.parseFromString(xml, 'text/xml').getElementsByTagNameNS(OBAVESTENJE, 'Obavestenje');
    const obavestenja: ObavestenjeDTO[] = [];

    for (const key of Object.keys(document)){
      obavestenja.push({
        broj: document[key].getElementsByTagNameNS(OSNOVA, 'broj')[0].textContent,
        datum: document[key].getElementsByTagNameNS(OSNOVA, 'datum')[0].textContent,
        datumZahteva: document[key].getElementsByTagNameNS(OBAVESTENJE, 'datumZahteva')[0].textContent,
      });
    }

    return obavestenja;
  }

  save(brojZahteva: string, obavestenje: Obavestenje): Observable<null>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<null>(`${this.API_OBAVESTENJA}/${brojZahteva}`, this.obavestenjeToXml(obavestenje), options);
  }

  list(): Observable<ObavestenjeDTO[]>{
    return this.http.get<string>(this.API_OBAVESTENJA, {responseType: 'text' as 'json'}).pipe(
      map((xml: string) => this.xmlToObavestenja(xml))
    );
  }

  view(broj: string): Observable<string>{
    return this.http.get<string>(`${this.API_OBAVESTENJA}/${broj}`, {responseType: 'text' as 'json'});
  }

}
