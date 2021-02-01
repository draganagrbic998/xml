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

  private xmlToResenja(xml: string): ResenjeDTO[]{
    const parser = new DOMParser();
    const resenja = parser.parseFromString(xml, 'text/xml').getElementsByTagNameNS(RESENJE, 'Resenje');
    const resenjaDTO: ResenjeDTO[] = [];

    for (let i = 0; i < resenja.length; ++i){
      resenjaDTO.push({
        broj: +resenja.item(i).getElementsByTagNameNS(OSNOVA, 'broj')[0].textContent,
        datum: resenja.item(i).getElementsByTagNameNS(OSNOVA, 'datum')[0].textContent,
        status: resenja.item(i).getElementsByTagNameNS(RESENJE, 'status')[0].textContent
      });
    }

    return resenjaDTO;
  }

  private resenjeToXml(brojZalbe: number, resenje: Resenje): string{
    return `
      <resenje:Resenje
      xmlns="${OSNOVA}"
      xmlns:resenje="${RESENJE}">
        <resenje:status property="pred:tip" datatype="xs:string">${resenje.status}</resenje:status>
        ${resenje.odluka}
        <resenje:brojZalbe>${brojZalbe}</resenje:brojZalbe>
      </resenje:Resenje>
    `;

  }

  add(brojZalbe: number, resenje: Resenje): Observable<null>{
    resenje.odluka = this.xonomyService.removeXmlSpace(resenje.odluka);
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<null>(this.API_RESENJA, this.resenjeToXml(brojZalbe, resenje), options);
  }

  findAll(): Observable<ResenjeDTO[]>{
    return this.http.get<string>(this.API_RESENJA, {responseType: 'text' as 'json'}).pipe(
      map((xml: string) => this.xmlToResenja(xml))
    );
  }

  find(broj: number, format: string): Observable<string>{
    let headers = new HttpHeaders();
    headers = headers.set('Accept', format === 'text' ? 'text/html' : 'application/pdf');
    return this.http.get<string>(`${this.API_RESENJA}/${broj}`, {responseType: format as 'json', headers});
  }

  metadata(broj: number, format: string): Observable<string>{
    let headers = new HttpHeaders();
    headers = headers.set('Accept', format === 'rdf' ? 'text/xml' : 'application/json');
    return this.http.get<string>(`${this.API_RESENJA}/${broj}/metadata`, {responseType: 'text' as 'json', headers});
  }

  pretraga(xml: string, tip: string): Observable<ResenjeDTO[]>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml').set('search-type', tip), responseType: 'text' as 'json' };
    return this.http.post<string>(`${this.API_RESENJA}/pretraga`, xml, options).pipe(
      map((result: string) => this.xmlToResenja(result)));
  }

}
