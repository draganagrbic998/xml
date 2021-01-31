import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { ODGOVOR, OSNOVA } from 'src/app/constants/namespaces';
import { OdgovorDTO } from 'src/app/models/odgovorDTO';
import { Referenca } from 'src/app/models/referenca';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class OdgovorService {

  constructor(
    private http: HttpClient
  ) { }

  private readonly API_ODGOVORI = `${environment.baseUrl}/${environment.apiOdgovori}`;

  xmlToOdgovori(xml: string): OdgovorDTO[]{
    const parser = new DOMParser();
    const odgovori = parser.parseFromString(xml, 'text/xml').getElementsByTagNameNS(ODGOVOR, 'Odgovor');
    const odgovoriDTO: OdgovorDTO[] = [];

    for (let i = 0; i < odgovori.length; ++i){
      const odgovor = odgovori.item(i);
      const reference = odgovor.getElementsByTagNameNS(OSNOVA, 'ref');
      const referenceDTO: Referenca[] = [];
      // tslint:disable-next-line: prefer-for-of
      for (let j = 0; j < reference.length; ++j){
        referenceDTO.push({
          tip: reference.item(j).getAttribute('tip'),
          broj: reference.item(j).textContent
        });
      }

      odgovoriDTO.push({
        broj: +odgovori.item(i).getElementsByTagNameNS(OSNOVA, 'broj')[0].textContent,
        datum: odgovori.item(i).getElementsByTagNameNS(OSNOVA, 'datum')[0].textContent,
        datumZalbe: odgovori.item(i).getElementsByTagNameNS(ODGOVOR, 'datumZalbe')[0].textContent,
        reference: referenceDTO
      });
    }

    return odgovoriDTO;
  }

  findAll(): Observable<OdgovorDTO[]>{
    return this.http.get<string>(this.API_ODGOVORI, {responseType: 'text' as 'json'}).pipe(
      map((xml: string) => this.xmlToOdgovori(xml))
    );
  }

  find(broj: number, format: string): Observable<string>{
    let headers = new HttpHeaders();
    headers = headers.set('Accept', format === 'text' ? 'text/html' : 'application/pdf');
    return this.http.get<string>(`${this.API_ODGOVORI}/${broj}`, {responseType: format as 'json', headers});
  }

  obicnaPretraga(pretraga: string): Observable<OdgovorDTO[]>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml'), responseType: 'text' as 'json' };
    return this.http.post<string>(`${this.API_ODGOVORI}/obicna_pretraga`, pretraga, options).pipe(
      map((xml: string) => this.xmlToOdgovori(xml)));
  }

  naprednaPretraga(pretraga: string): Observable<OdgovorDTO[]>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml'), responseType: 'text' as 'json' };
    return this.http.post<string>(`${this.API_ODGOVORI}/napredna_pretraga`, pretraga, options).pipe(
      map((xml: string) => this.xmlToOdgovori(xml))
    );
  }

}
