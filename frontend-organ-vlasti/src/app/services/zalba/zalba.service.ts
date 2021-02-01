import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { OSNOVA, ZALBA } from 'src/app/constants/namespaces';
import { Referenca } from 'src/app/models/referenca';
import { ZalbaDTO } from 'src/app/models/zalbaDTO';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ZalbaService {

  constructor(
    private http: HttpClient
  ) { }

  private readonly API_ZALBE = `${environment.baseUrl}/${environment.apiZalbe}`;

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
          broj: +reference.item(j).textContent
        });
      }

      zalbeDTO.push({
        tipZalbe: zalba.getElementsByTagNameNS(ZALBA, 'tipZalbe')[0].textContent,
        broj: +zalba.getElementsByTagNameNS(OSNOVA, 'broj')[0].textContent,
        datum: zalba.getElementsByTagNameNS(OSNOVA, 'datum')[0].textContent,
        status: zalba.getElementsByTagNameNS(ZALBA, 'status')[0].textContent,
        reference: referenceDTO
      });
    }

    return zalbeDTO;
  }

  findAll(): Observable<ZalbaDTO[]>{
    return this.http.get<string>(this.API_ZALBE, {responseType: 'text' as 'json'}).pipe(
      map((xml: string) => this.xmlToZalbe(xml))
    );
  }

  find(broj: string, format: string): Observable<string>{
    let headers = new HttpHeaders();
    headers = headers.set('Accept', format === 'text' ? 'text/html' : 'application/pdf');
    return this.http.get<string>(`${this.API_ZALBE}/${broj}`, {responseType: format as 'json', headers});
  }

  metadata(broj: number, format: string): Observable<string>{
    let headers = new HttpHeaders();
    headers = headers.set('Accept', format === 'rdf' ? 'text/xml' : 'application/json');
    return this.http.get<string>(`${this.API_ZALBE}/${broj}/metadata`, {responseType: 'text' as 'json', headers});
  }

  obicnaPretraga(pretraga: string): Observable<ZalbaDTO[]>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml'), responseType: 'text' as 'json' };
    return this.http.post<string>(`${this.API_ZALBE}/obicna_pretraga`, pretraga, options).pipe(
      map((xml: string) => this.xmlToZalbe(xml)));
  }

  naprednaPretraga(pretraga: string): Observable<ZalbaDTO[]>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml'), responseType: 'text' as 'json' };
    return this.http.post<string>(`${this.API_ZALBE}/napredna_pretraga`, pretraga, options).pipe(
      map((xml: string) => this.xmlToZalbe(xml))
    );
  }

}
