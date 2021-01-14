import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Zahtev } from 'src/app/models/zahtev';
import { environment } from 'src/environments/environment';
import { OSNOVA, ZAHTEV } from 'src/app/constants/namespaces';
import { ZahtevDTO } from 'src/app/models/zahtevDTO';
import { map } from 'rxjs/operators';
import { XonomyService } from '../xonomy/xonomy.service';
import { ZahtevPretraga } from 'src/app/models/zahtevPretraga';
import { Referenca } from 'src/app/models/referenca';
import { MatTableDataSource } from '@angular/material/table';

@Injectable({
  providedIn: 'root'
})
export class ZahtevService {

  constructor(
    private http: HttpClient,
    private xonomyService: XonomyService
  ) { }

  private readonly API_ZAHTEVI = `${environment.baseUrl}/${environment.apiZahtevi}`;

  private dateToString(date: Date): string {
    if (!date){
      return '';
    }
    return `${date.getDate()}.${date.getMonth() + 1}.${date.getFullYear()}.`;
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

  private pretragaToXml(pretraga: ZahtevPretraga): string{
    return `
      <pretraga>
        <datum>${this.dateToString(pretraga.datum)}</datum>
        <mesto>${pretraga.mesto}</mesto>
        <tip>${pretraga.tip}</tip>
        <stanje>${pretraga.stanje}</stanje>
      </pretraga>
    `;
  }

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

  save(zahtev: Zahtev): Observable<null>{
    zahtev.detalji = this.xonomyService.removeXmlSpace(zahtev.detalji);
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<null>(this.API_ZAHTEVI, this.zahtevToXml(zahtev), options);
  }

  list(): Observable<ZahtevDTO[]>{
    return this.http.get<string>(this.API_ZAHTEVI, {responseType: 'text' as 'json'}).pipe(
      map((xml: string) => this.xmlToZahtevi(xml))
    );
  }

  view(broj: number): Observable<string>{
    return this.http.get<string>(`${this.API_ZAHTEVI}/${broj}`, {responseType: 'text' as 'json'});
  }

  advancedSearch(pretraga: ZahtevPretraga): Observable<ZahtevDTO[]>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml'), responseType: 'text' as 'json' };
    return this.http.post<string>(`${this.API_ZAHTEVI}/advanced_search`, this.pretragaToXml(pretraga), options).pipe(
      map((xml: string) => this.xmlToZahtevi(xml))
    );
  }

}
