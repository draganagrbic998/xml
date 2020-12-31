import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { ODGOVOR, OSNOVA, XSI } from 'src/app/constants/namespaces';
import { Obavestenje } from 'src/app/models/obavestenje';
import { OdgovorDTO } from 'src/app/models/odgovorDTO';
import { Odbijanje } from 'src/app/models/odbijanje';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class OdgovorService {

  constructor(
    private http: HttpClient
  ) { }

  private readonly API_ODGOVORI = `${environment.baseUrl}/${environment.apiOdgovori}`;

  private dateToString(date: Date): string {
    return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
  }

  private odbijanjeToXml(brojZahteva, odbijanje: Odbijanje): string{

    return `
      <odgovor:Odgovor xmlns="${OSNOVA}"
      xmlns:odgovor="${ODGOVOR}"
      xmlns:xsi="${XSI}"
      xsi:type="TOdbijanje">
        <odgovor:brojZahteva>${brojZahteva}</odgovor:brojZahteva>
        ${odbijanje.detalji}
      </odgovor:Odgovor>
    `;

  }

  private obavestenjeToXml(brojZahteva, obavestenje: Obavestenje): string{

    return `
      <odgovor:Odgovor xmlns="${OSNOVA}"
      xmlns:odgovor="${ODGOVOR}"
      xmlns:xsi="${XSI}"
      xsi:type="TObavestenje">
        <odgovor:brojZahteva>${brojZahteva}</odgovor:brojZahteva>
        ${obavestenje.detalji}
        <odgovor:Uvid>
          <odgovor:datumUvida>${this.dateToString(obavestenje.datumUvida)}</odgovor:datumUvida>
          <odgovor:pocetak>${obavestenje.pocetak}</odgovor:pocetak>
          <odgovor:kraj>${obavestenje.kraj}</odgovor:kraj>
          <odgovor:kancelarija>${obavestenje.kancelarija}</odgovor:kancelarija>
        </odgovor:Uvid>
        <odgovor:kopija>${obavestenje.kopija}</odgovor:kopija>
      </odgovor:Odgovor>
    `;

  }

  private xmlToOdgovori(xml: string): OdgovorDTO[]{
    const parser = new DOMParser();
    const document = parser.parseFromString(xml, 'text/xml').getElementsByTagNameNS(ODGOVOR, 'Odgovor');
    const odgovori: OdgovorDTO[] = [];

    for (const key of Object.keys(document)){
      odgovori.push({
        tipOdgovora: document[key].getElementsByTagNameNS(ODGOVOR, 'tipOdgovora')[0].textContent,
        broj: document[key].getElementsByTagNameNS(OSNOVA, 'broj')[0].textContent,
        datum: document[key].getElementsByTagNameNS(OSNOVA, 'datum')[0].textContent,
        datumZahteva: document[key].getElementsByTagNameNS(ODGOVOR, 'datumZahteva')[0].textContent,
      });
    }

    return odgovori;
  }

  saveObavestenje(brojZahteva: string, obavestenje: Obavestenje): Observable<null>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<null>(this.API_ODGOVORI, this.obavestenjeToXml(brojZahteva, obavestenje), options);
  }

  saveOdbijanje(brojZahteva: string, odbijanje: Odbijanje): Observable<null>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<null>(this.API_ODGOVORI, this.odbijanjeToXml(brojZahteva, odbijanje), options);
  }

  list(): Observable<OdgovorDTO[]>{
    return this.http.get<string>(this.API_ODGOVORI, {responseType: 'text' as 'json'}).pipe(
      map((xml: string) => this.xmlToOdgovori(xml))
    );
  }

  view(broj: string): Observable<string>{
    return this.http.get<string>(`${this.API_ODGOVORI}/${broj}`, {responseType: 'text' as 'json'});
  }

}
