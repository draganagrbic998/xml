import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { OSNOVA, XSI, ZALBA } from 'src/app/constants/namespaces';
import { ZalbaCutanje } from 'src/app/models/zalba-cutanje';
import { ZalbaOdluka } from 'src/app/models/zalba-odluka';
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
            <mesto>${zalba.sediste.split(' '[0])}</mesto>
            <ulica>${zalba.sediste.split(' '[1])}</ulica>
            <broj>${zalba.sediste.split(' '[2])}</broj>
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
    xmlns:xsi="${XSI}" xsi:type="zalba:TZalbaCutanje">
      <OrganVlasti>
        <naziv>${zalba.naziv}</naziv>
        <Adresa>
          <mesto>${zalba.sediste.split(' '[0])}</mesto>
          <ulica>${zalba.sediste.split(' '[1])}</ulica>
          <broj>${zalba.sediste.split(' '[2])}</broj>
        </Adresa>
      </OrganVlasti>
      ${zalba.detalji}
      <zalba:datumZahteva>${this.dateToString(zalba.datumZahteva)}</zalba:datumZahteva>
      <zalba:brojOdluke>${zalba.brojOdluke}</zalba:brojOdluke>
      <zalba:datumOdluke>${this.dateToString(zalba.datumOdluke)}</zalba:datumOdluke>
    </zalba:Zalba>
`;
  }

  saveZalbaCutanje(zalba: ZalbaCutanje): Observable<null>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<null>(this.API_ZALBE, this.zalbaCutanjeToXml(zalba), options);
  }

  saveZalbaOdluka(zalba: ZalbaOdluka): Observable<null>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<null>(this.API_ZALBE, this.zalbaOdlukaToXml(zalba), options);
  }

  list(): Observable<ZalbaDTO[]>{
    return this.http.get<ZalbaDTO[]>(this.API_ZALBE);
  }

}
