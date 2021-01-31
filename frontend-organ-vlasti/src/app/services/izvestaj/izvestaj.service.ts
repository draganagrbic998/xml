import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { OSNOVA, IZVESTAJ } from 'src/app/constants/namespaces';
import { IzvestajDTO } from 'src/app/models/izvestajDTO';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class IzvestajService {

  constructor(
    private http: HttpClient
  ) { }

  private readonly API_IZVESTAJI = `${environment.baseUrl}/${environment.apiIzvestaji}`;

  private xmlToIzvestaji(xml: string): IzvestajDTO[]{
    const parser = new DOMParser();
    const izvestaji = parser.parseFromString(xml, 'text/xml').getElementsByTagNameNS(IZVESTAJ, 'Izvestaj');
    const izvestajiDTO: IzvestajDTO[] = [];

    for (let i = 0; i < izvestaji.length; ++i){
      izvestajiDTO.push({
        broj: +izvestaji.item(i).getElementsByTagNameNS(OSNOVA, 'broj')[0].textContent,
        datum: izvestaji.item(i).getElementsByTagNameNS(OSNOVA, 'datum')[0].textContent,
        godina: izvestaji.item(i).getElementsByTagNameNS(IZVESTAJ, 'godina')[0].textContent
      });
    }

    return izvestajiDTO;
  }

  add(godina: string): Observable<null>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<null>(`${this.API_IZVESTAJI}/${godina}`, options);
  }

  findAll(): Observable<IzvestajDTO[]>{
    return this.http.get<string>(this.API_IZVESTAJI, {responseType: 'text' as 'json'}).pipe(
      map((xml: string) => this.xmlToIzvestaji(xml))
    );
  }

  find(broj: number, format: string): Observable<string>{
    let headers = new HttpHeaders();
    headers = headers.set('Accept', format === 'text' ? 'text/html' : 'application/pdf');
    return this.http.get<string>(`${this.API_IZVESTAJI}/${broj}`, {responseType: format as 'json', headers});
  }

  obicnaPretraga(pretraga: string): Observable<IzvestajDTO[]>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml'), responseType: 'text' as 'json' };
    return this.http.post<string>(`${this.API_IZVESTAJI}/obicna_pretraga`, pretraga, options).pipe(
      map((xml: string) => this.xmlToIzvestaji(xml)));
  }

  naprednaPretraga(pretraga: string): Observable<IzvestajDTO[]>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml'), responseType: 'text' as 'json' };
    return this.http.post<string>(`${this.API_IZVESTAJI}/napredna_pretraga`, pretraga, options).pipe(
      map((xml: string) => this.xmlToIzvestaji(xml))
    );
  }

}
