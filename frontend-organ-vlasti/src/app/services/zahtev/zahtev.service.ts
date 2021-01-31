import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Zahtev } from 'src/app/models/zahtev';
import { environment } from 'src/environments/environment';
import { OSNOVA, ZAHTEV } from 'src/app/constants/namespaces';
import { ZahtevDTO } from 'src/app/models/zahtevDTO';
import { map } from 'rxjs/operators';
import { XonomyService } from '../xonomy/xonomy.service';
import { Referenca } from 'src/app/models/referenca';

@Injectable({
  providedIn: 'root'
})
export class ZahtevService {

  constructor(
    private http: HttpClient,
    private xonomyService: XonomyService
  ) { }

  private readonly API_ZAHTEVI = `${environment.baseUrl}/${environment.apiZahtevi}`;

  private xmlToZahtevi(xml: string): ZahtevDTO[]{
    const parser = new DOMParser();
    const zahtevi = parser.parseFromString(xml, 'text/xml').getElementsByTagNameNS(ZAHTEV, 'Zahtev');
    const zahteviDTO: ZahtevDTO[] = [];

    for (let i = 0; i < zahtevi.length; ++i){
      const zahtev = zahtevi.item(i);
      const reference = zahtev.getElementsByTagNameNS(OSNOVA, 'ref');
      const referenceDTO: Referenca[] = [];
      // tslint:disable-next-line: prefer-for-of
      for (let j = 0; j < reference.length; ++j){
        referenceDTO.push({
          tip: reference.item(j).getAttribute('tip'),
          broj: reference.item(j).textContent
        });
      }

      zahteviDTO.push({
        tipZahteva: zahtev.getElementsByTagNameNS(ZAHTEV, 'tipZahteva')[0].textContent,
        broj: +zahtev.getElementsByTagNameNS(OSNOVA, 'broj')[0].textContent,
        datum: zahtev.getElementsByTagNameNS(OSNOVA, 'datum')[0].textContent,
        status: zahtev.getElementsByTagNameNS(ZAHTEV, 'status')[0].textContent,
        reference: referenceDTO
      });
    }

    return zahteviDTO;
  }

  private zahtevToXml(zahtev: Zahtev): string{

    let xml = `
      ${zahtev.detalji}
      <zahtev:tipZahteva property="pred:tip" datatype="xs:string">${zahtev.tipZahteva}</zahtev:tipZahteva>
    `;
    if (zahtev.tipZahteva === 'dostava'){
      xml += `<zahtev:tipDostave>${zahtev.tipDostave}</zahtev:tipDostave>`;
    }
    if (zahtev.tipDostave === 'ostalo'){
      xml += `<zahtev:opisDostave>${zahtev.opisDostave}</zahtev:opisDostave>`;
    }

    return `
      <zahtev:Zahtev
      xmlns="${OSNOVA}"
      xmlns:zahtev="${ZAHTEV}">
        ${xml}
      </zahtev:Zahtev>
    `;

  }

  add(zahtev: Zahtev): Observable<null>{
    zahtev.detalji = this.xonomyService.removeXmlSpace(zahtev.detalji);
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<null>(this.API_ZAHTEVI, this.zahtevToXml(zahtev), options);
  }

  findAll(): Observable<ZahtevDTO[]>{
    return this.http.get<string>(this.API_ZAHTEVI, {responseType: 'text' as 'json'}).pipe(
      map((xml: string) => this.xmlToZahtevi(xml))
    );
  }

  find(broj: number, format: string): Observable<string>{
    let headers = new HttpHeaders();
    headers = headers.set('Accept', format === 'text' ? 'text/html' : 'application/pdf');
    return this.http.get<string>(`${this.API_ZAHTEVI}/${broj}`, {responseType: format as 'json', headers});
  }

  obicnaPretraga(pretraga: string): Observable<ZahtevDTO[]>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml'), responseType: 'text' as 'json' };
    return this.http.post<string>(`${this.API_ZAHTEVI}/obicna_pretraga`, pretraga, options).pipe(
      map((xml: string) => this.xmlToZahtevi(xml)));
  }

  naprednaPretraga(pretraga: string): Observable<ZahtevDTO[]>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml'), responseType: 'text' as 'json' };
    return this.http.post<string>(`${this.API_ZAHTEVI}/napredna_pretraga`, pretraga, options).pipe(
      map((xml: string) => this.xmlToZahtevi(xml))
    );
  }

}
