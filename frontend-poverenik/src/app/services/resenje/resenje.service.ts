import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { OSNOVA, RESENJE } from 'src/app/constants/namespaces';
import { Resenje } from 'src/app/models/resenje';
import { ResenjeDTO } from 'src/app/models/resenjeDTO';
import { environment } from 'src/environments/environment';
import { XonomyService } from '../xonomy/xonomy.service';

@Injectable({
  providedIn: 'root'
})
export class ResenjeService {

  constructor(
    private http: HttpClient,
    private xonomyService: XonomyService
  ) { }

  private readonly API_RESENJA = `${environment.baseUrl}/${environment.apiResenja}`;

  private resenjeToXml(brojZalbe, resenje: Resenje): string{
    return `
      <Resenje xmlns="${RESENJE}"
      xmlns:osnova="${OSNOVA}">
        <brojZalbe>${brojZalbe}</brojZalbe>
        <status>${resenje.status}</status>
        ${resenje.odluka}
      </Resenje>
    `;

  }

  private xmlToResenja(xml: string): ResenjeDTO[]{
    const parser = new DOMParser();
    const resenja = parser.parseFromString(xml, 'text/xml').getElementsByTagNameNS(RESENJE, 'Resenje');
    const resenjaDTO: ResenjeDTO[] = [];

    for (let i = 0; i < resenja.length; ++i){
      resenjaDTO.push({
        broj: resenja.item(i).getElementsByTagNameNS(OSNOVA, 'broj')[0].textContent,
        datum: resenja.item(i).getElementsByTagNameNS(OSNOVA, 'datum')[0].textContent,
        status: resenja.item(i).getElementsByTagNameNS(RESENJE, 'status')[0].textContent
      });
    }

    return resenjaDTO;
  }

  save(brojZalbe: string, resenje: Resenje): Observable<null>{
    resenje.odluka = this.xonomyService.removeXmlSpace(resenje.odluka);
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<null>(this.API_RESENJA, this.resenjeToXml(brojZalbe, resenje), options);
  }

  list(): Observable<ResenjeDTO[]>{
    return this.http.get<string>(this.API_RESENJA, {responseType: 'text' as 'json'}).pipe(
      map((xml: string) => this.xmlToResenja(xml))
    );
  }

  view(broj: string): Observable<string>{
    return this.http.get<string>(`${this.API_RESENJA}/${broj}`, {responseType: 'text' as 'json'});
  }

}
