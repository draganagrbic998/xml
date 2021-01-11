import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { OSNOVA, RESENJE, XS } from 'src/app/constants/namespaces';
import { KORISNIK, PREDIKAT, ZALBA } from 'src/app/constants/prefixes';
import { Resenje } from 'src/app/models/resenje';
import { ResenjeDTO } from 'src/app/models/resenjeDTO';
import { environment } from 'src/environments/environment';
import { AuthService } from '../auth/auth.service';
import { XonomyService } from '../xonomy/xonomy.service';

@Injectable({
  providedIn: 'root'
})
export class ResenjeService {

  constructor(
    private http: HttpClient,
    private xonomyService: XonomyService,
    private authService: AuthService
  ) { }

  private readonly API_RESENJA = `${environment.baseUrl}/${environment.apiResenja}`;

  private dateToString(date: Date): string {
    return `${date.getDate()}.${date.getMonth() + 1}.${date.getFullYear()}.`;
  }

  private resenjeToXml(brojZalbe: number, resenje: Resenje): string{
    return `
      <resenje:Resenje
      about=""
      rel="pred:podneo"
      href="${KORISNIK}${this.authService.getUser().mejl}"
      xmlns:xs="${XS}"
      xmlns:pred="${PREDIKAT}"
      xmlns="${OSNOVA}"
      xmlns:resenje="${RESENJE}">
        <datum property="pred:datum" datatype="xs:string">${this.dateToString(new Date())}</datum>
        <resenje:status property="pred:tip" datatype="xs:string">${resenje.status}</resenje:status>
        ${resenje.odluka}
        <resenje:PodaciZahteva rel="pred:zahtev" href="">
        </resenje:PodaciZahteva>
        <resenje:PodaciZalbe>
          <resenje:brojZalbe rel="pred:zalba" href="${ZALBA}${brojZalbe}">${brojZalbe}</resenje:brojZalbe>
        </resenje:PodaciZalbe>
        <resenje:PodaciOdluke rel="pred:odluka" href="">
        </resenje:PodaciOdluke>
      </resenje:Resenje>
    `;

  }

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

  save(brojZalbe: number, resenje: Resenje): Observable<null>{
    resenje.odluka = this.xonomyService.removeXmlSpace(resenje.odluka);
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<null>(this.API_RESENJA, this.resenjeToXml(brojZalbe, resenje), options);
  }

  list(): Observable<ResenjeDTO[]>{
    return this.http.get<string>(this.API_RESENJA, {responseType: 'text' as 'json'}).pipe(
      map((xml: string) => this.xmlToResenja(xml))
    );
  }

  view(broj: number): Observable<string>{
    return this.http.get<string>(`${this.API_RESENJA}/${broj}`, {responseType: 'text' as 'json'});
  }

}
