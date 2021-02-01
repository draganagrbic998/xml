import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { ODLUKA, OSNOVA } from 'src/app/constants/namespaces';
import { Obavestenje } from 'src/app/models/obavestenje';
import { OdlukaDTO } from 'src/app/models/odlukaDTO';
import { Odbijanje } from 'src/app/models/odbijanje';
import { environment } from 'src/environments/environment';
import { XonomyService } from '../xonomy/xonomy.service';
import { Referenca } from 'src/app/models/referenca';
import { dateToString } from '../utils';

@Injectable({
  providedIn: 'root'
})
export class OdlukaService {

  constructor(
    private http: HttpClient,
    private xonomyService: XonomyService
  ) { }

  private readonly API_ODLUKE = `${environment.baseUrl}/${environment.apiOdluke}`;

  private xmlToOdluke(xml: string): OdlukaDTO[]{
    const parser = new DOMParser();
    const odluke = parser.parseFromString(xml, 'text/xml').getElementsByTagNameNS(ODLUKA, 'Odluka');
    const odlukeDTO: OdlukaDTO[] = [];

    for (let i = 0; i < odluke.length; ++i){
      const odluka = odluke.item(i);
      const reference = odluka.getElementsByTagNameNS(OSNOVA, 'ref');
      const referenceDTO: Referenca[] = [];
      // tslint:disable-next-line: prefer-for-of
      for (let j = 0; j < reference.length; ++j){
        referenceDTO.push({
          tip: reference.item(j).getAttribute('tip'),
          broj: +reference.item(j).textContent
        });
      }

      odlukeDTO.push({
        tipOdluke: odluka.getElementsByTagNameNS(ODLUKA, 'tipOdluke')[0].textContent,
        broj: +odluka.getElementsByTagNameNS(OSNOVA, 'broj')[0].textContent,
        datum: odluka.getElementsByTagNameNS(OSNOVA, 'datum')[0].textContent,
        datumZahteva: odluka.getElementsByTagNameNS(ODLUKA, 'datumZahteva')[0].textContent,
        reference: referenceDTO
      });
    }

    return odlukeDTO;
  }

  private obavestenjeToXml(brojZahteva: number, obavestenje: Obavestenje): string{

    return `
      <odluka:Odluka
      xmlns="${OSNOVA}"
      xmlns:odluka="${ODLUKA}">
        ${obavestenje.detalji}
        <odluka:brojZahteva>${brojZahteva}</odluka:brojZahteva>
        <odluka:Uvid>
          <odluka:datumUvida>${dateToString(obavestenje.datumUvida)}</odluka:datumUvida>
            <odluka:pocetak>${obavestenje.pocetak}</odluka:pocetak>
            <odluka:kraj>${obavestenje.kraj}</odluka:kraj>
            <odluka:kancelarija>${obavestenje.kancelarija}</odluka:kancelarija>
        </odluka:Uvid>
        <odluka:kopija>${obavestenje.kopija}</odluka:kopija>
      </odluka:Odluka>
    `;

  }

  private odbijanjeToXml(brojZahteva: number, odbijanje: Odbijanje): string{

    return `
      <odluka:Odluka
      xmlns="${OSNOVA}"
      xmlns:odluka="${ODLUKA}">
        ${odbijanje.detalji}
        <odluka:brojZahteva>${brojZahteva}</odluka:brojZahteva>
      </odluka:Odluka>
    `;

  }

  addObavestenje(brojZahteva: number, obavestenje: Obavestenje): Observable<null>{
    obavestenje.detalji = this.xonomyService.removeXmlSpace(obavestenje.detalji);
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<null>(this.API_ODLUKE, this.obavestenjeToXml(brojZahteva, obavestenje), options);
  }

  addOdbijanje(brojZahteva: number, odbijanje: Odbijanje): Observable<null>{
    odbijanje.detalji = this.xonomyService.removeXmlSpace(odbijanje.detalji);
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<null>(this.API_ODLUKE, this.odbijanjeToXml(brojZahteva, odbijanje), options);
  }

  findAll(): Observable<OdlukaDTO[]>{
    return this.http.get<string>(this.API_ODLUKE, {responseType: 'text' as 'json'}).pipe(
      map((xml: string) => this.xmlToOdluke(xml))
    );
  }

  find(broj: number, format: string): Observable<string>{
    let headers = new HttpHeaders();
    headers = headers.set('Accept', format === 'text' ? 'text/html' : 'application/pdf');
    return this.http.get<string>(`${this.API_ODLUKE}/${broj}`, {responseType: format as 'json', headers});
  }

  metadata(broj: number, format: string): Observable<string>{
    let headers = new HttpHeaders();
    headers = headers.set('Accept', format === 'rdf' ? 'text/xml' : 'application/json');
    return this.http.get<string>(`${this.API_ODLUKE}/${broj}/metadata`, {responseType: 'text' as 'json', headers});
  }

  obicnaPretraga(pretraga: string): Observable<OdlukaDTO[]>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml'), responseType: 'text' as 'json' };
    return this.http.post<string>(`${this.API_ODLUKE}/obicna_pretraga`, pretraga, options).pipe(
      map((xml: string) => this.xmlToOdluke(xml)));
  }

  naprednaPretraga(pretraga: string): Observable<OdlukaDTO[]>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml'), responseType: 'text' as 'json' };
    return this.http.post<string>(`${this.API_ODLUKE}/napredna_pretraga`, pretraga, options).pipe(
      map((xml: string) => this.xmlToOdluke(xml))
    );
  }

}
