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

  save(godina: string): Observable<null>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<null>(`${this.API_IZVESTAJI}/${godina}`, options);
  }

  list(): Observable<IzvestajDTO[]>{
    return this.http.get<string>(this.API_IZVESTAJI, {responseType: 'text' as 'json'}).pipe(
      map((xml: string) => this.xmlToIzvestaji(xml))
    );
  }

  view(broj: number): Observable<string>{
    return this.http.get<string>(`${this.API_IZVESTAJI}/${broj}`, {responseType: 'text' as 'json'});
  }

}
