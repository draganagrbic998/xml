import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { OSNOVA, XSI, ZALBA } from 'src/app/constants/namespaces';
import { ZalbaCutanje } from 'src/app/models/zalba-cutanje';
import { ZalbaOdluka } from 'src/app/models/zalba-odluka';
import { ZalbaDTO } from 'src/app/models/zalbaDTO';
import { environment } from 'src/environments/environment';
import { XonomyService } from '../xonomy/xonomy.service';

@Injectable({
  providedIn: 'root'
})
export class ZalbaService {

  constructor(
    private http: HttpClient,
    private xonomyService: XonomyService
  ) { }

  private readonly API_ZALBE = `${environment.baseUrl}/${environment.apiZalbe}`;

  private dateToString(date: Date): string {
    return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
  }

  private zalbaCutanjeToXml(zalba: ZalbaCutanje): string{

    return `
      <zalba:Zalba xmlns="${OSNOVA}"
      xmlns:zalba="${ZALBA}"
      xmlns:xsi="${XSI}" xsi:type="zalba:TZalbaCutanje">
        <OrganVlasti>
          <naziv>${zalba.naziv}</naziv>
          <Adresa>
            <mesto>${zalba.sediste.split(' ')[0]}</mesto>
            <ulica>${zalba.sediste.split(' ')[1]}</ulica>
            <broj>${zalba.sediste.split(' ')[2]}</broj>
          </Adresa>
        </OrganVlasti>
        ${zalba.detalji}
        <zalba:datumZahteva>${this.dateToString(zalba.datumZahteva)}</zalba:datumZahteva>
        <zalba:tipCutanja>${zalba.tipCutanja}</zalba:tipCutanja>
      </zalba:Zalba>
    `;

  }

  private zalbaOdlukaToXml(zalba: ZalbaOdluka): string{
    return `
    <zalba:Zalba xmlns="${OSNOVA}"
    xmlns:zalba="${ZALBA}"
    xmlns:xsi="${XSI}" xsi:type="zalba:TZalbaOdluka">
      <OrganVlasti>
        <naziv>${zalba.naziv}</naziv>
        <Adresa>
          <mesto>${zalba.sediste.split(' ')[0]}</mesto>
          <ulica>${zalba.sediste.split(' ')[1]}</ulica>
          <broj>${zalba.sediste.split(' ')[2]}</broj>
        </Adresa>
      </OrganVlasti>
      ${zalba.detalji}
      <zalba:datumZahteva>${this.dateToString(zalba.datumZahteva)}</zalba:datumZahteva>
      <zalba:brojOdluke>${zalba.brojOdluke}</zalba:brojOdluke>
      <zalba:datumOdluke>${this.dateToString(zalba.datumOdluke)}</zalba:datumOdluke>
    </zalba:Zalba>
`;
  }

  private xmlToZalbe(xml: string): ZalbaDTO[]{
    const parser = new DOMParser();
    const document = parser.parseFromString(xml, 'text/xml').getElementsByTagNameNS(ZALBA, 'Zalba');
    const zalbe: ZalbaDTO[] = [];
    let zalbaDatumProsledjivanja: number;
    let zalbaStatus: string;

    for (const key of Object.keys(document)){
      zalbaStatus = document[key].getElementsByTagNameNS(ZALBA, 'status')[0].textContent;

      if (zalbaStatus !== 'cekanje') {
        zalbaDatumProsledjivanja = Date.parse(document[key].getElementsByTagNameNS(ZALBA, 'datumProsledjivanja')[0].textContent);
      }
      else {
        zalbaDatumProsledjivanja = Date.now();
      }

      zalbe.push({
        tipZalbe: document[key].getElementsByTagNameNS(ZALBA, 'tipZalbe')[0].textContent,
        broj: document[key].getElementsByTagNameNS(OSNOVA, 'broj')[0].textContent,
        datum: document[key].getElementsByTagNameNS(OSNOVA, 'datum')[0].textContent,
        organVlasti: document[key].getElementsByTagNameNS(OSNOVA, 'naziv')[0].textContent,
        status: zalbaStatus,
        datumProsledjivanja: zalbaDatumProsledjivanja
      });
    }

    return zalbe;
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

  view(broj: string): Observable<string>{
    return this.http.get<string>(`${this.API_ZALBE}/${broj}`, {responseType: 'text' as 'json'});
  }

  send(broj: string): Observable<null>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<null>(`${this.API_ZALBE}/send/${broj}`, options);
  }

}
