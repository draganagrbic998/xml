import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { OSNOVA, ZALBA } from 'src/app/constants/namespaces';
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
      zalbeDTO.push({
        tipZalbe: zalbe.item(i).getElementsByTagNameNS(ZALBA, 'tipZalbe')[0].textContent,
        broj: +zalbe.item(i).getElementsByTagNameNS(OSNOVA, 'broj')[0].textContent,
        datum: zalbe.item(i).getElementsByTagNameNS(OSNOVA, 'datum')[0].textContent,
        status: zalbe.item(i).getElementsByTagNameNS(ZALBA, 'status')[0].textContent
      });
    }

    return zalbeDTO;
  }

  list(): Observable<ZalbaDTO[]>{
    return this.http.get<string>(this.API_ZALBE, {responseType: 'text' as 'json'}).pipe(
      map((xml: string) => this.xmlToZalbe(xml))
    );
  }

  view(broj: number): Observable<string>{
    return this.http.get<string>(`${this.API_ZALBE}/${broj}`, {responseType: 'text' as 'json'});
  }

}
