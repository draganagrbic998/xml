import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { OSNOVA, ZALBA } from 'src/app/constants/namespaces';
import { Referenca } from 'src/app/models/referenca';
import { ZalbaCutanje } from 'src/app/models/zalba-cutanje';
import { ZalbaOdluka } from 'src/app/models/zalba-odluka';
import { ZalbaDTO } from 'src/app/models/zalbaDTO';
import { ZalbaPretraga } from 'src/app/models/zalbaPretraga';
import { environment } from 'src/environments/environment';
import { XonomyService } from '../xonomy/xonomy.service';

@Injectable({
  providedIn: 'root'
})
export class ZalbaService {

  constructor(
    private http: HttpClient,
    private xonomyService: XonomyService
  ) { }

  private readonly API_ZALBE = `${environment.baseUrl}/${environment.apiZalbe}`;

  private zalbaCutanjeToXml(zalba: ZalbaCutanje): string{
    let brojDokumenta;
    if (zalba.tipCutanja === 'nije postupio u celosti'){
      brojDokumenta = `<zalba:brojOdluke>${zalba.brojDokumenta}</zalba:brojOdluke>`;
    }
    else{
      brojDokumenta = `<zalba:brojZahteva>${zalba.brojDokumenta}</zalba:brojZahteva>`;
    }
    return `
      <zalba:Zalba
      xmlns="${OSNOVA}"
      xmlns:zalba="${ZALBA}">
        <zalba:tipCutanja>${zalba.tipCutanja}</zalba:tipCutanja>
        ${brojDokumenta}
        ${zalba.detalji}
        <lozinka>${zalba.lozinka}</lozinka>
      </zalba:Zalba>
    `;

  }

  private zalbaOdlukaToXml(zalba: ZalbaOdluka): string{
    return `
    <zalba:Zalba
    xmlns="${OSNOVA}"
    xmlns:zalba="${ZALBA}">
      <zalba:brojOdluke>${zalba.brojOdluke}</zalba:brojOdluke>
      ${zalba.detalji}
      <lozinka>${zalba.lozinka}</lozinka>
    </zalba:Zalba>
  `;
  }

  private pretragaToXml(pretraga: ZalbaPretraga): string{
    return `
      <pretraga>
        <operacija>${pretraga.operacija}</operacija>
        <datum>${pretraga.datum}</datum>
        <mesto>${pretraga.mesto}</mesto>
        <mestoIzdavanja>${pretraga.mestoIzdavanja}</mestoIzdavanja>
        <organVlasti>${pretraga.organVlasti}</organVlasti>
        <tip>${pretraga.tip}</tip>
        <stanje>${pretraga.stanje}</stanje>
      </pretraga>
    `;
  }

  private xmlToZalbe(xml: string): ZalbaDTO[]{
    const parser = new DOMParser();
    const zalbe = parser.parseFromString(xml, 'text/xml').getElementsByTagNameNS(ZALBA, 'Zalba');
    const zalbeDTO: ZalbaDTO[] = [];

    for (let i = 0; i < zalbe.length; ++i){
      const zalba = zalbe.item(i);
      const reference = zalba.getElementsByTagNameNS(OSNOVA, 'ref');
      const referenceDTO: Referenca[] = [];
      // tslint:disable-next-line: prefer-for-of
      for (let j = 0; j < reference.length; ++j){
        referenceDTO.push({
          tip: reference.item(j).getAttribute('tip'),
          broj: reference.item(j).textContent
        });
      }

      let datumProsledjivanja;
      if (zalba.getElementsByTagNameNS(ZALBA, 'datumProsledjivanja').length === 0){
        datumProsledjivanja = Date.now();
      }
      else{
        datumProsledjivanja = Date.parse(zalba.getElementsByTagNameNS(ZALBA, 'datumProsledjivanja')[0].textContent);
      }

      zalbeDTO.push({
        tipZalbe: zalba.getElementsByTagNameNS(ZALBA, 'tipZalbe')[0].textContent,
        broj: +zalba.getElementsByTagNameNS(OSNOVA, 'broj')[0].textContent,
        datum: zalba.getElementsByTagNameNS(OSNOVA, 'datum')[0].textContent,
        status: zalba.getElementsByTagNameNS(ZALBA, 'status')[0].textContent,
        datumProsledjivanja,
        reference: referenceDTO
      });
    }

    return zalbeDTO;
  }

  saveZalbaCutanje(zalba: ZalbaCutanje): Observable<null>{
    zalba.detalji = this.xonomyService.removeXmlSpace(zalba.detalji);
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<null>(this.API_ZALBE, this.zalbaCutanjeToXml(zalba), options);
  }

  saveZalbaOdluka(zalba: ZalbaOdluka): Observable<null>{
    zalba.detalji = this.xonomyService.removeXmlSpace(zalba.detalji);
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<null>(this.API_ZALBE, this.zalbaOdlukaToXml(zalba), options);
  }

  list(): Observable<ZalbaDTO[]>{
    return this.http.get<string>(this.API_ZALBE, {responseType: 'text' as 'json'}).pipe(
      map((xml: string) => this.xmlToZalbe(xml))
    );
  }

  view(broj: number): Observable<string>{
    return this.http.get<string>(`${this.API_ZALBE}/${broj}`, {responseType: 'text' as 'json'});
  }

  prosledi(broj: number): Observable<null>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<null>(`${this.API_ZALBE}/prosledi/${broj}`, options);
  }

  odustani(broj: number): Observable<null>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<null>(`${this.API_ZALBE}/odustani/${broj}`, options);
  }

  obustavi(broj: number): Observable<null>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<null>(`${this.API_ZALBE}/obustavi/${broj}`, options);
  }

  advancedSearch(pretraga: ZalbaPretraga): Observable<ZalbaDTO[]>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml'), responseType: 'text' as 'json' };
    return this.http.post<string>(`${this.API_ZALBE}/advanced_search`, this.pretragaToXml(pretraga), options).pipe(
      map((xml: string) => this.xmlToZalbe(xml))
    );
  }

}
