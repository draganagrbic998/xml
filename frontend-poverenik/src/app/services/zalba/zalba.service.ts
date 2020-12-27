import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DOKUMENT_NAMESPACE, OSNOVA_NAMESPACE, XSI_NAMESPACE } from 'src/app/constants/namespaces';
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

  private zalbaCutanjeToXml(zalba: ZalbaCutanje): string{

    return `
      <dokument:Zalba xmlns="${OSNOVA_NAMESPACE}"
      xmlns:dokument="${DOKUMENT_NAMESPACE}"
      xmlns:xsi="${XSI_NAMESPACE}" xsi:type="dokument:TZalbaCutanje">
        <OrganVlasti>
          <naziv>${zalba.organVlasti}</naziv>
        </OrganVlasti>
        ${zalba.detalji}
        <dokument:datumZahteva>2020-12-12</dokument:datumZahteva>
        <dokument:tipCutanja>${zalba.tipCutanja}</dokument:tipCutanja>
      </dokument:Zalba>
    `;

  }

  private zalbaOdlukaToXml(zalba: ZalbaOdluka): string{
    return `
    <dokument:Zalba xmlns="${OSNOVA_NAMESPACE}"
    xmlns:dokument="${DOKUMENT_NAMESPACE}"
    xmlns:xsi="${XSI_NAMESPACE}" xsi:type="dokument:TZalbaOdluka">
      <OrganVlasti>
        <naziv>${zalba.organVlasti}</naziv>
      </OrganVlasti>
      ${zalba.detalji}
      <dokument:datumZahteva>2020-12-12</dokument:datumZahteva>
      <dokument:brojOdluke>${zalba.brojOdluke}</dokument:brojOdluke>
      <dokument:datumOdluke>${zalba.datumOdluke}</dokument:datumOdluke>
    </dokument:Zalba>
  `;
  }

  saveCutanje(zalba: ZalbaCutanje): Observable<null>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<null>(this.API_ZALBE, this.zalbaCutanjeToXml(zalba), options);
  }

  saveOdluka(zalba: ZalbaOdluka): Observable<null>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<null>(this.API_ZALBE, this.zalbaOdlukaToXml(zalba), options);
  }

  list(): Observable<ZalbaDTO[]>{
    return this.http.get<ZalbaDTO[]>(this.API_ZALBE);
  }

}
