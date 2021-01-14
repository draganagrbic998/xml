import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { ODLUKA, OSNOVA, XS, XSI } from 'src/app/constants/namespaces';
import { Obavestenje } from 'src/app/models/obavestenje';
import { OdlukaDTO } from 'src/app/models/odlukaDTO';
import { Odbijanje } from 'src/app/models/odbijanje';
import { environment } from 'src/environments/environment';
import { XonomyService } from '../xonomy/xonomy.service';
import { Referenca } from 'src/app/models/referenca';
import { MatTableDataSource } from '@angular/material/table';

@Injectable({
  providedIn: 'root'
})
export class OdlukaService {

  constructor(
    private http: HttpClient,
    private xonomyService: XonomyService
  ) { }

  private readonly API_ODLUKE = `${environment.baseUrl}/${environment.apiOdluke}`;

  private dateToString(date: Date): string {
    return `${date.getDate()}.${date.getMonth() + 1}.${date.getFullYear()}.`;
  }

  private odbijanjeToXml(brojZahteva: number, odbijanje: Odbijanje): string{

    return `
      <odluka:Odluka
      xmlns="${OSNOVA}"
      xmlns:odluka="${ODLUKA}"
      xmlns:xsi="${XSI}">
        ${odbijanje.detalji}
        <odluka:brojZahteva>${brojZahteva}</odluka:brojZahteva>
      </odluka:Odluka>
    `;

  }

  private obavestenjeToXml(brojZahteva: number, obavestenje: Obavestenje): string{

    return `
      <odluka:Odluka
      xmlns="${OSNOVA}"
      xmlns:odluka="${ODLUKA}"
      xmlns:xsi="${XSI}">
        ${obavestenje.detalji}
        <odluka:brojZahteva>${brojZahteva}</odluka:brojZahteva>
        <odluka:Uvid>
          <odluka:datumUvida>${this.dateToString(obavestenje.datumUvida)}</odluka:datumUvida>
            <odluka:pocetak>${obavestenje.pocetak}</odluka:pocetak>
            <odluka:kraj>${obavestenje.kraj}</odluka:kraj>
            <odluka:kancelarija>${obavestenje.kancelarija}</odluka:kancelarija>
        </odluka:Uvid>
        <odluka:kopija>${obavestenje.kopija}</odluka:kopija>
      </odluka:Odluka>
    `;

  }

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
          broj: reference.item(j).textContent
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

  saveObavestenje(brojZahteva: number, obavestenje: Obavestenje): Observable<null>{
    obavestenje.detalji = this.xonomyService.removeXmlSpace(obavestenje.detalji);
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<null>(this.API_ODLUKE, this.obavestenjeToXml(brojZahteva, obavestenje), options);
  }

  saveOdbijanje(brojZahteva: number, odbijanje: Odbijanje): Observable<null>{
    odbijanje.detalji = this.xonomyService.removeXmlSpace(odbijanje.detalji);
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<null>(this.API_ODLUKE, this.odbijanjeToXml(brojZahteva, odbijanje), options);
  }

  list(): Observable<OdlukaDTO[]>{
    return this.http.get<string>(this.API_ODLUKE, {responseType: 'text' as 'json'}).pipe(
      map((xml: string) => this.xmlToOdluke(xml))
    );
  }

  view(broj: number): Observable<string>{
    return this.http.get<string>(`${this.API_ODLUKE}/${broj}`, {responseType: 'text' as 'json'});
  }

}
