import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { ODGOVOR, OSNOVA, ZALBA } from 'src/app/constants/namespaces';
import { Odgovor } from 'src/app/models/odgovor';
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
  private readonly API_ODGOVORI = `${environment.baseUrl}/${environment.apiOdgovori}`;

  private odgovorToXml(brojZahteva: string, odgovor: Odgovor): string{

    return `
      <odgovor:Odgovor
      xmlns="${OSNOVA}"
      xmlns:odgovor="${ODGOVOR}">
        <broj>${brojZahteva}</broj>
        ${odgovor.detalji}
      </odgovor:Odgovor>
    `;

  }

  private xmlToZalbe(xml: string): ZalbaDTO[]{
    const parser = new DOMParser();
    const document = parser.parseFromString(xml, 'text/xml');
    const zalbe = document.getElementsByTagNameNS(ZALBA, 'Zalba');
    const zalbeDTO: ZalbaDTO[] = [];

    for (let i = 0; i < zalbe.length; ++i){
      zalbeDTO.push({
        tipZalbe: zalbe.item(i).getElementsByTagNameNS(ZALBA, 'tipZalbe')[0].textContent,
        broj: zalbe.item(i).getElementsByTagNameNS(OSNOVA, 'broj')[0].textContent,
        datum: zalbe.item(i).getElementsByTagNameNS(OSNOVA, 'datum')[0].textContent,
        status: zalbe.item(i).getElementsByTagNameNS(ZALBA, 'status')[0].textContent
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

  saveOdgovor(broj: string, odgovor: Odgovor): Observable<null>{
    odgovor.detalji = this.xonomyService.removeXmlSpace(odgovor.detalji);
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<null>(this.API_ODGOVORI, this.odgovorToXml(broj, odgovor), options);
  }

}
