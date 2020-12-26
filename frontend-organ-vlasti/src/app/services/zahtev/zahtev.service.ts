import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Zahtev } from 'src/app/models/zahtev';
import { environment } from 'src/environments/environment';
import { DOKUMENT_NAMESPACE, OSNOVA_NAMESPACE } from 'src/app/constants/namespaces';

@Injectable({
  providedIn: 'root'
})
export class ZahtevService {

  constructor(
    private http: HttpClient
  ) { }

  private readonly API_ZAHTEVI = `${environment.baseUrl}/${environment.apiZahtevi}`;

  private zahtevToXml(zahtev: Zahtev): string{

    let xml = `
      ${zahtev.detalji}
      <dokument:tipZahteva>${zahtev.tipZahteva}</dokument:tipZahteva>
    `;
    if (zahtev.tipZahteva === 'dostava'){
      xml += `
        <dokument:tipDostave>${zahtev.tipDostave}</dokument:tipDostave>
      `;
    }
    if (zahtev.tipDostave === 'ostalo'){
      xml += `
        <dokument:opisDostave>${zahtev.opisDostave}</dokument:opisDostave>
      `;
    }

    return `
      <dokument:Zahtev xmlns="${OSNOVA_NAMESPACE}"
      xmlns:dokument="${DOKUMENT_NAMESPACE}">
        ${xml}
      </dokument:Zahtev>
    `;

  }

  save(zahtev: Zahtev): Observable<null>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<null>(this.API_ZAHTEVI, this.zahtevToXml(zahtev), options);
  }


  // ovo ces obrisati
  view(documentIndex: number): Observable<string>{
    const headers = new HttpHeaders().set('Content-Type', 'text/xml');
    return this.http.get<string>(`${this.API_ZAHTEVI}/${documentIndex}`, {headers, responseType: 'text' as 'json'});
  }

}
