import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ODGOVOR, OSNOVA } from 'src/app/constants/namespaces';
import { Odgovor } from 'src/app/models/odgovor';
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

  private odgovorToXml(brojZalbe: string, odgovor: Odgovor): string{

    return `
      <odgovor:Odgovor
      xmlns="${OSNOVA}"
      xmlns:odgovor="${ODGOVOR}">
        <broj>${brojZalbe}</broj>
        ${odgovor.detalji}
      </odgovor:Odgovor>
    `;

  }

  view(broj: string): Observable<string>{
    return this.http.get<string>(`${this.API_ODGOVORI}/${broj}`, {responseType: 'text' as 'json'});
  }

  save(broj: string, odgovor: Odgovor): Observable<null>{
    odgovor.detalji = this.xonomyService.removeXmlSpace(odgovor.detalji);
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<null>(this.API_ODGOVORI, this.odgovorToXml(broj, odgovor), options);
  }

}
