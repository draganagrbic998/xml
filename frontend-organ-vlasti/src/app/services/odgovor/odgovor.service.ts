import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { ODGOVOR, OSNOVA } from 'src/app/constants/namespaces';
import { Odgovor } from 'src/app/models/odgovor';
import { OdgovorDTO } from 'src/app/models/odgovorDTO';
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

  view(broj: number): Observable<string>{
    return this.http.get<string>(`${this.API_ODGOVORI}/${broj}`, {responseType: 'text' as 'json'});
  }

  save(broj: number, odgovor: Odgovor): Observable<null>{
    odgovor.detalji = this.xonomyService.removeXmlSpace(odgovor.detalji);
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<null>(this.API_ODGOVORI, this.odgovorToXml(broj, odgovor), options);
  }

  xmlToOdgovori(xml: string): OdgovorDTO[]{
    const parser = new DOMParser();
    const odgovori = parser.parseFromString(xml, 'text/xml').getElementsByTagNameNS(ODGOVOR, 'Odgovor');
    const odgovoriDTO: OdgovorDTO[] = [];

    for (let i = 0; i < odgovori.length; ++i){
      odgovoriDTO.push({
        broj: +odgovori.item(i).getElementsByTagNameNS(OSNOVA, 'broj')[0].textContent,
        datum: odgovori.item(i).getElementsByTagNameNS(OSNOVA, 'datum')[0].textContent,
        datumZalbe: odgovori.item(i).getElementsByTagNameNS(ODGOVOR, 'datumZalbe')[0].textContent,
      });
    }

    return odgovoriDTO;
  }

  list(): Observable<OdgovorDTO[]>{
    return this.http.get<string>(this.API_ODGOVORI, {responseType: 'text' as 'json'}).pipe(
      map((xml: string) => this.xmlToOdgovori(xml))
    );
  }

}
