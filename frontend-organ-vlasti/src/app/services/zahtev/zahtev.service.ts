import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Zahtev } from 'src/app/models/zahtev';
import { environment } from 'src/environments/environment';
import { OSNOVA, XS, ZAHTEV } from 'src/app/constants/namespaces';
import { ZahtevDTO } from 'src/app/models/zahtevDTO';
import { map } from 'rxjs/operators';
import { XonomyService } from '../xonomy/xonomy.service';
import { AuthService } from '../auth/auth.service';
import { KORISNIK, PREDIKAT } from 'src/app/constants/prefixes';

@Injectable({
  providedIn: 'root'
})
export class ZahtevService {

  constructor(
    private http: HttpClient,
    private xonomyService: XonomyService,
    private authService: AuthService
  ) { }

  private readonly API_ZAHTEVI = `${environment.baseUrl}/${environment.apiZahtevi}`;

  private dateToString(date: Date): string {
    return `${date.getDate()}.${date.getMonth() + 1}.${date.getFullYear()}.`;
  }

  private zahtevToXml(zahtev: Zahtev): string{

    let xml = `
      ${zahtev.detalji}
      <zahtev:tipZahteva property="pred:tip" datatype="xs:string">${zahtev.tipZahteva}</zahtev:tipZahteva>
    `;
    if (zahtev.tipZahteva === 'dostava'){
      xml += `<zahtev:tipDostave>${zahtev.tipDostave}</zahtev:tipDostave>`;
    }
    if (zahtev.tipDostave === 'ostalo'){
      xml += `<zahtev:opisDostave>${zahtev.opisDostave}</zahtev:opisDostave>`;
    }

    return `
      <zahtev:Zahtev
      xmlns="${OSNOVA}"
      xmlns:zahtev="${ZAHTEV}"
      xmlns:xs="${XS}"
      xmlns:pred="${PREDIKAT}"
      about=""
      rel="pred:podneo"
      href="${KORISNIK}${this.authService.getUser().mejl}">
        <datum property="pred:datum" datatype="xs:string">${this.dateToString(new Date())}</datum>
        ${xml}
      </zahtev:Zahtev>
    `;

  }

  private xmlToZahtevi(xml: string): ZahtevDTO[]{
    const parser = new DOMParser();
    const zahtevi = parser.parseFromString(xml, 'text/xml').getElementsByTagNameNS(ZAHTEV, 'Zahtev');
    const zahteviDTO: ZahtevDTO[] = [];

    for (let i = 0; i < zahtevi.length; ++i){
      zahteviDTO.push({
        tipZahteva: zahtevi.item(i).getElementsByTagNameNS(ZAHTEV, 'tipZahteva')[0].textContent,
        broj: +zahtevi.item(i).getElementsByTagNameNS(OSNOVA, 'broj')[0].textContent,
        datum: zahtevi.item(i).getElementsByTagNameNS(OSNOVA, 'datum')[0].textContent,
        status: zahtevi.item(i).getElementsByTagNameNS(ZAHTEV, 'status')[0].textContent
      });
    }

    return zahteviDTO;
  }

  save(zahtev: Zahtev): Observable<null>{
    zahtev.detalji = this.xonomyService.removeXmlSpace(zahtev.detalji);
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<null>(this.API_ZAHTEVI, this.zahtevToXml(zahtev), options);
  }

  list(): Observable<ZahtevDTO[]>{
    return this.http.get<string>(this.API_ZAHTEVI, {responseType: 'text' as 'json'}).pipe(
      map((xml: string) => this.xmlToZahtevi(xml))
    );
  }

  view(broj: number): Observable<string>{
    return this.http.get<string>(`${this.API_ZAHTEVI}/${broj}`, {responseType: 'text' as 'json'});
  }

}
