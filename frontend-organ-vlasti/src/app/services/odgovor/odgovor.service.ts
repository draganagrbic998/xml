import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { ODGOVOR, OSNOVA } from 'src/app/constants/namespaces';
import { Odgovor } from 'src/app/models/odgovor';
import { OdgovorDTO } from 'src/app/models/odgovorDTO';
import { Referenca } from 'src/app/models/referenca';
import { environment } from 'src/environments/environment';
import { XonomyService } from '../xonomy/xonomy.service';

@Injectable({
  providedIn: 'root'
})
export class OdgovorService {

  constructor(
    private http: HttpClient,
    private xonomyService: XonomyService
  ) { }

  private readonly API_ODGOVORI = `${environment.baseUrl}/${environment.apiOdgovori}`;

  private xmlToOdgovori(xml: string): OdgovorDTO[]{
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
          broj: +reference.item(j).textContent
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

  private odgovorToXml(brojZalbe: number, odgovor: Odgovor): string{

    return `
      <odgovor:Odgovor
      xmlns="${OSNOVA}"
      xmlns:odgovor="${ODGOVOR}">
        <broj>${brojZalbe}</broj>
        ${odgovor.detalji}
      </odgovor:Odgovor>
    `;

  }

  add(broj: number, odgovor: Odgovor): Observable<null>{
    odgovor.detalji = this.xonomyService.removeXmlSpace(odgovor.detalji);
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<null>(this.API_ODGOVORI, this.odgovorToXml(broj, odgovor), options);
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

  metadata(broj: number, format: string): Observable<string>{
    let headers = new HttpHeaders();
    headers = headers.set('Accept', format === 'rdf' ? 'text/xml' : 'application/json');
    return this.http.get<string>(`${this.API_ODGOVORI}/${broj}/metadata`, {responseType: 'text' as 'json', headers});
  }

  pretraga(xml: string, tip: string): Observable<OdgovorDTO[]>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml').set('search-type', tip), responseType: 'text' as 'json' };
    return this.http.post<string>(`${this.API_ODGOVORI}/pretraga`, xml, options).pipe(
      map((result: string) => this.xmlToOdgovori(result)));
  }

}
