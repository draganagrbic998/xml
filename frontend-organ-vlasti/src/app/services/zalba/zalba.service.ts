import { HttpClient } from '@angular/common/http';
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
          broj: reference.item(j).textContent
        });
      }

      zalbeDTO.push({
        tipZalbe: zalbe.item(i).getElementsByTagNameNS(ZALBA, 'tipZalbe')[0].textContent,
        broj: +zalbe.item(i).getElementsByTagNameNS(OSNOVA, 'broj')[0].textContent,
        datum: zalbe.item(i).getElementsByTagNameNS(OSNOVA, 'datum')[0].textContent,
        status: zalbe.item(i).getElementsByTagNameNS(ZALBA, 'status')[0].textContent,
        reference: referenceDTO
      });
    }

    return zalbeDTO;
  }

  list(): Observable<ZalbaDTO[]>{
    return this.http.get<string>(this.API_ZALBE, {responseType: 'text' as 'json'}).pipe(
      map((xml: string) => this.xmlToZalbe(xml))
    );
  }

  view(broj: string): Observable<string>{
    return this.http.get<string>(`${this.API_ZALBE}/${broj}`, {responseType: 'text' as 'json'});
  }

}
