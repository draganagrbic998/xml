import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ODGOVOR, OSNOVA, XS, ZALBA } from 'src/app/constants/namespaces';
import { KORISNIK, PREDIKAT } from 'src/app/constants/prefixes';
import { Odgovor } from 'src/app/models/odgovor';
import { environment } from 'src/environments/environment';
import { AuthService } from '../auth/auth.service';
import { XonomyService } from '../xonomy/xonomy.service';

@Injectable({
  providedIn: 'root'
})
export class OdgovorService {

  constructor(
    private http: HttpClient,
    private xonomyService: XonomyService,
    private authService: AuthService
  ) { }

  private readonly API_ODGOVORI = `${environment.baseUrl}/${environment.apiOdgovori}`;

  private dateToString(date: Date): string {
    return `${date.getDate()}.${date.getMonth() + 1}.${date.getFullYear()}.`;
  }

  private odgovorToXml(brojZalbe: number, odgovor: Odgovor): string{

    return `
      <odgovor:Odgovor
      xmlns:xs="${XS}"
      xmlns:pred="${PREDIKAT}"
      about=""
      rel="pred:podneo"
      href="${KORISNIK}${this.authService.getUser().mejl}"
      xmlns="${OSNOVA}"
      xmlns:odgovor="${ODGOVOR}">
        <broj>${brojZalbe}</broj>
        <datum property="pred:datum" datatype="xs:string">${this.dateToString(new Date())}</datum>
        ${odgovor.detalji}
        <odgovor:datumZalbe rel="pred:zalba" href="${ZALBA}${brojZalbe}"></odgovor:datumZalbe>
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

}
