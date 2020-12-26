import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Zahtev } from 'src/app/models/zahtev';
import { environment } from 'src/environments/environment';
import { DOKUMENT_NAMESPACE } from 'src/app/constants/namespaces';

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
      <dokument:tipZahteva>${zahtev.tipZahteva}</dokument:tipZahtev>
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
    xml += `
      <dokument:Detalji>${zahtev.detalji}</dokument:Detalji>
    `;

    return `
      <dokument:Zahtev
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
