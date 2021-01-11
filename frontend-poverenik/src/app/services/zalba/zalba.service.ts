import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { OSNOVA, XS, XSI, ZALBA } from 'src/app/constants/namespaces';
import { KORISNIK, PREDIKAT, ZAHTEV, ODLUKA } from 'src/app/constants/prefixes';
import { ZalbaCutanje } from 'src/app/models/zalba-cutanje';
import { ZalbaOdluka } from 'src/app/models/zalba-odluka';
import { ZalbaDTO } from 'src/app/models/zalbaDTO';
import { environment } from 'src/environments/environment';
import { AuthService } from '../auth/auth.service';
import { XonomyService } from '../xonomy/xonomy.service';

@Injectable({
  providedIn: 'root'
})
export class ZalbaService {

  constructor(
    private http: HttpClient,
    private xonomyService: XonomyService,
    private authService: AuthService
  ) { }

  private readonly API_ZALBE = `${environment.baseUrl}/${environment.apiZalbe}`;

  private dateToString(date: Date): string {
    return `${date.getDate()}.${date.getMonth() + 1}.${date.getFullYear()}.`;
  }

  private zalbaCutanjeToXml(zalba: ZalbaCutanje): string{
    let result;
    if (zalba.tipCutanja !== 'nije postupio u celosti'){
      result = `
      ${zalba.detalji}
      <zalba:PodaciZahteva>
        <broj rel="pred:zahtev" href="${ZAHTEV}${zalba.brojDokumenta}">${zalba.brojDokumenta}</broj>
      </zalba:PodaciZahteva>
      <zalba:tipCutanja>${zalba.tipCutanja}</zalba:tipCutanja>
    `;
    }
    else{
      result = `
      ${zalba.detalji}
      <zalba:PodaciZahteva>
        <broj rel="pred:zahtev" href=""></broj>
      </zalba:PodaciZahteva>
      <zalba:tipCutanja>${zalba.tipCutanja}</zalba:tipCutanja>
      <zalba:PodaciOdluke>
        <broj rel="pred:odluka" href="${ODLUKA}${zalba.brojDokumenta}">${zalba.brojDokumenta}</broj>
      </zalba:PodaciOdluke>
    `;
    }
    return `
      <zalba:Zalba
      about=""
      rel="pred:podneo"
      href="${KORISNIK}${this.authService.getUser().mejl}"
      xmlns:xs="${XS}"
      xmlns:pred="${PREDIKAT}"
      xmlns="${OSNOVA}"
      xmlns:zalba="${ZALBA}"
      xmlns:xsi="${XSI}"
      xsi:type="zalba:TZalbaCutanje">
        <datum property="pred:datum" datatype="xs:string">${this.dateToString(new Date())}</datum>
        ${result}
      </zalba:Zalba>
    `;

  }

  private zalbaOdlukaToXml(zalba: ZalbaOdluka): string{
    return `
    <zalba:Zalba
    about=""
    rel="pred:podneo"
    href="${KORISNIK}${this.authService.getUser().mejl}"
    xmlns:xs="${XS}"
    xmlns:pred="${PREDIKAT}"
    xmlns="${OSNOVA}"
    xmlns:zalba="${ZALBA}"
    xmlns:xsi="${XSI}" xsi:type="zalba:TZalbaOdluka">
      <datum property="pred:datum" datatype="xs:string">${this.dateToString(new Date())}</datum>
      ${zalba.detalji}
      <zalba:PodaciZahteva>
        <broj rel="pred:zahtev" href=""></broj>
      </zalba:PodaciZahteva>
      <zalba:PodaciOdluke>
        <broj rel="pred:odluka" href="${ODLUKA}${zalba.brojOdluke}">${zalba.brojOdluke}</broj>
      </zalba:PodaciOdluke>
    </zalba:Zalba>
  `;
  }

  private xmlToZalbe(xml: string): ZalbaDTO[]{
    const parser = new DOMParser();
    const zalbe = parser.parseFromString(xml, 'text/xml').getElementsByTagNameNS(ZALBA, 'Zalba');
    const zalbeDTO: ZalbaDTO[] = [];

    for (let i = 0; i < zalbe.length; ++i){
      let datumProsledjivanja;
      if (zalbe.item(i).getElementsByTagNameNS(ZALBA, 'status')[0].textContent === 'cekanje'){
        datumProsledjivanja = Date.now();
      }
      else{
        datumProsledjivanja = Date.parse(zalbe.item(i).getElementsByTagNameNS(ZALBA, 'datumProsledjivanja')[0].textContent);
      }

      zalbeDTO.push({
        tipZalbe: zalbe.item(i).getElementsByTagNameNS(ZALBA, 'tipZalbe')[0].textContent,
        broj: +zalbe.item(i).getElementsByTagNameNS(OSNOVA, 'broj')[0].textContent,
        datum: zalbe.item(i).getElementsByTagNameNS(OSNOVA, 'datum')[0].textContent,
        status: zalbe.item(i).getElementsByTagNameNS(ZALBA, 'status')[0].textContent,
        datumProsledjivanja
      });
    }

    return zalbeDTO;
  }

  saveZalbaCutanje(zalba: ZalbaCutanje): Observable<null>{
    zalba.detalji = this.xonomyService.removeXmlSpace(zalba.detalji);
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<null>(this.API_ZALBE, this.zalbaCutanjeToXml(zalba), options);
  }

  saveZalbaOdluka(zalba: ZalbaOdluka): Observable<null>{
    zalba.detalji = this.xonomyService.removeXmlSpace(zalba.detalji);
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<null>(this.API_ZALBE, this.zalbaOdlukaToXml(zalba), options);
  }

  list(): Observable<ZalbaDTO[]>{
    return this.http.get<string>(this.API_ZALBE, {responseType: 'text' as 'json'}).pipe(
      map((xml: string) => this.xmlToZalbe(xml))
    );
  }

  view(broj: number): Observable<string>{
    return this.http.get<string>(`${this.API_ZALBE}/${broj}`, {responseType: 'text' as 'json'});
  }

  prosledi(broj: number): Observable<null>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<null>(`${this.API_ZALBE}/prosledi/${broj}`, options);
  }

  odustani(broj: number): Observable<null>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<null>(`${this.API_ZALBE}/odustani/${broj}`, options);
  }

  obustavi(broj: number): Observable<null>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<null>(`${this.API_ZALBE}/obustavi/${broj}`, options);
  }

}
