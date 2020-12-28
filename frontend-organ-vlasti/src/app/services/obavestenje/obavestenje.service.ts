import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
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

  save(brojZahteva: string, obavestenje: Obavestenje): Observable<null>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<null>(`${this.API_OBAVESTENJA}/${brojZahteva}`, this.obavestenjeToXml(obavestenje), options);
  }

  list(): Observable<ObavestenjeDTO[]>{
    return this.http.get<ObavestenjeDTO[]>(this.API_OBAVESTENJA);
  }

}
